package chrslws.covourier.service

import java.util.UUID

import chrslws.covourier.model._
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Item}

import scala.collection.mutable.ListBuffer

trait DeliveryService {
  def assignDelivery(deliveryId: UUID, courier: Courier)
  def createNew(delivery: NewDelivery): Delivery
  def getAll(): List[Delivery]
}

final class LocalDynamoDbImpl extends DeliveryService {
  private val local =
    new DynamoDB(
      AmazonDynamoDBClientBuilder
        .standard()
        .withEndpointConfiguration(
          new AwsClientBuilder.EndpointConfiguration(
            "http://localhost:8000",
            "us-east-1"
          )
        )
        .build()
    )

  private val table = local.getTable("Deliveries")

  val newDeliveryFixture =
    NewDelivery(
      "Fake item",
      Option(Courier(UUID.randomUUID(), "John", "B")),
      Address("123 Fake St", "Brooklyn", "NY", "11222"),
      List(Contact("Picker", "Upper", "123-456-7890")),
      Address("321 Also Fake St", "Brooklyn", "NY", "11222"),
      List(Contact("Dropper", "Offer", "321-456-7890"))
    )

  def readStatus(name: Option[String]): Option[Status] =
    name.collect {
      case Status.Unassigned.name => Status.Unassigned
      case Status.Assigned.name   => Status.Assigned
      case Status.InTransit.name  => Status.InTransit
      case Status.Delivered.name  => Status.Delivered
      case Status.Cancelled.name  => Status.Cancelled
    }

  def readCourier(map: Option[java.util.Map[String, String]]): Option[Courier] =
    map.collect { m =>
      Courier(
        UUID.fromString(m.get("id")),
        m.get("firstName"),
        m.get("lastName")
      )
    }

  def readAddress(map: java.util.Map[String, String]): Address =
    Address(
      map.get("streetAddress"),
      map.get("city"),
      map.get("state"),
      map.get("zip")
    )

  def readContacts(jlist: java.util.List[java.util.Map[String, String]]): List[Contact] = {
    val contacts = new ListBuffer[Contact]
    val it = jlist.iterator()
    while (it.hasNext) {
      val item = it.next()
      contacts += Contact(
        item.get("firstName"),
        item.get("lastName"),
        item.get("phone")
      )
    }
    contacts.toList
  }

  override def assignDelivery(deliveryId: UUID, courier: Courier): Unit = ???

  override def getAll(): List[Delivery] = {
    val results = table.scan()
    val widgets = new ListBuffer[Delivery]
    val it = results.iterator()
    while (it.hasNext) {
      val item = it.next()
      widgets += Delivery(
        UUID.fromString(item.getString("id")),
        item.getString("item"),
        courier = readCourier(Option(item.getMap[String]("courier"))),
        status =
          readStatus(Option(item.getString("status"))).getOrElse(sys.error("Invalid status!")),
        pickupAddress = readAddress(item.getMap[String]("pickupAddress")),
        pickupContacts = readContacts(item.getList[java.util.Map[String, String]]("pickupContacts")),
        deliveryAddress = readAddress(item.getMap[String]("deliveryAddress")),
        deliveryContacts =
          readContacts(item.getList[java.util.Map[String, String]]("deliveryContacts"))
      )
    }
    widgets.toList
  }

  def createNew(delivery: NewDelivery): Delivery = {

    val d = Delivery(
      UUID.randomUUID(),
      delivery.item,
      delivery.courier,
      Status.Unassigned,
      delivery.pickupAddress,
      delivery.pickupContacts,
      delivery.deliveryAddress,
      delivery.deliveryContacts
    )

    val item = new Item()
      .withString("id", d.id.toString)
      .withString("item", d.item)
      .withString("status", d.status.name)
      .withMap(
        "pickupAddress",
        java.util.Map
          .of[String, String](
            "streetAddress",
            d.pickupAddress.streetAddress,
            "city",
            d.pickupAddress.city,
            "state",
            d.pickupAddress.state,
            "zip",
            d.pickupAddress.zip
          )
      )
      .withList(
        "pickupContacts",
        java.util.List
          .of(d.pickupContacts.map {
            case Contact(fn, ln, p) =>
              java.util.Map.of[String, String]("firstName", fn, "lastName", ln, "phone", p)
          }: _*)
      )
      .withMap(
        "deliveryAddress",
        java.util.Map
          .of[String, String](
            "streetAddress",
            d.deliveryAddress.streetAddress,
            "city",
            d.deliveryAddress.city,
            "state",
            d.deliveryAddress.state,
            "zip",
            d.deliveryAddress.zip
          )
      )
      .withList(
        "deliveryContacts",
        java.util.List
          .of(d.deliveryContacts.map {
            case Contact(fn, ln, p) =>
              java.util.Map.of[String, String]("firstName", fn, "lastName", ln, "phone", p)
          }: _*)
      )

    d.courier.foreach { c =>
      item.withMap(
        "courier",
        java.util.Map
          .of[String, String]("id", c.id.toString, "firstName", c.firstName, "lastName", c.lastName)
      )
    }

    val result = table.putItem(item)
    d
  }
}

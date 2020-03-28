package chrslws.covourier.service

import java.util.UUID

import chrslws.covourier.model._
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Item}
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException

import scala.collection.mutable.ListBuffer

object DynamoDbDeliveryService {
  lazy val local = new DynamoDbDeliveryService(
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
  )

  val newDeliveryFixture =
    NewDelivery(
      "Fake item",
      Option(Courier(UUID.randomUUID(), "John", "B")),
      Address("123 Fake St", "Brooklyn", "NY", "11222"),
      List(Contact("Picker", "Upper", "123-456-7890")),
      Address("321 Also Fake St", "Brooklyn", "NY", "11222"),
      List(Contact("Dropper", "Offer", "321-456-7890"))
    )
}

final class DynamoDbDeliveryService(dynamoDb: DynamoDB) extends DeliveryService {

  private val table = dynamoDb.getTable("Deliveries")

  override def claimDelivery(deliveryId: UUID, courier: Courier): Delivery = {
    val update = new UpdateItemSpec()
      .withPrimaryKey("id", deliveryId.toString)
      .withUpdateExpression("set R_status = :status, courier = :courier")
      .withConditionExpression("R_status = :currentStatus")
      .withValueMap(
        new ValueMap()
          .withString(":status", Status.Assigned.name)
          .withMap(":courier", courerAsMap(courier))
          .withString(":currentStatus", Status.Unassigned.name)
      )
      .withReturnValues("ALL_NEW")

    try {
      val udpated = table.updateItem(update)
      readDelivery(udpated.getItem)
    } catch {
      case _: ConditionalCheckFailedException => ???
    }
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
      .withString("R_status", d.status.name)
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
        courerAsMap(c)
      )
    }

    table.putItem(item)
    d
  }

  override def getAll(): List[Delivery] = {
    val results = table.scan()
    val widgets = new ListBuffer[Delivery]
    val it = results.iterator()
    while (it.hasNext) {
      val item = it.next()
      widgets += readDelivery(item)
    }
    widgets.toList
  }

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

  def readDelivery(item: Item): Delivery =
    Delivery(
      UUID.fromString(item.getString("id")),
      item.getString("item"),
      courier = readCourier(Option(item.getMap[String]("courier"))),
      status =
        readStatus(Option(item.getString("R_status"))).getOrElse(sys.error("Invalid status!")),
      pickupAddress = readAddress(item.getMap[String]("pickupAddress")),
      pickupContacts = readContacts(item.getList[java.util.Map[String, String]]("pickupContacts")),
      deliveryAddress = readAddress(item.getMap[String]("deliveryAddress")),
      deliveryContacts =
        readContacts(item.getList[java.util.Map[String, String]]("deliveryContacts"))
    )

  def courerAsMap(c: Courier) =
    java.util.Map
      .of[String, String]("id", c.id.toString, "firstName", c.firstName, "lastName", c.lastName)
}

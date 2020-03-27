package chrslws.covourier.model

final case class NewDelivery(
    item: String,
    courier: Option[Courier],
    pickupAddress: Address,
    pickupContacts: List[Contact],
    deliveryAddress: Address,
    deliveryContacts: List[Contact]
)

package chrslws.covourier.model

final case class NewDelivery(
    item: String,
    pickupAddress: Address,
    pickupContacts: List[Contact],
    deliveryAddress: Address,
    deliveryContacts: List[Contact]
)

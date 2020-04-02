package chrslws.covourier.model

import java.time.Instant

final case class NewDelivery(
    item: String,
    description: String,
    pickupTime: Instant,
    pickupAddress: Address,
    pickupContacts: List[Contact],
    deliveryAddress: Address,
    deliveryContacts: List[Contact]
)

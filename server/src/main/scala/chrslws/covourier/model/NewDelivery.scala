package chrslws.covourier.model

import java.time.OffsetDateTime

final case class NewDelivery(
    item: String,
    description: String,
    pickupTime: OffsetDateTime,
    pickupAddress: Address,
    pickupContacts: List[Contact],
    deliveryAddress: Address,
    deliveryContacts: List[Contact]
)

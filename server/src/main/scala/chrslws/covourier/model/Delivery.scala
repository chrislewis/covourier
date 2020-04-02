package chrslws.covourier.model

import java.time.Instant
import java.util.UUID

final case class Delivery(
    id: UUID,
    item: String,
    description: String,
    status: Status,
    pickupTime: Instant,
    pickupAddress: Address,
    pickupContacts: List[Contact],
    deliveryAddress: Address,
    deliveryContacts: List[Contact]
)

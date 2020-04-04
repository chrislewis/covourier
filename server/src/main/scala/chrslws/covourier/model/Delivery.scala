package chrslws.covourier.model

import java.time.OffsetDateTime
import java.util.UUID

final case class Delivery(
    id: UUID,
    item: String,
    description: String,
    status: Status,
    pickupTime: OffsetDateTime,
    pickupAddress: Address,
    pickupContacts: List[Contact],
    deliveryAddress: Address,
    deliveryContacts: List[Contact]
)

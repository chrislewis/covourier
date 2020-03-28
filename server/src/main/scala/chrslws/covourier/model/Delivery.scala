package chrslws.covourier.model

import java.util.UUID

final case class Delivery(
    id: UUID,
    item: String,
    courier: Option[Courier],
    status: Status,
    pickupAddress: Address,
    pickupContacts: List[Contact],
    deliveryAddress: Address,
    deliveryContacts: List[Contact]
)

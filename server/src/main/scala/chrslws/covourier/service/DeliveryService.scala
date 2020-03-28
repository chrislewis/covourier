package chrslws.covourier.service

import java.util.UUID

import chrslws.covourier.model._

trait DeliveryService {

  /**
    * To claim a delivery means to assign it to a courier for actual delivery of
    * the payload. A claim on can only be made on a delivery whose status
    * is UNASSIGNED.
    */
  def claimDelivery(deliveryId: UUID, courier: Courier): Delivery
  def createNew(delivery: NewDelivery): Delivery
  def getAll(): List[Delivery]
}

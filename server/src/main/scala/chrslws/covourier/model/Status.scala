package chrslws.covourier.model

object Status {
  case object Unassigned extends Status("UNASSIGNED")
  case object Assigned extends Status("ASSIGNED")
  case object InTransit extends Status("IN_TRANSIT")
  case object Delivered extends Status("DELIVERED")
  case object Cancelled extends Status("CANCELLED")
}

sealed abstract class Status(val name: String)

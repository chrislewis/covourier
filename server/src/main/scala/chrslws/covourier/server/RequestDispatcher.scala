package chrslws.covourier.server

import chrslws.covourier.service.DeliveryService
import chrslws.covourier.model.Status
import io.circe.{Encoder, Json}
import io.circe.generic.auto._
import io.circe.syntax._
import io.netty.buffer.Unpooled
import io.netty.handler.codec.http.HttpHeaderNames.{CONTENT_LENGTH, CONTENT_TYPE}
import io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON
import io.netty.handler.codec.http.HttpResponseStatus.{NOT_FOUND, OK}
import io.netty.handler.codec.http.{DefaultFullHttpResponse, HttpMethod, HttpRequest}

class RequestDispatcher(service: DeliveryService) {

  implicit val encodeStatus: Encoder[Status] = new Encoder[Status] {
    final def apply(a: Status): Json = Json.fromString(a.name)
  }

  def dispatch(request: HttpRequest) = {
    (request.uri(), request.method()) match {
      case ("/deliveries", HttpMethod.GET) =>
        val deliveries = service.getAll()
        val jsonText = deliveries.asJson.noSpaces
        val response = new DefaultFullHttpResponse(
          request.protocolVersion(),
          OK,
          Unpooled.wrappedBuffer((jsonText + "\r\n").getBytes())
        )
        response.headers
          .set(CONTENT_TYPE, APPLICATION_JSON)
          .setInt(CONTENT_LENGTH, response.content.readableBytes)
        response
      case _ =>
        new DefaultFullHttpResponse(request.protocolVersion(), NOT_FOUND)
    }
  }
}

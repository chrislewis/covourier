package chrslws.covourier.server

import java.nio.charset.Charset

import chrslws.covourier.model.Status
import chrslws.covourier.service.DeliveryService
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.{Encoder, Json}
import io.netty.buffer.Unpooled
import io.netty.handler.codec.http.HttpHeaderNames.{CONTENT_LENGTH, CONTENT_TYPE}
import io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON
import io.netty.handler.codec.http.HttpResponseStatus.{NOT_FOUND, NOT_IMPLEMENTED, OK}
import io.netty.handler.codec.http.{DefaultFullHttpResponse, HttpContent, HttpMethod, HttpRequest}

class RequestDispatcher(service: DeliveryService) {

  implicit val encodeStatus: Encoder[Status] = new Encoder[Status] {
    final def apply(a: Status): Json = Json.fromString(a.name)
  }

  def dispatch(request: HttpRequest) = {
    val path = request.uri().split("/").toList.tail // drop leading slash

    (path, request.method()) match {
      case ("deliveries" :: Nil, HttpMethod.GET) =>
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

      case ("deliveries" :: Nil, HttpMethod.POST) =>
        val buf = request.asInstanceOf[HttpContent].content()

        println(buf.toString(Charset.forName("UTF-8")))
        val response = new DefaultFullHttpResponse(request.protocolVersion(), NOT_IMPLEMENTED)
        response.headers().setInt(CONTENT_LENGTH, 0)
        response

      case _ =>
        val response = new DefaultFullHttpResponse(request.protocolVersion(), NOT_FOUND)
        response.headers().setInt(CONTENT_LENGTH, 0)
        response
    }
  }
}

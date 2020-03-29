package chrslws.covourier.server

import chrslws.covourier.service.DynamoDbDeliveryService
import io.netty.channel.{ChannelFutureListener, ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.handler.codec.http.HttpHeaderNames.CONNECTION
import io.netty.handler.codec.http.HttpHeaderValues.{CLOSE, KEEP_ALIVE}
import io.netty.handler.codec.http.{HttpRequest, HttpUtil}

/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

class HttpHelloWorldServerHandler extends SimpleChannelInboundHandler[HttpRequest] {

  // TODO
  val dispatcher = new RequestDispatcher(DynamoDbDeliveryService.local)

  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    ctx.flush
    ()
  }

  override def channelRead0(ctx: ChannelHandlerContext, req: HttpRequest): Unit = {
    val keepAlive = HttpUtil.isKeepAlive(req)

    val response = dispatcher.dispatch(req)

    if (keepAlive)
      if (!req.protocolVersion.isKeepAliveDefault) response.headers.set(CONNECTION, KEEP_ALIVE)
      else { // Tell the client we're going to close the connection.
        response.headers.set(CONNECTION, CLOSE)
      }
    val f = ctx.write(response)
    if (!keepAlive) f.addListener(ChannelFutureListener.CLOSE)
    ()
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    cause.printStackTrace()
    ctx.close
    ()
  }
}

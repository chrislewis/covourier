package chrslws.covourier.server

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.codec.http.HttpServerExpectContinueHandler
import io.netty.handler.codec.http.cors.{CorsConfigBuilder, CorsHandler}
import io.netty.handler.ssl.SslContext

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

class HttpHelloWorldServerInitializer(val sslCtx: SslContext)
    extends ChannelInitializer[SocketChannel] {
  override def initChannel(ch: SocketChannel): Unit = {
    val corsConfig = CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials().build()
    val p = ch.pipeline
    if (sslCtx != null) p.addLast(sslCtx.newHandler(ch.alloc))
    p.addLast(new HttpServerCodec)
    p.addLast(new HttpServerExpectContinueHandler)
    p.addLast(new CorsHandler(corsConfig))
    p.addLast(new HttpHelloWorldServerHandler)
    ()
  }
}

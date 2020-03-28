package chrslws.covourier.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate

object HttpHelloWorldServer {
  private val SSL = System.getProperty("ssl") != null
  private val PORT = System
    .getProperty(
      "port",
      if (SSL) "8443"
      else "8080"
    )
    .toInt

  def main(args: Array[String]): Unit = { // Configure SSL.
    var sslCtx: SslContext = null
    if (SSL) {
      val ssc = new SelfSignedCertificate
      sslCtx = SslContextBuilder.forServer(ssc.certificate, ssc.privateKey).build
    } else sslCtx = null
    // Configure the server.
    val bossGroup = new NioEventLoopGroup(1)
    val workerGroup = new NioEventLoopGroup
    try {
      val b = new ServerBootstrap
      b.option[java.lang.Integer](ChannelOption.SO_BACKLOG, 1024)
      b.group(bossGroup, workerGroup)
        .channel(classOf[NioServerSocketChannel])
        .handler(new LoggingHandler(LogLevel.INFO))
        .childHandler(new HttpHelloWorldServerInitializer(sslCtx))
      val ch = b.bind(PORT).sync.channel
      val cf = ch.closeFuture
      if (Thread.currentThread().getName.startsWith("run-main-")) {
        println("awaiting you ..")
        scala.io.StdIn.readLine()
        ()
      } else {
        cf.sync()
        ()
      }
    } finally {
      bossGroup.shutdownGracefully().sync()
      workerGroup.shutdownGracefully().sync()
      ()
    }
  }
}

final class HttpHelloWorldServer {}

package controllers

import akka.actor._
import play.api.Logger
import play.api.Play.current
import play.api.mvc._

object WebSocketApi extends Controller {

  def socket = WebSocket.acceptWithActor[String, String] { request => out =>
    WebSocketActor.props(out)
  }
}

object WebSocketActor {
  def props(out: ActorRef) = Props(new WebSocketActor(out))
}

class WebSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      Logger.info(s"Receive: $msg")
      out ! ("I received your message: " + msg)
  }
}
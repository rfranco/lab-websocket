package controllers

import akka.actor._
import org.joda.time.DateTime
import play.api.Logger
import play.api.Play.current
import play.api.mvc._
import scala.concurrent.duration._

object WebSocketApi extends Controller {

  def socket = WebSocket.acceptWithActor[String, String] { request => out =>
    WebSocketActor.props(out)
  }
}

object WebSocketActor {
  def props(out: ActorRef) = Props(new WebSocketActor(out))
  sealed trait SocketMessage
  case object UpdateTime extends SocketMessage
}

class WebSocketActor(out: ActorRef) extends Actor {

  import WebSocketActor._
  import context.dispatcher
  Logger.info(s"Init: ${self.path}")
  val cancellable = context.system.scheduler.schedule(0.second, 5.seconds, self, UpdateTime)

  def receive = {
    case msg: String =>
      Logger.info(s"Receive: $msg")
      out ! s"I received your message: $msg"
    case UpdateTime =>
      out ! s"Clock ${DateTime.now}"
  }

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    Logger.info(s"preStart: ${self.path}")
    super.preStart()
  }

  @throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    Logger.info(s"postStop: ${self.path}")
    super.postStop()
  }

  @throws[Exception](classOf[Exception])
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    Logger.info(s"preRestart: ${self.path}")
    super.preRestart(reason, message)
  }

  @throws[Exception](classOf[Exception])
  override def postRestart(reason: Throwable): Unit = {
    Logger.info(s"postRestart: ${self.path}")
    super.postRestart(reason)
  }
}
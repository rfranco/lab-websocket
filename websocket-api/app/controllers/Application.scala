package controllers

import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok("WebSocket")
  }

  def options(all: String) = Action {
    Ok("")
  }
}
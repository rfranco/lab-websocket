import play.api._
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Global extends WithFilters(RequestTimeLogger, CORSFilter) with GlobalSettings {

}

object RequestTimeLogger extends Filter {
  def apply(nextFilter: (RequestHeader) => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    val startTime = System.currentTimeMillis
    nextFilter(requestHeader).map { result =>
      val endTime = System.currentTimeMillis
      val requestTime = endTime - startTime
      Logger.info(s"${requestHeader.method} ${requestHeader.uri} took ${requestTime}ms and returned ${result.header.status}")
      result.withHeaders("Request-Time" -> requestTime.toString)
    }
  }
}

object CORSFilter extends Filter {

  import scala.concurrent._

  lazy val allowedDomain = play.api.Play.current.configuration.getString("cors.allowed.domain")
  lazy val exposeHeaders = "X-Auth-Token"

  def isPreFlight(r: RequestHeader) = {
    r.method.toLowerCase.equals("options") && r.headers.get("Access-Control-Request-Method").nonEmpty
  }

  def apply(f: (RequestHeader) => Future[Result])(request: RequestHeader): Future[Result] = {
    Logger.trace("[cors] filtering request to add cors")
    if (isPreFlight(request)) {
      Logger.trace("[cors] request is preflight")
      Logger.trace(s"[cors] default allowed domain is $allowedDomain")
      Future.successful(Results.Ok.withHeaders(
        "Access-Control-Allow-Origin" -> allowedDomain.orElse(request.headers.get("Origin")).getOrElse(""),
        "Access-Control-Allow-Methods" -> request.headers.get("Access-Control-Request-Method").getOrElse("*"),
        "Access-Control-Allow-Headers" -> request.headers.get("Access-Control-Request-Headers").getOrElse(""),
        "Access-Control-Expose-Headers" -> request.headers.get("Access-Control-Expose-Headers").getOrElse(exposeHeaders),
        "Access-Control-Allow-Credentials" -> "true"
      ))
    } else {
      Logger.trace("[cors] request is normal")
      Logger.trace(s"[cors] default allowed domain is $allowedDomain")
      f(request).map {
        _.withHeaders(
          "Access-Control-Allow-Origin" -> allowedDomain.orElse(request.headers.get("Origin")).getOrElse(""),
          "Access-Control-Allow-Methods" -> request.headers.get("Access-Control-Request-Method").getOrElse("*"),
          "Access-Control-Allow-Headers" -> request.headers.get("Access-Control-Request-Headers").getOrElse(""),
          "Access-Control-Expose-Headers" -> request.headers.get("Access-Control-Expose-Headers").getOrElse(exposeHeaders),
          "Access-Control-Allow-Credentials" -> "true"
        )
      }
    }
  }
}
package au.com.cba.json.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ValidationRejection
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import au.com.cba.json.parser.JsonParser
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.ExecutionContext

object Server extends App with LazyLogging {

  val config = ConfigFactory.load()
  val host = config.getString("parser.api.host")
  val port = config.getInt("parser.api.port")

  implicit val system: ActorSystem = ActorSystem("json-parser")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(system))

  val allRoutes = path("api" / "parser") {
    post {
      entity(as[String]) { jsonString =>
        JsonParser.parse(jsonString) match {
          case Right(x) =>
            val successMessage = s"Json parser succeeded for input string. It's a type of ${x.getClass}"
            logger.info(successMessage)
            complete(successMessage)

          case Left(er) =>
            val errorMessage = s"Json parser failed for input string with error: ${er.message}"
            logger.error(errorMessage)
            reject(ValidationRejection(errorMessage, Option(er.error)))
        }
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(allRoutes, host, port)
  logger.info(s"Server online at http://$host:$port/")
}

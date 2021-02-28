package au.com.cba.json.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ValidationRejection
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import au.com.cba.json.parser.JsonParser
import com.typesafe.config.ConfigFactory
import scala.concurrent.ExecutionContext

object Server extends App {

  val config = ConfigFactory.load()
  val host = config.getString("parser.api.host")
  val port = config.getInt("parser.api.port")

  implicit val system: ActorSystem = ActorSystem("json-parser")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(system))

  val allRoutes = path("api" / "parser") {
    get {
      complete("Welcome to json parser api!")
    }

    post {
      entity(as[String]) { jsonString =>
        JsonParser.parse(jsonString) match {
          case Right(x) => complete(s"Json parser succeeded for JSON string.It's a type of ${x.getClass}")
          case Left(er) => reject(ValidationRejection(s"Json parser failed for JSON ${er.message}", Option(er.error)))
        }
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(allRoutes, host, port)
  println(s"Server online at http://$host:$port/")
}

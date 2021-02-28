package au.com.cba.json.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ValidationRejection
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import au.com.cba.json.parser.JsonParser
import scala.concurrent.ExecutionContext

object Server extends App {

  val host = "0.0.0.0"
  val port = 9000

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
          case Right(_) => complete(s"Json parser succeeded for JSON $jsonString")
          case Left(er) => reject(ValidationRejection(s"Json parser failed for JSON $jsonString", Option(er.error)))
        }
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(allRoutes, host, port)
  println(s"Server online at http://$host:$port/")
}

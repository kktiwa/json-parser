package au.com.cba.json.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ValidationRejection
import akka.stream.ActorMaterializer
import au.com.cba.json.parser.JsonParser
import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Server extends App {
  val host = "0.0.0.0"
  val port = 9000

  implicit val system: ActorSystem = ActorSystem("json-parser")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val route = path("api" / "parser") {
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

  val bindingFuture = Http().bindAndHandle(route, host, port)
  println(s"Server online at http://$host:$port/\nPress RETURN to stop...")

  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}

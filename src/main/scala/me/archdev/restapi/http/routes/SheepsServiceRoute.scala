package me.archdev.restapi.http.routes

import akka.http.scaladsl._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import me.archdev.restapi.models.{SheepEntity, SheepEntityUpdate}
import me.archdev.restapi.services.SheepsService
import spray.json._

import scalaj.http.{Http, HttpOptions}

trait SheepsServiceRoute extends SheepsService with BaseServiceRoute {

  import StatusCodes._

  implicit val sheepsUpdateFormat = jsonFormat2(SheepEntityUpdate)

  val sheepsRoute = pathPrefix("sheeps") {
    pathEndOrSingleSlash {
      get {
        println("get all sheeps")
        complete(getSheeps().map(_.toJson))
      } ~
      post {
        println("post new sheep")
        entity(as[SheepEntity]) { sheepUpdate =>
          complete(Created -> createSheep(sheepUpdate).map(_.toJson))
        }
      }
    } ~
      pathPrefix(IntNumber) { id =>
        pathEndOrSingleSlash {
          get {

            println("get")
            // https://github.com/scalaj/scalaj-http
            println(Http("http://localhost:9000/v1/sheeps").asString) // example external get request
            println("post")
            println(Http("http://localhost:9000/v1/sheeps").postForm(Seq("sheepname" -> "jon", "password" -> "29"))
              .header("content-type", "application/json").asString) // example external post request



            complete(getSheepById(id).map(_.toJson))
          } ~
            put {
              entity(as[SheepEntityUpdate]) { sheepUpdate =>
                complete(updateSheep(id, sheepUpdate).map(_.toJson))
              }
            } ~
            delete {
              onSuccess(deleteSheep(id)) { ignored =>
                complete(NoContent)
              }
            }
        }
      }
  }

}

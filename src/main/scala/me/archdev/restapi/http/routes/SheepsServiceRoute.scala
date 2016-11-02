package me.archdev.restapi.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import me.archdev.restapi.models.{SheepEntity, SheepEntityUpdate}
import me.archdev.restapi.services.SheepsService
import spray.json._


trait SheepsServiceRoute extends SheepsService with BaseServiceRoute {

  import StatusCodes._

  implicit val sheepsUpdateFormat = jsonFormat2(SheepEntityUpdate)

  val sheepsRoute = pathPrefix("sheeps") {
    pathEndOrSingleSlash {
      get {
        complete(getSheeps().map(_.toJson))
      } ~
      post {
        entity(as[SheepEntity]) { sheepUpdate =>
          complete(Created -> createSheep(sheepUpdate).map(_.toJson))
        }
      }
    } ~
      pathPrefix(IntNumber) { id =>
        pathEndOrSingleSlash {
          get {
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

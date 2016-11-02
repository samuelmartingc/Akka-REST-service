package me.archdev.restapi.services

import me.archdev.restapi.models.{SheepEntity, SheepEntityUpdate}
import me.archdev.restapi.models.db.SheepEntityTable
import org.mindrot.jbcrypt.BCrypt

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import scala.concurrent.Future

object SheepsService extends SheepsService

trait SheepsService extends SheepEntityTable {

  import driver.api._

  def getSheeps(): Future[Seq[SheepEntity]] = db.run(sheeps.result)

  def getSheepById(id: Long): Future[Option[SheepEntity]] = db.run(sheeps.filter(_.id === id).result.headOption)

  def getSheepByLogin(login: String): Future[Option[SheepEntity]] = db.run(sheeps.filter(_.sheepname === login).result.headOption)

  def createSheep(sheep: SheepEntity): Future[SheepEntity] = db.run(sheeps returning sheeps += sheep.withHashedPassword())

  def updateSheep(id: Long, sheepUpdate: SheepEntityUpdate): Future[Option[SheepEntity]] = getSheepById(id).flatMap {
    case Some(sheep) =>
      val updatedSheep = sheepUpdate.merge(sheep)
      db.run(sheeps.filter(_.id === id).update(updatedSheep)).map(_ => Some(updatedSheep))
    case None => Future.successful(None)
  }

  def deleteSheep(id: Long): Future[Int] = db.run(sheeps.filter(_.id === id).delete)

}
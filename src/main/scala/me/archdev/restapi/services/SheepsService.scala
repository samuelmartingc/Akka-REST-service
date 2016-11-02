package me.archdev.restapi.services

import me.archdev.restapi.models.{SheepEntity, SheepEntityUpdate}
import org.mindrot.jbcrypt.BCrypt

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object SheepsService extends SheepsService

trait SheepsService {

  def getSheeps(): Future[Seq[SheepEntity]] = Future(Seq(new SheepEntity(Option(1),"sheepname","password"),new SheepEntity(Option(42),"meaning_of_life","*****")))

  def getSheepById(id: Long): Future[Option[SheepEntity]] = Future(Option(new SheepEntity(Option(1),"sheepname","password")))

  def getSheepByLogin(login: String): Future[Option[SheepEntity]] = Future(Option(new SheepEntity(Option(1),"sheepname","password")))

  def createSheep(sheep: SheepEntity): Future[SheepEntity] = Future(new SheepEntity(Option(1),"sheepname","password"))

  def updateSheep(id: Long, sheepUpdate: SheepEntityUpdate): Future[Option[SheepEntity]] = Future(Option(new SheepEntity(Option(1),"sheepname","password")))

  def deleteSheep(id: Long): Future[Int] = Future(1)

}
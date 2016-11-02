package me.archdev.restapi.models.db

import me.archdev.restapi.models.SheepEntity
import me.archdev.restapi.utils.DatabaseConfig

trait SheepEntityTable extends DatabaseConfig {

  import driver.api._

  class Sheeps(tag: Tag) extends Table[SheepEntity](tag, "sheeps") {
    def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def sheepname = column[String]("sheepname")
    def password = column[String]("password")

    def * = (id, sheepname, password) <> ((SheepEntity.apply _).tupled, SheepEntity.unapply)
  }

  protected val sheeps = TableQuery[Sheeps]

}

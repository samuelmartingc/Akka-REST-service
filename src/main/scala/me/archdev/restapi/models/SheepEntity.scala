package me.archdev.restapi.models

import org.mindrot.jbcrypt.BCrypt

case class SheepEntity(id: Option[Long] = None, sheepname: String, password: String) {
  require(!sheepname.isEmpty, "sheepname.empty")
  require(!password.isEmpty, "password.empty")

  def withHashedPassword(): SheepEntity = this.copy(password = BCrypt.hashpw(password, BCrypt.gensalt()))
}

case class SheepEntityUpdate(sheepname: Option[String] = None, password: Option[String] = None) {
  def merge(sheep: SheepEntity): SheepEntity = {
    SheepEntity(sheep.id, sheepname.getOrElse(sheep.sheepname), password.map(ps => BCrypt.hashpw(ps, BCrypt.gensalt())).getOrElse(sheep.password))
  }
}
package com.laplacian.luxuryakka.module.user.dao

import com.laplacian.luxuryakka.core.GeneratedId
import com.laplacian.luxuryakka.module.user.domain.{UserCreateEntity, UserDetailsEntity}

trait UserRepository
{
  def insert(item: UserCreateEntity): GeneratedId

  def findById(id: Long): Option[UserDetailsEntity]
  def findByUsername(username: String): Option[UserDetailsEntity]
  def findByEmail(email: String): Option[UserDetailsEntity]
  def findAll: List[UserDetailsEntity]
}

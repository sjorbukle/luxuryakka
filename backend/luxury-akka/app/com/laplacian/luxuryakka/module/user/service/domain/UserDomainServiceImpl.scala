package com.laplacian.luxuryakka.module.user.service.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.beans.factory.annotation.Autowired
import com.laplacian.luxuryakka.module.user.dao.UserRepository
import com.laplacian.luxuryakka.core.{GeneratedId, Asserts}
import com.laplacian.luxuryakka.module.user.domain.{UserDetailsEntity, UserCreateEntity}

@Service
@Transactional
class UserDomainServiceImpl @Autowired
(
  private val userRepository: UserRepository
) extends UserDomainService
{
  Asserts.argumentIsNotNull(userRepository)

  override def create(item: UserCreateEntity): GeneratedId =
  {
    Asserts.argumentIsNotNull(item)

    this.userRepository.insert(item)
  }

  override def tryGetById(id: Long): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsTrue(id > 0)

    this.userRepository.findById(id)
  }

  override def tryGetByUsername(username: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNull(username)

    this.userRepository.findByUsername(username)
  }

  override def tryGetByEmail(email: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNullNorEmpty(email)

    this.userRepository.findByEmail(email)
  }

  override def getAll: List[UserDetailsEntity] =
  {
    this.userRepository.findAll
  }
}

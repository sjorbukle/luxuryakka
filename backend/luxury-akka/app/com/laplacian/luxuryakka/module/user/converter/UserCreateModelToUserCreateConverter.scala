package com.laplacian.luxuryakka.module.user.converter

import com.laplacian.luxuryakka.core.{Asserts, Converter}
import com.laplacian.luxuryakka.module.user.model.UserCreateModel
import com.laplacian.luxuryakka.module.user.domain.UserCreateEntity

object UserCreateModelToUserCreateConverter extends Converter[UserCreateModel, UserCreateEntity]
{
  override def convert(in: UserCreateModel): UserCreateEntity =
  {
    Asserts.argumentIsNotNull(in)

    UserCreateEntity(
      firstName = UserCreateModel.FIRST_NAME_FIELD.bind(in.firstName.map(_.toString)),
      lastName  = UserCreateModel.LAST_NAME_FIELD.bind(in.lastName.map(_.toString)),
      email     = UserCreateModel.EMAIL_FIELD.bind(in.email.map(_.toString)),
      username  = UserCreateModel.USERNAME_FIELD.bind(in.username.map(_.toString)),
      password  = UserCreateModel.PASSWORD_FIELD.bind(in.password.map(_.toString))
    )
  }
}

package com.laplacian.luxuryakka.module.user.dao.sql

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import com.laplacian.luxuryakka.module.user.domain.UserDetailsEntity
import com.laplacian.luxuryakka.core.Asserts

object UserRowMapper extends RowMapper[UserDetailsEntity]
{
  def mapRow(resultSet: ResultSet, rowNum: Int): UserDetailsEntity =
  {
    Asserts.argumentIsNotNull(resultSet)
    Asserts.argumentIsNotNull(rowNum)

    UserDetailsEntity(
      id        = resultSet.getLong("id"),
      firstName = resultSet.getString("first_name"),
      lastName  = resultSet.getString("last_name"),
      email     = resultSet.getString("email"),
      username  = resultSet.getString("username"),
      password  = resultSet.getString("password")
    )
  }
}

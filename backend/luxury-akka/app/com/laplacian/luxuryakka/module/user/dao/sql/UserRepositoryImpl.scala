package com.laplacian.luxuryakka.module.user.dao.sql

import com.laplacian.luxuryakka.module.user.dao.UserRepository
import com.laplacian.luxuryakka.module.user.domain.{UserDetailsEntity, UserCreateEntity}
import com.laplacian.luxuryakka.core.{Asserts, GeneratedId}
import org.springframework.jdbc.core.namedparam.{MapSqlParameterSource, NamedParameterJdbcTemplate}
import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.support.GeneratedKeyHolder
import scala.collection.convert.Wrappers
import scala.collection.JavaConversions._

@Repository
class UserRepositoryImpl @Autowired
(
  private val jdbcTemplate : NamedParameterJdbcTemplate
) extends UserRepository
{
  Asserts.argumentIsNotNull(jdbcTemplate)

  private final val INSERT_QUERY =
    """
      |INSERT INTO public.users
      |(
      |    first_name,
      |    last_name,
      |    username,
      |    email,
      |    password
      |)
      |VALUES
      |(
      |    :firstName,
      |    :lastName,
      |    :username,
      |    :email,
      |    :password
      |)
      |
    """.stripMargin

  private final val FIND_ALL_QUERY =
    """
      |SELECT id,
      |       first_name,
      |       last_name,
      |       username,
      |       email,
      |       password
      |FROM public.users
    """.stripMargin

  private final val GET_BY_ID       = FIND_ALL_QUERY + " WHERE id = :id"
  private final val GET_BY_USERNAME = FIND_ALL_QUERY + " WHERE username = :username"
  private final val GET_BY_EMAIL    = FIND_ALL_QUERY + " WHERE email = :email"

  override def insert(item: UserCreateEntity): GeneratedId =
  {
    Asserts.argumentIsNotNull(item)

    val parametersMap = new MapSqlParameterSource(
      Map(
        "firstName" -> item.firstName,
        "lastName"  -> item.lastName,
        "username"  -> item.username,
        "email"     -> item.email,
        "password"  -> item.password
      )
    )

    val generatedKey = new GeneratedKeyHolder
    jdbcTemplate.update(INSERT_QUERY, parametersMap, generatedKey)

    GeneratedId(generatedKey.getKeys.get("id").asInstanceOf[Number].longValue)
  }

  override def findById(id: Long): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsTrue(id > 0)

    val parametersMap = new MapSqlParameterSource("id", id)
    val itemCandidate = Wrappers.JListWrapper(jdbcTemplate.query(GET_BY_ID, parametersMap, UserRowMapper)).toList

    itemCandidate match {
      case Nil          => None
      case head :: Nil  => Some(head)
      case _            => throw new IllegalStateException("More than one user with same id found")
    }
  }

  override def findByUsername(username: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNull(username)

    val parametersMap = new MapSqlParameterSource("username", username)
    val itemCandidate = Wrappers.JListWrapper(jdbcTemplate.query(GET_BY_USERNAME, parametersMap, UserRowMapper)).toList

    itemCandidate match {
      case Nil          => None
      case head :: Nil  => Some(head)
      case _            => throw new IllegalStateException("More than one user with same username found")
    }
  }

  override def findByEmail(email: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNullNorEmpty(email)

    val parametersMap = new MapSqlParameterSource("email", email)
    val itemCandidate = Wrappers.JListWrapper(jdbcTemplate.query(GET_BY_EMAIL, parametersMap, UserRowMapper)).toList

    itemCandidate match {
      case Nil          => None
      case head :: Nil  => Some(head)
      case _            => throw new IllegalStateException("More than one user with same email found")
    }
  }

  override def findAll: List[UserDetailsEntity] =
  {
    Wrappers.JListWrapper(jdbcTemplate.query(FIND_ALL_QUERY, UserRowMapper)).toList
  }
}

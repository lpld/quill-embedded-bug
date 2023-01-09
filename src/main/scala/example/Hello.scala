package example

import io.getquill._

object Hello extends App {

  val ctx = new SqlMirrorContext(PostgresDialect, SnakeCase)

  import ctx._

  case class PersonName(firstName: String, lastName: String)
  case class Person(id: Long, name: PersonName)

  implicit val pNameDecode = MappedEncoding[String, PersonName] { PersonName(_, "Smith") }

  // The following two queries generate different SQL:

  // SELECT x.id, x.name FROM person x
  println(run { query[Person] }.string)

  // SELECT x.id, x.first_name AS firstName, x.last_name AS lastName FROM person x
  println(run { dynamicQuery[Person] }.string)
}


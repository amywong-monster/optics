package org.jinilover.optics.optionalprisms

object EmployeeFuncs {
  import monocle.macros.Lenses

  @Lenses
  case class Street(number: Int, name: String)
  // start of Address ADT
  sealed trait Address
  @Lenses
  case class UkAddress(postTown: String, postcode: String, street: Street) extends Address
  @Lenses
  case class AusAddress(state: String, postcode: Int, street: Street) extends Address
  // end of Address ADT
  @Lenses
  case class Company(name: String, address: Option[Address])
  @Lenses
  case class Employee(name: String, company: Company)

  // TODO
  // try using the previous example to figure out how to modify/update the `Street` fields of an employee
  // working in Australia
}

package org.jinilover.optics.optionalprisms

object EmployeeFuncs {
  import monocle.macros.Lenses

  @Lenses
  final case class Street(number: Int, name: String)
  // start of Address ADT
  sealed trait Address
  @Lenses
  final case class UkAddress(postTown: String, postcode: String, street: Street) extends Address
  @Lenses
  final case class AusAddress(state: String, postcode: Int, street: Street) extends Address
  // end of Address ADT
  @Lenses
  final case class Company(name: String, address: Option[Address])
  @Lenses
  final case class Employee(name: String, company: Company)

  // TODO
  // try using the previous example to figure out how to update the `Street number` of an employee
  // working in Australia
  import monocle.syntax.all._
  def updateAusStreetNum(origEmployee: Employee, newStreetNum: Int): Employee =
    ???
}

package org.jinilover.optics.usesugar

object EmployeeFuncs {
  final case class Street(number: Int, name: String)
  final case class Address(city: String, street: Street)
  final case class Company(name: String, address: Address)
  final case class Employee(name: String, company: Company)

  import monocle.macros.syntax.lens._

  def capitaliseStreetName(origEmployee: Employee): Employee =
    origEmployee.lens(_.company.address.street.name).modify(_.capitalize)

  def updateStreetName(origEmployee: Employee, newStreetName: String): Employee =
    origEmployee.lens(_.company.address.street.name).set(newStreetName)

  def updateStreetNum(origEmployee: Employee, newStreetNum: Int): Employee =
    origEmployee.lens(_.company.address.street.number).set(newStreetNum)

  def updateStreetNameAndNum(origEmployee: Employee, newStreetName: String, newStreetNum: Int): Employee =
    origEmployee
      .lens(_.company.address.street.name)
      .set(newStreetName)
      .lens(_.company.address.street.number)
      .set(newStreetNum)
}

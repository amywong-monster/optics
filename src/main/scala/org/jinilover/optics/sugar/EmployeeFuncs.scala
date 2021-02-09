package org.jinilover.optics.sugar

case class Street(number: Int, name: String)
case class Address(city: String, street: Street)
case class Company(name: String, address: Address)
case class Employee(name: String, company: Company)

object EmployeeFuncs {
  import monocle.macros.syntax.lens._

  def chainUpdate(origEmployee: Employee, newStreetName: String, newStreetNum: Int): Employee = {
    origEmployee
      .lens(_.company.address.street.name).set(newStreetName)
      .lens(_.company.address.street.number).set(newStreetNum)
  }
}

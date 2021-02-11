package org.jinilover.optics.usesugar

object EmployeeFuncs {
  case class Street(number: Int, name: String)
  case class Address(city: String, street: Street)
  case class Company(name: String, address: Address)
  case class Employee(name: String, company: Company)

  import monocle.macros.syntax.lens._

  def seriesUpdate(origEmployee: Employee, newStreetName: String, newCompanyName: String): Employee =
    origEmployee
      .lens(_.company.address.street.name).set(newStreetName) // `_.company.address.street.name` is the same as `v => v.company.address.street.name`
      .lens(_.company.name).set(newCompanyName)
}

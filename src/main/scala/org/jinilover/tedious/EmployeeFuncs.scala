package org.jinilover.tedious

case class Street(number: Int, name: String)
case class Address(city: String, street: Street)
case class Company(name: String, address: Address)
case class Employee(name: String, company: Company)

object EmployeeFuncs {
  def capitaliseStreetName(origEmployee: Employee): Employee =
    origEmployee.copy(
      company = origEmployee.company.copy(
        address = origEmployee.company.address.copy(
          street = origEmployee.company.address.street.copy(
            name = origEmployee.company.address.street.name.capitalize
          )
        )
      )
    )
}

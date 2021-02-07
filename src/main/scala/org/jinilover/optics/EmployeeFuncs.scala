package org.jinilover.optics

case class Street(number: Int, name: String)
case class Address(city: String, street: Street)
case class Company(name: String, address: Address)
case class Employee(name: String, company: Company)

object EmployeeFuncs {
  def capitaliseStreetName1(employee: Employee): Employee =
    employee.copy(
      company = employee.company.copy(
        address = employee.company.address.copy(
          street = employee.company.address.street.copy(
            name = employee.company.address.street.name.capitalize
          )
        )
      )
    )
}

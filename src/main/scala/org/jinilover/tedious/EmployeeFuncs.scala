package org.jinilover.tedious

final case class Street(number: Int, name: String)
final case class Address(city: String, street: Street)
final case class Company(name: String, address: Address)
final case class Employee(name: String, company: Company)

object EmployeeFuncs {
  def capitaliseStreetName(origEmployee: Employee): Employee =
    origEmployee.copy(company =
      origEmployee.company.copy(address =
        origEmployee.company.address.copy(street =
          origEmployee.company.address.street.copy(name = origEmployee.company.address.street.name.capitalize)
        )
      )
    )
}

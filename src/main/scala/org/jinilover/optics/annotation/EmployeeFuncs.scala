package org.jinilover.optics.annotation

import monocle.macros._

@Lenses
case class Street(number: Int, name: String)
@Lenses
case class Address(city: String, street: Street)
@Lenses
case class Company(name: String, address: Address)
@Lenses
case class Employee(name: String, company: Company)

object EmployeeFuncs {
  def capitaliseStreetName(origEmployee: Employee): Employee =
    Employee.company.composeLens(Company.address).composeLens(Address.street).composeLens(Street.name).modify(_.capitalize)(origEmployee)

  def updateStreetName(origEmployee: Employee, newStreetName: String): Employee =
    Employee.company.composeLens(Company.address).composeLens(Address.street).composeLens(Street.name).set(newStreetName)(origEmployee)

  def updateStreetNum(origEmployee: Employee, newStreetNum: Int): Employee =
    Employee.company.composeLens(Company.address).composeLens(Address.street).composeLens(Street.number).set(newStreetNum)(origEmployee)
}

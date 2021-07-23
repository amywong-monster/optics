package org.jinilover.optics.useannotation

object EmployeeFuncs {
  import monocle.macros.Lenses

  @Lenses
  case class Street(number: Int, name: String)
  @Lenses
  case class Address(city: String, street: Street)
  @Lenses
  case class Company(name: String, address: Address)
  @Lenses
  case class Employee(name: String, company: Company)

  def capitaliseStreetName(origEmployee: Employee): Employee =
    Employee.company
      .composeLens(Company.address)
      .composeLens(Address.street)
      .composeLens(Street.name)
      .modify(_.capitalize)(origEmployee)

  def updateStreetName(origEmployee: Employee, newStreetName: String): Employee =
    Employee.company
      .composeLens(Company.address)
      .composeLens(Address.street)
      .composeLens(Street.name)
      .set(newStreetName)(origEmployee)

  def updateStreetNum(origEmployee: Employee, newStreetNum: Int): Employee =
    Employee.company
      .composeLens(Company.address)
      .composeLens(Address.street)
      .composeLens(Street.number)
      .set(newStreetNum)(origEmployee)

  def seriesUpdate(origEmployee: Employee, newStreetNum: Int, newCompanyName: String): Employee = {
    val employee2 = Employee.company
      .composeLens(Company.address)
      .composeLens(Address.street)
      .composeLens(Street.number)
      .set(newStreetNum)(origEmployee)
    Employee.company.composeLens(Company.name).set(newCompanyName)(employee2)

    // alternatively can be written as
//    (
// Employee.company.composeLens(Company.address).composeLens(Address.street).composeLens(Street.number).set(newStreetNum)
//      andThen Employee.company.composeLens(Company.name).set(newCompanyName)
//      )(origEmployee)
  }

}

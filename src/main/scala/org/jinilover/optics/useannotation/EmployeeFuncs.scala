package org.jinilover.optics.useannotation

object EmployeeFuncs {
  import monocle.macros.Lenses

  @Lenses
  final case class Street(number: Int, name: String)
  @Lenses
  final case class Address(city: String, street: Street)
  @Lenses
  final case class Company(name: String, address: Address)
  @Lenses
  final case class Employee(name: String, company: Company)

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

  def updateStreetNameAndNum(origEmployee: Employee, newStreetName: String, newStreetNum: Int): Employee = {
    val employee2 = Employee.company
      .composeLens(Company.address)
      .composeLens(Address.street)
      .composeLens(Street.name)
      .set(newStreetName)(origEmployee)
    Employee.company
      .composeLens(Company.address)
      .composeLens(Address.street)
      .composeLens(Street.number)
      .set(newStreetNum)(employee2)

    // alternatively can be written as
//    (
// Employee.company.composeLens(Company.address).composeLens(Address.street).composeLens(Street.number).set(newStreetNum)
//      andThen Employee.company.composeLens(Company.name).set(newCompanyName)
//      )(origEmployee)
  }

}

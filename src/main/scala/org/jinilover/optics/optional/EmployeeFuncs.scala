package org.jinilover.optics.optional

object EmployeeFuncs {
  import monocle.macros.Lenses

  @Lenses
  case class Street(number: Int, name: String)
  @Lenses
  case class Address(city: String, street: Street)
  @Lenses
  case class Company(name: String, address: Option[Address])
  @Lenses
  case class Employee(name: String, company: Company)

  import monocle.Optional
  val addressOpt = Optional[Company, Address](_.address)(addr => _.copy(address = Some(addr)))

  def capitaliseStreetName(origEmployee: Employee): Employee =
    Employee.company.composeOptional(addressOpt).composeLens(Address.street).composeLens(Street.name).modify(_.capitalize)(origEmployee)
}

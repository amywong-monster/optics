package org.jinilover.optics.optionalprisms


object EmployeeFuncs {
  import monocle.macros.Lenses

  @Lenses
  final case class Street(number: Int, name: String)
  // start of Address ADT
  sealed trait Address
  @Lenses
  final case class UkAddress(postTown: String, postcode: String, street: Street) extends Address
  @Lenses
  final case class AusAddress(state: String, postcode: Int, street: Street) extends Address
  // end of Address ADT
  @Lenses
  final case class Company(name: String, address: Option[Address])
  @Lenses
  final case class Employee(name: String, company: Company)

  // base on what we learnt from using Lens, Prism, Optional,
  // try to figure out how to update the `Street number` of an employee working in Australia
  def updateAusStreetNum(origEmployee: Employee, newStreetNum: Int): Employee = {
    import monocle.Optional
    import monocle.Prism
    import monocle.macros.GenPrism

    val addressOptional = Optional[Company, Address](_.address)(addr => _.copy(address = Some(addr)))
    val ausAddressP: Prism[Address, AusAddress] = GenPrism[Address, AusAddress]

    import monocle.syntax.all._
    origEmployee
      .applyOptional(
        Employee.company.composeOptional(addressOptional).composePrism(ausAddressP).composeLens(AusAddress.street).composeLens(Street.number)
      ).set(newStreetNum)
  }
}

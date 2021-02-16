package org.jinilover.optics.prisms

object EmployeeFuncs {
  import monocle.macros.Lenses

  @Lenses
  case class Street(number: Int, name: String)
  // start of Address ADT
  sealed trait Address
  @Lenses
  case class UkAddress(postTown: String, postcode: String, street: Street) extends Address
  @Lenses
  case class AusAddress(state: String, postcode: Int, street: Street) extends Address
  // end of Address ADT
  @Lenses
  case class Company(name: String, address: Address)
  @Lenses
  case class Employee(name: String, company: Company)

  import monocle.Prism
  import monocle.macros.GenPrism

  val ukAddressP: Prism[Address, UkAddress] = GenPrism[Address, UkAddress]
  val ausAddressP: Prism[Address, AusAddress] = GenPrism[Address, AusAddress]

  // try
  // Employee("john", Company("monster", AusAddress("nsw", 2000, Street(83, "clarence street"))))
  // Employee("john", Company("monster", UkAddress("london", "w5 5yz", Street(83, "clarence street"))))
  // in the following functions

  def capitaliseAusState(origEmployee: Employee): Employee =
    Employee.company.composeLens(Company.address).composePrism(ausAddressP).composeLens(AusAddress.state).modify(_.capitalize)(origEmployee)

  def updateUkStreetNumb(origEmployee: Employee, newStreetNum: Int): Employee =
    Employee.company.composeLens(Company.address).composePrism(ukAddressP).composeLens(UkAddress.street).composeLens(Street.number).set(newStreetNum)(origEmployee)

  def getAusPostcode(origEmployee: Employee): Option[Int] =
    Employee.company.composeLens(Company.address).composePrism(ausAddressP).composeLens(AusAddress.postcode).getOption(origEmployee)

  import monocle.syntax.all._
  def seriesUpdate(origEmployee: Employee, newStreetNum: Int, newState: String, newCompanyName: String): Employee =
    origEmployee
      .applyOptional( Employee.company.composeLens(Company.address).composePrism(ausAddressP).composeLens(AusAddress.street).composeLens(Street.number) ).set(newStreetNum)
      .applyOptional( Employee.company.composeLens(Company.address).composePrism(ausAddressP).composeLens(AusAddress.state) ).set(newState)
      .applyLens( Employee.company.composeLens(Company.name) ).set(newCompanyName)

}

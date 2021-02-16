package org.jinilover.optics.handwritelens

object EmployeeFuncs {
  case class Street(number: Int, name: String)
  case class Address(city: String, street: Street)
  case class Company(name: String, address: Address)
  case class Employee(name: String, company: Company)

  import monocle.Lens
  import monocle.macros.GenLens

  val companyL   : Lens[Employee, Company] = GenLens[Employee](_.company)
  val addressL   : Lens[Company , Address] = GenLens[Company](_.address)
  val streetL    : Lens[Address , Street]  = GenLens[Address](_.street)
  val streetNameL: Lens[Street  , String]  = GenLens[Street](_.name)
  val streetNumL : Lens[Street, Int] = GenLens[Street](_.number)

  // e.g. Employee("john", Company("awesome inc", Address("london", Street(23, "high street"))))
  def capitaliseStreetName(origEmployee: Employee): Employee =
    companyL.composeLens(addressL).composeLens(streetL).composeLens(streetNameL).modify(_.capitalize)(origEmployee)

  def updateStreetName(origEmployee: Employee, newStreetName: String): Employee =
    companyL.composeLens(addressL).composeLens(streetL).composeLens(streetNameL).set(newStreetName)(origEmployee)

  def updateStreetNum(origEmployee: Employee, newStreetNum: Int): Employee =
    companyL.composeLens(addressL).composeLens(streetL).composeLens(streetNumL).set(newStreetNum)(origEmployee)
}



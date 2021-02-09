package org.jinilover.optics.annotation

case class Street2(number: Int, name: String)
case class Address2(city: String, street: Street2)
case class Company2(name: String, address: Address2)
case class Employee2(name: String, company: Company2)

object EmployeeFuncs {

}

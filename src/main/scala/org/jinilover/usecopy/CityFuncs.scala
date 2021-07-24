package org.jinilover.usecopy

final case class City(name: String, area: Double, population: Double)

object CityFuncs {
  def updatePopulation(city: City): City =
    city.copy(population = city.population * 1.1)
  // this is the same as `City(city.name, city.area, city.population * 1.1)`
}

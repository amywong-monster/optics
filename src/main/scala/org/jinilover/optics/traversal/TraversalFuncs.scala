package org.jinilover.optics.traversal

object TraversalFuncs {
  import monocle.macros.Lenses

  @Lenses
  final case class CategoryFund(category: String, defaultAmount: Long, maxAmount: Long)
  @Lenses
  final case class District(districtName: String, categoryFund: CategoryFund)
  @Lenses
  final case class City(cityName: String, districts: List[District])
  @Lenses
  final case class Contribution(districtName: String, contributedAmount: Long)
  @Lenses
  final case class Bill(billName: String, contributions: List[Contribution])

  import monocle.Traversal
  // `Traversal.fromTraverse` implementation requires the first argument to be a `Traverse` type class
  // instance
  // where `Traverse` is declared in the cats library.
  // In this example, because the first argument is `List`,
  // an implicit `List` instance satisfying `Traverse` type class should be provided,
  // o.w. it will cause compilation error
  // `List` is commonly-used core scala type constructors s.t. it is provided by `import cats.implicits._`
  import cats.implicits._

  def each[A]: Traversal[List[A], A] = Traversal.fromTraverse[List, A]

  import monocle.syntax.all._
  // replace maxAmount of CategoryFund of EACH District in the District list of City by the input value
  // e.g. try origCity = City("sydney", List(District("wynyard", CategoryFund("art&culture", 300, 500)),
  // District("surry hills", CategoryFund("health", 400, 600)) ))
  // setMaxAmountsOfCityToSameValue(origCity, 999)
  def setMaxAmountsOfCityToSameValue(origCity: City, newMaxAmount: Long): City =
    origCity.applyTraversal {
      City.districts
        .composeTraversal(each[District])
        .composeLens(District.categoryFund)
        .composeLens(CategoryFund.maxAmount)
    }.set(newMaxAmount)

  // redistribute contributedAmount of EACH Contribution in the Contribution list of Bill
  def redistributeContributionAmountsOfBill(origBill: Bill, newTotalContributedAmount: Long): Bill = {
    val newContributions =
      redistributeValues(newTotalContributedAmount, Contribution.contributedAmount, origBill.contributions)
    origBill.copy(contributions = newContributions)
  }

  // redistribute maxAmount of CategoryFund of EACH District in the District list of City by the input value
  // e.g. try origCity = City("sydney", List(District("wynyard", CategoryFund("art&culture", 300, 500)),
  // District("surry hills", CategoryFund("health", 400, 600)) ))
  // redistributeMaxAmountsOfCity(origCity, 2200)
  def redistributeMaxAmountsOfCity(origCity: City, newTotalMaxAmount: Long): City = {
    val newDistricts = redistributeValues(
      newTotalMaxAmount,
      District.categoryFund.composeLens(CategoryFund.maxAmount),
      origCity.districts
    )
    origCity.copy(districts = newDistricts)
  }

  import monocle.Lens

  // e.g. redistributeLongs(List(1,2,3), 60) = List(10,20,30)
  def redistributeLongs(longs: List[Long], newTotalValue: Long): List[Long] =
    redistributeValues(newTotalValue, Lens.id[Long], longs)

  private def redistributeValues[A](
    newTotalValue: Long,
    theLens: Lens[A, Long],
    origItems: List[A]
  ): List[A] = {
    val origTotalValue = origItems.applyTraversal(each[A].composeLens(theLens)).getAll.sum

    if (newTotalValue == origTotalValue)
      origItems
    else
      origItems.applyTraversal(each[A].composeLens(theLens)).modify { origValue =>
        Math.round(origValue.toDouble * newTotalValue.toDouble / origTotalValue.toDouble)
      }
  }

}

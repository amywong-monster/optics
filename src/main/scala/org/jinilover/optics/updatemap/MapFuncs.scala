package org.jinilover.optics.updatemap

import monocle.{Lens, Optional}

object MapFuncs {
  val m1 = Map(
    "errors" ->
      List(
        Map(
          "errorCode" -> "INTERNAL_SERVER_ERROR",
          "message" -> "There has been a problem with your request, please contact UNO support or try again later"
        )
      )
  )

  val m2 = Map(
    "errors" ->
      List(
        Map(
          "errorCode" -> "INTERNAL_SERVER_ERROR",
          "message" -> "There has been a problem with your request, please contact UNO support or try again later",
          "parameter" -> ""
        ),
        Map(
          "errorCode" -> "USER_ALREADY_EXISTS_ERROR",
          "message" -> "user already exists, the personal info is something",
          "parameter" -> ""
        ),
        Map(
          "errorCode" -> "INTERNAL_SERVER_ERROR",
          "message" -> "There has been a problem with your request, please contact UNO support or try again later",
          "parameter" -> ""
        ),
        Map(
          "errorCode" -> "PARAMETER_MISSING",
          "message" -> "One or more key-value pairs for the required parameters are missing.",
          "parameter" -> "Water"
        ),
        Map(
          "errorCode" -> "USER_EMAIL_ALREADY_EXIST",
          "message" -> "user email already exists, the personal info is something",
          "parameter" -> ""
        )
      ),
    "dummy" ->
      List(
        Map("key" -> "value")
      )
  )

  val m3 = Map(
    "errors" ->
      List(
        Map(
          "errorCode" -> "USER_ALREADY_EXISTS_ERROR",
          "message" -> "user already exists, the personal info is something",
          "parameter" -> ""
        )
      )
  )

  import cats.implicits._
  import monocle.Traversal
  import monocle.function.At._
  import monocle.syntax.apply._

  private def each[A] = Traversal.fromTraverse[List, A]
  private def optionalList[A]: Optional[Option[List[A]], List[A]] =
    Optional[Option[List[A]], List[A]](identity)(xs => _ => Some(xs))

  val errorCodeKey = "errorCode"
  val piiErrorCodes = List("USER_ALREADY_EXISTS_ERROR", "USER_EMAIL_ALREADY_EXIST")

  /*
   * Try the sample maps above to see how they look like
   */
  def updateMap(m: Map[String, List[Map[String, String]]]) = {
    m.applyLens(at("errors"))
      .composeOptional(optionalList)
      .composeTraversal(each)
      .composeLens(Lens.id[Map[String, String]])
      .modify { origMap =>
        if (origMap.get(errorCodeKey).exists(piiErrorCodes.contains))
          origMap.get(errorCodeKey).map(errorCodeKey -> _).toMap
        else
          origMap
      }
  }
}

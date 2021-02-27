package au.com.cba.json.models

import enumeratum._
import scala.collection.immutable

sealed trait IngredientType extends EnumEntry

object IngredientType extends Enum[IngredientType] {

  def values: immutable.IndexedSeq[IngredientType] = findValues

  case object Natural extends IngredientType

  case object Artificial extends IngredientType

  //additional ingredient type added to prove extensibility
  case object Vegan extends IngredientType

}

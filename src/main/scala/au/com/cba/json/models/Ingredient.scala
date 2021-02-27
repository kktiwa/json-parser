package au.com.cba.json.models

sealed trait Ingredient {
  val name: String
}

case class NaturalIngredient(name: String) extends Ingredient

case class ArtificialIngredient(name: String) extends Ingredient

//additional ingredient added to prove extensibility
case class VeganIngredient(name: String) extends Ingredient

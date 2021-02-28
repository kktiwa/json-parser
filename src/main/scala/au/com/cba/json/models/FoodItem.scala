package au.com.cba.json.models

sealed trait FoodItem {
  val price: Double
}

case class Candy(price: Double,
                 ingredients: Set[Ingredient]
                ) extends FoodItem

//Additional food item type added to show building of newer data types
case class Fruit(price: Double,
                 nutrients: Seq[String] //kept as a String type for simplicity. Ideally should be Enums for different nutrients
                ) extends FoodItem
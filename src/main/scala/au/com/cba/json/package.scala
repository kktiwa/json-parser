package au.com.cba

import au.com.cba.json.models._
import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._
import cats.syntax.functor._
import io.circe.syntax._

//This package object holds all encoders/decoders for different types
//Add new case blocks when introducing new types
package object codecs {

  implicit val encodeFoodItem: Encoder[FoodItem] = Encoder.instance {
    case candy@Candy(_, _) => candy.asJson
    case fruit@Fruit(_, _) => fruit.asJson
  }

  implicit val decodeFoodItem: Decoder[FoodItem] =
    List[Decoder[FoodItem]](
      Decoder[Fruit].widen,
      Decoder[Candy].widen
    ).reduceLeft(_ or _)

}

package au.com.cba.json.parser

import au.com.cba.json.models._
import io.circe.config.parser
import au.com.cba.codecs._

object JsonParser {

  def parse(jsonString: String): Either[DecodingError, FoodItem] = {

    parser.decode(jsonString)(decodeFoodItem) match {
      case Right(foodItem) => Right(foodItem)
      case Left(ex) => Left(DecodingError(ex, ex.getMessage))
    }
  }
}

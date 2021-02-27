package au.com.cba.json.parser

import au.com.cba.json.models._
import org.scalatest.{EitherValues, FlatSpec, Inside, Matchers}

class JsonParserSpec extends FlatSpec with Matchers
  with EitherValues
  with Inside {

  "parser" should "parse a valid json string with price attribute" in {
    val jsonString =
      """
        |{
        |  "price": 3.14,
        |  "ingredients": [
        |
        |  ]
        |}
        |""".stripMargin

    val result = JsonParser.parse(jsonString)
    inside(result.right.get) {
      case Candy(price, _) => price shouldBe 3.14
    }
  }

  it should "parse natural ingredients correctly" in {
    val jsonString =
      """
        |{
        |  "price": 3.5,
        |  "ingredients": [
        |    {
        |      "NaturalIngredient": {
        |        "name": "natural"
        |      }
        |    }
        |  ]
        |}
        |""".stripMargin

    val result = JsonParser.parse(jsonString)
    inside(result.right.get) {
      case Candy(_, ingredients) => ingredients.head shouldBe a[NaturalIngredient]
    }
  }

  it should "parse artificial ingredients correctly" in {
    val jsonString =
      """
        |{
        |  "price": 3.5,
        |  "ingredients": [
        |    {
        |      "ArtificialIngredient": {
        |        "name": "artificial"
        |      }
        |    }
        |  ]
        |}
        |""".stripMargin

    val result = JsonParser.parse(jsonString)
    inside(result.right.get) {
      case Candy(_, ingredients) => ingredients.head shouldBe a[ArtificialIngredient]
    }
  }

  it should "parse a combination of artificial and natural ingredients correctly" in {
    val jsonString =
      """
        |{
        |  "price": 3.5,
        |  "ingredients": [
        |    {
        |      "ArtificialIngredient": {
        |        "name": "artificial"
        |      }
        |    },
        |    {
        |      "NaturalIngredient": {
        |        "name": "natural"
        |      }
        |    }
        |  ]
        |}
        |""".stripMargin

    val result = JsonParser.parse(jsonString)
    inside(result.right.get) {
      case Candy(_, ingredients) =>
        ingredients should contain theSameElementsAs Set(ArtificialIngredient("artificial"), NaturalIngredient("natural"))
    }
  }

  it should "fail when name attribute is invalid" in {
    val jsonString =
      """
        |{
        |  "price": 3.5,
        |  "ingredients": [
        |    {
        |      "ArtificialIngredient": {
        |        "invalid": ""
        |      }
        |    },
        |    {
        |      "NaturalIngredient": {
        |        "invalid": ""
        |      }
        |    }
        |  ]
        |}
        |""".stripMargin

    val result = JsonParser.parse(jsonString)
    result.isLeft shouldBe true
  }

  it should "parse new ingredient types correctly" in {
    val jsonString =
      """
        |{
        |  "price": 3.5,
        |  "ingredients": [
        |    {
        |      "ArtificialIngredient": {
        |        "name": "artificial"
        |      }
        |    },
        |    {
        |      "NaturalIngredient": {
        |        "name": "natural"
        |      }
        |    },
        |    {
        |      "VeganIngredient": {
        |        "name": "vegan"
        |      }
        |    }
        |  ]
        |}
        |""".stripMargin

    val result = JsonParser.parse(jsonString)
    inside(result.right.get) {
      case Candy(_, ingredients) =>
        ingredients should contain theSameElementsAs Set(ArtificialIngredient("artificial"), NaturalIngredient("natural"), VeganIngredient("vegan"))
    }
  }

  it should "parse new food types correctly" in {
    val jsonString =
      """
        |{
        |  "price": 3.5,
        |  "nutrients": [
        |    "iron",
        |    "calcium"
        |  ]
        |}
        |""".stripMargin
    val result = JsonParser.parse(jsonString)
    inside(result.right.get) {
      case Fruit(price, nutrients) =>
        price shouldBe 3.5
        nutrients should contain theSameElementsAs Seq("iron", "calcium")
    }
  }

}

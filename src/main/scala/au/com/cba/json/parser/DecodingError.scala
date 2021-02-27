package au.com.cba.json.parser

case class DecodingError(error: Throwable,
                         message: String
                        )

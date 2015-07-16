package models

trait Content {
	def id : Int
	def name: String
}

case class Category(val id: Int, val name: String) extends Content {
	var products : Option[List[Product]] = None
	def getProducts() : List[Product] = {
		products match {
			case Some(list) => list
			case None | Some(Nil) => Nil
		}
		
	}
}

case class Product(val id: Int, val name: String, val categoryId: Int) extends Content 
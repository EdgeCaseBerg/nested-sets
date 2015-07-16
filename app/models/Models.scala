package models

trait Content {
	def id : Int
	def name: String
}

case class Category(val id: Int, val name: String) extends Content {
	var products : Option[List[Product]] = None
	var depth : Int = _
	var productCount : Int = _

	/* Allow setting of depth */
	def this(id: Int, name: String, depth: Int) = { 
		this(id, name)
		this.depth = depth
	}

	/* Allow setting of product count */
	def this(id: Int, name: String, depth: Int, productCount: Int) = {
		this(id, name, depth)
		this.productCount = productCount
	}

	def getProducts() : List[Product] = {
		products match {
			case Some(list) => list
			case None | Some(Nil) => Nil
		}
	}

}

case class Product(val id: Int, val name: String, val categoryId: Int) extends Content 
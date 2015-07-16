package models

trait Content {
	def id : Int
	def name: String
}

case class Category(val id: Int, val name: String) extends Content

case class Product(val id: Int, val name: String, val categoryId: Int) extends Content 
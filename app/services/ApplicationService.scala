package services

import models._

import play.api.db._
import play.api.Play.current
 
import anorm._
import anorm.SqlParser._

/** Rather poorly named class, but that's ok, this is an example */
class ApplicationService {
	/** Simple Anorm Mapper for a Category row */
	val fullCategoryParser = {
		get[Int]("category_id") ~
		get[String]("name") map {
			case id ~ name => Category(id, name)
		}
	}

	val categoryWithDepthAndCountParser = {
		get[Int]("category_id") ~
		get[String]("name") ~ 
		get[Int]("depth") ~ 
		get[Int]("productCount") map {
			case id ~ name ~ depth ~ productCount => new Category(id, name, depth, productCount)
		}	
	}

	val fullProductParser = {
		get[Int]("product_id") ~
		get[String]("name") ~ 
		get[Int]("category_id") map {
			case id ~ name ~ categoryId => Product(id, name, categoryId)
		}
	}

	def getCategories() : List[Category]  = {
		DB.withConnection { implicit connection =>
			val categories : List[Category] = SQL(
				"""
				SELECT a.category_id, a.name, depth, productCount FROM 
				(
					SELECT node.category_id, node.name, (COUNT(parent.name) - 1) AS depth
					FROM nested_category AS node,
					nested_category AS parent
					WHERE node.lft BETWEEN parent.lft AND parent.rgt
					GROUP BY node.name
					ORDER BY node.lft
				) a JOIN
				(
					SELECT parent.category_id, parent.name, COUNT(product.name) as productCount
					FROM nested_category AS node ,
					nested_category AS parent,
					product
					WHERE node.lft BETWEEN parent.lft AND parent.rgt
					AND node.category_id = product.category_id
					GROUP BY parent.name
					ORDER BY node.lft
				) b ON a.category_id = b.category_id
				"""
	    ).as(categoryWithDepthAndCountParser.*)
      categories
		}
	}

	def getProductsInCategory(categoryId: Int) : List[Product] = {
		DB.withConnection { implicit connection =>
			val categories = SQL(
				"""	
				SELECT product_id, product.name, product.category_id 
				FROM nested_category AS node,
				nested_category AS parent,
				product
				WHERE node.lft BETWEEN parent.lft AND parent.rgt
				AND parent.category_id = {category}
				AND product.category_id = node.category_id
				ORDER BY node.lft
				"""
			).on("category" -> categoryId).as(fullProductParser.*)
			categories
		}
	}
	


}
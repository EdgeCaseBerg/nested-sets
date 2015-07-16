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

	val categoryWithDepthParser = {
		get[Int]("category_id") ~
		get[String]("name") ~ 
		get[Int]("depth") map {
			case id ~ name ~ depth => new Category(id, name, depth)
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
				SELECT node.category_id, node.name, (COUNT(parent.name) - 1) AS depth
				FROM nested_category AS node,
				nested_category AS parent
				WHERE node.lft BETWEEN parent.lft AND parent.rgt
				GROUP BY node.category_id
				ORDER BY node.lft;
				"""
	    ).as(categoryWithDepthParser.*)
      categories
		}
	}

	def getAllInCategory(categoryId: Int) : List[Category] = {
		DB.withConnection { implicit connection =>
			val categories = SQL(
				"""	SELECT node.category_id, node.name
						FROM nested_category AS node,
						nested_category AS parent
						WHERE node.lft BETWEEN parent.lft AND parent.rgt
						AND parent.category_id = {category}
						ORDER BY node.lft;
				"""
			).on("category" -> categoryId).as(fullCategoryParser.*)
			categories
		}
	}
	


}
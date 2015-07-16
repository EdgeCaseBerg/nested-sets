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
				"""SELECT category_id, name FROM nested_category"""
	    ).as(fullCategoryParser.*)
      categories
		}
	}

	def getAllInCategory(categoryName: String) : List[Category] = {
		DB.withConnection { implicit connection =>
			/* Note that querying by name means duplicate tags might be a bit off */
			val categories = SQL(
				"""	SELECT node.category_id, node.name
						FROM nested_category AS node,
						nested_category AS parent
						WHERE node.lft BETWEEN parent.lft AND parent.rgt
						AND parent.name = {category}
						ORDER BY node.lft;
				"""
			).on("category" -> categoryName).as(fullCategoryParser.*)
			categories
		}
	}
	


}
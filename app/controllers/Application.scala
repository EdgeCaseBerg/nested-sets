package controllers

import play.api._
import play.api.mvc._

import services._
import models._

class Application extends Controller {
	val applicationService = new ApplicationService()
	def index = Action {
		val cats = applicationService.getCategories()
		Ok(cats.mkString(","))
	}

	def subtree(categoryName: String) = Action {
		val cats = applicationService.getAllInCategory(categoryName)
		Ok(cats.mkString(","))
	}
}
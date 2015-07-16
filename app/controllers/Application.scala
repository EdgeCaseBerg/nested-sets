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

	def subtree(categoryId: Int) = Action {
		val cats = applicationService.getAllInCategory(categoryId)
		Ok(cats.mkString(","))
	}
}
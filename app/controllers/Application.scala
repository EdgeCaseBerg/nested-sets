package controllers

import play.api._
import play.api.mvc._

import services._
import models._

class Application extends Controller {
	val applicationService = new ApplicationService()
	def index = Action {
		val cats = applicationService.getCategories()
		Ok(views.html.index(cats))
	}

	def subtree(categoryId: Int) = Action {
		val prods = applicationService.getProductsInCategory(categoryId)
		Ok(views.html.templates.contentList(prods))
	}
}
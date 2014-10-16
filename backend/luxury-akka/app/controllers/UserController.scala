package controllers

import controllers.core.SecuredController

class UserController extends SecuredController()
{
  def create = AuthenticatedAction(parse.json) {
    request =>
      Ok("")
  }
}

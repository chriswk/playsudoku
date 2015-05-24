package controllers

import play.api._
import play.api.mvc._
object Sudoku extends Controller {

  val board = Action {
    Ok(views.html.board())
  }
}

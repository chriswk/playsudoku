package controllers

import actors.BoardGeneratorActor
import akka.actor.Props
import play.api.libs.concurrent.Akka
import play.api.libs.json.Json
import sudoku._
import sudoku.Generator
import sudoku.StoredBoards
import play.api._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext



import scala.util.Random

object Sudoku extends Controller {
  val boardGen = Akka.system.actorOf(Props[BoardGeneratorActor], name = "boards")
  val board = Action {
    Ok(views.html.board())
  }


  def shuffleDifficulty(): Difficulty = Random.nextInt(3) match {
      case 0 => Easy
      case 1 => Medium
      case 2 => Hard
  }
  
  def random(difficulty: Option[String] = None) = Action.async {
      val board = difficulty match {
          case Some(diff) => diff match {
              case "easy" => StoredBoards.getRandomBoard(Easy)
              case "medium" => StoredBoards.getRandomBoard(Medium)
              case "hard" => StoredBoards.getRandomBoard(Hard)
          }
          case None => StoredBoards.getRandomBoard(shuffleDifficulty())
      }
      board.map { b =>
        Json.toJson(Map("board" -> b.toIdString))
      }.map { r =>
        Ok(r)
      }
  }
  def indexBoards(boardCount: Option[Int]) = Action {
    boardCount match {
      case Some(c) => boardGen ! actors.GenerateBoards(c)
      case None => boardGen ! actors.GenerateBoards(10)
    }
    Ok
  }
}
package controllers

import actors.BoardGeneratorActor
import akka.actor.Props
import play.api.libs.concurrent.Akka
import play.api.libs.json.Json
import sudoku._
import sudoku.Generator
import play.api._
import play.api.mvc._
import play.api.Play.current

import scala.util.Random

object Sudoku extends Controller {
  val boardGen = Akka.system.actorOf(Props[BoardGeneratorActor], name = "boards")
  val board = Action {
    Ok(views.html.board())
  }

  def shuffle() = Random.nextInt(3) match {
    case 0 => random("easy")
    case 1 => random("medium")
    case 2 => random("hard")
  }

  def random(difficulty: String, placements: Option[Int] = None) = Action {
    val puzzle: Option[GraphColouringProblem] = difficulty.toLowerCase match {
      case "easy" => {
        placements match {
          case Some(maxPlacements) => Generator.generateEasyPuzzle(maxPlacements)
          case None => Generator.generateEasyPuzzle()
        }
      }
      case "medium" => {
        placements match {
          case Some(maxPlacements) => Generator.generateMediumPuzzle(maxPlacements)
          case None => Generator.generateMediumPuzzle()
        }
      }
      case "hard" => {
        placements match {
          case Some(maxPlacements) => Generator.generateHardPuzzle(maxPlacements)
          case None => Generator.generateHardPuzzle()
        }
      }
    }
    puzzle match {
      case Some(p) => {
        val solution = Solver.apply(p)
        Ok(Json.toJson(Map("board" -> p.toBoard.toIdString, "solution" -> solution.headOption.map(_.toBoard.toIdString).getOrElse(""))))
      }
      case None => NoContent
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
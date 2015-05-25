package actors

import java.time.Instant

import akka.actor.{Actor, ActorLogging}
import com.sksamuel.elastic4s.source.{DocumentMap, DocumentSource}
import com.typesafe.config.{ConfigFactory, Config}
import play.api.libs.json.Json
import sudoku._
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._

case class SudokuPuzzle(md5: String, idstring: String, created: Long,
                        numPlacings: Int, difficulty: Difficulty,
                        md5Solution: Option[String] = None,
                        idStringSolution: Option[String] = None) extends DocumentMap {
  def map = Map(
    "md5" -> md5,
    "idString" -> idstring,
    "created" -> created,
    "numPlacings" -> numPlacings,
    "difficulty" -> difficulty,
    "md5Solution" -> md5Solution.getOrElse(""),
    "idStringSolution" -> idStringSolution.getOrElse("")
  )
}

case class Solution(md5: String, idString: String) extends DocumentMap {
  def map = Map(
    "md5" -> md5,
    "idString" -> idString
  )
}

class IndexerActor extends Actor with ActorLogging {
  val conf = ConfigFactory.load()
  val esUrl = conf.getString("elasticsearch.url")
  val client = ElasticClient.remote(esUrl, 9300)
  implicit val solution = Json.format[Solution]
  def receive = {
    case IndexBoard(graph) => {
      val board = graph.toBoard
      val solution = Solver.apply(graph).headOption
      val tempIndex = SudokuPuzzle(board.md5, board.toIdString, Instant.now().toEpochMilli, graph.numPlacings, Difficulty(graph))
      val toIndex = solution match {
        case Some(sol) => {
          val solvedBoard = sol.toBoard
          client execute {
            index into "sudoku" / "solution" doc Solution(solvedBoard.md5, solvedBoard.toIdString)
          }
          tempIndex.copy(md5Solution = Some(solvedBoard.md5))
            .copy(idStringSolution = Some(solvedBoard.toIdString))
        }
        case None => tempIndex
      }
      client.execute {
        index into "sudoku" / "puzzle" doc toIndex
      }
    }
  }
}

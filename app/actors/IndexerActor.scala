package actors

import java.time.Instant

import akka.actor.{Actor, ActorLogging}
import com.sksamuel.elastic4s.source.{DocumentMap, DocumentSource}
import com.typesafe.config.{ConfigFactory, Config}
import sudoku._
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._

case class SudokuPuzzle(md5: String, idstring: String, created: Long,
                        numPlacings: Int, difficulty: Int,
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

class IndexerActor extends Actor with ActorLogging {
  val conf = ConfigFactory.load()
  val esUrl = conf.getString("elasticsearch.url")
  val client = ElasticClient.remote(esUrl, 9300)

  def receive = {
    case IndexBoard(graph) => {
      val board = graph.toBoard
      val solution = Solver.apply(graph).headOption
      val tempIndex = SudokuPuzzle(board.md5, board.toIdString, Instant.now().toEpochMilli, graph.numPlacings, Difficulty.difficulty(graph))
      val toIndex = solution match {
        case Some(sol) => {
          val solvedBoard = sol.toBoard
          tempIndex.copy(md5Solution = Some(solvedBoard.md5))
            .copy(idStringSolution = Some(solvedBoard.toIdString))
        }
        case None => tempIndex
      }
      client.execute {
        index into "puzzles" / "sudoku" doc toIndex
      }
    }
  }
}

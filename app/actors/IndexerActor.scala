package actors

import akka.actor.{Actor, ActorLogging}
import com.typesafe.config.{ConfigFactory, Config}
import sudoku.{Difficulty, Board, GraphColouringProblem}
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._

class IndexerActor extends Actor with ActorLogging {
  val conf = ConfigFactory.load()
  val esUrl = conf.getString("elasticsearch.url")
  val client = ElasticClient.remote(esUrl, 9200)
  def receive = {
    case IndexBoard(graph, seed) => {
      val board = graph.toBoard
      log.info(s"md5: [${board.md5}], idString: [${board.toIdString}}], difficulty: ${Difficulty.difficulty(graph)}")
    }

  }
}

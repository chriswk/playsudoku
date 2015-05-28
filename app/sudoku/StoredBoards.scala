package sudoku

import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import com.typesafe.config.{ConfigFactory, Config}
import play.api.Logger
import scala.concurrent._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object StoredBoards {
  val conf = ConfigFactory.load()
  val esUrl = conf.getString("elasticsearch.url")
  val client = ElasticClient.local

  def getRandomBoard(difficulty: Difficulty):Future[Board] = {
      client execute {
          search in "sudoku" -> "puzzles" query {
            termQuery("difficulty", difficulty)              
          } limit 1
     } map { resp => 
        Board.apply(resp.hits.head.field("idstring").value.toString, true) 
     }
     
  }
}
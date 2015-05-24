package actors

import akka.actor.{Props, Actor, ActorLogging}
import sudoku._

import scala.util.Random


class BoardGeneratorActor extends Actor with ActorLogging {
  val indexer = context.actorOf(Props[IndexerActor])
  def receive = {
    case GenerateBoard(difficulty, numberOfPlacements) => difficulty match {
      case Easy => indexer ! Generator.generateEasyPuzzle().head
      case Medium => indexer ! Generator.generateMediumPuzzle().head
      case Hard => indexer ! Generator.generateHardPuzzle().head
    }
    case GenerateBoards(noOfBoards) => {
      for (i <- 1 to noOfBoards) {
        Random.nextInt(3) match {
          case 0 => self ! GenerateBoard(Easy)
          case 1 => self ! GenerateBoard(Medium)
          case 2 => self ! GenerateBoard(Hard)
        }
      }
    }
  }
}

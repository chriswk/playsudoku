import sudoku.{Board, GraphColouringProblem, Difficulty}

package object actors {
  case class GenerateBoards(noOfBoards: Int)
  case class GenerateBoard(difficulty: Difficulty, numberOfPlacements: Option[Int] = None)
  case class IndexBoard(graph: GraphColouringProblem, seed: Option[Board] = None)
  case class CreateBoardIndex()
}

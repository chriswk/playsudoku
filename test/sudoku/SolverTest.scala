package sudoku

import play.api.test.PlaySpecification

object SolverTest extends PlaySpecification with TestBoards {
  "Solver" should {
    "solve sudoku2054easy" in {
      val solution = Solver(sudoku2054easy).headOption
      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue
    }
  }
}

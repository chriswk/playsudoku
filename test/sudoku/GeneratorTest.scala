package sudoku

import play.api.test.PlaySpecification

object GeneratorTest extends PlaySpecification with TestBoards {

  "Generator" should {
    "generate an easy puzzle" in {
      val puzzle = Generator.generateEasyPuzzle(graphColouringProblem = Solver(sudoku2060easy).next())
      puzzle.isDefined must beTrue
      Difficulty(puzzle.get) must equalTo(Easy)
      Difficulty.isPermitted(puzzle.get) must beTrue
    }

    /*"generate an easy puzzle with <= 27 values" in {
      val puzzle = Generator.generateEasyPuzzle(27, Solver(sudoku2060easy).next())
      puzzle.isDefined must beTrue
      puzzle.get.numPlacings must be lessThanOrEqualTo 27
      Difficulty.isPermitted(puzzle.get) must beTrue
    }

    "generate a medium puzzle" in {
      val puzzle = Generator.generateMediumPuzzle(graphColouringProblem = Solver(sudoku2055medium).next())
      puzzle.isDefined must beTrue
      val problem = puzzle.get
      Difficulty(problem) must be_===(Medium)
      Difficulty.isPermitted(problem) must beTrue
    }

    "generate a hard puzzle" in {
      val puzzle = Generator.generateHardPuzzle(graphColouringProblem = Solver(sudoku2057hard).next())
      puzzle.isDefined must beTrue
      val problem = puzzle.get
      Difficulty(problem) must be_===(Hard)
      Difficulty.isPermitted(problem) must beTrue
    }*/
  }
}

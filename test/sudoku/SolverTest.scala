package sudoku

import play.api.test.PlaySpecification

object SolverTest extends PlaySpecification with TestBoards {
  "Solver" should {
    "solve sudoku2054easy" in {
      val solution = Solver(sudoku2054easy).headOption
      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2054easy_solution)
    }

    "solve sudoku2055medium" in {
      val solution = Solver(sudoku2055medium).headOption
      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue
      solution.get must be_===(sudoku2055medium_solution)
    }

    "solve sudoku2056medium" in {
      val solution = Solver(sudoku2056medium).headOption
      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue
      solution.get must be_===(sudoku2056medium_solution)
    }

    "solve sudoku2057hard" in {
      val solution = Solver(sudoku2057hard).headOption

      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2057hard_solution)
    }

    "solve sudoku2058hard" in {
      val solution = Solver(sudoku2058hard).headOption

      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2058hard_solution)
    }

    "solve sudoku2059hard" in {
      val solution = Solver(sudoku2059hard).headOption

      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2059hard_solution)
    }

    "solve sudoku2060easy" in {
      val solution = Solver(sudoku2060easy).headOption

      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2060easy_solution)
    }
    "solve sudoku2061medium" in {
      val solution = Solver(sudoku2061medium).headOption

      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2061medium_solution)
    }

    "solve sudoku2062medium" in {
      val solution = Solver(sudoku2062medium).headOption

      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2062medium_solution)
    }
    "solve sudoku2063hard" in {
      val solution = Solver(sudoku2063hard).headOption

      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2063hard_solution)
    }
    "solve sudoku2064hard" in {
      val solution = Solver(sudoku2064hard).headOption

      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2064hard_solution)
    }
    "solve sudoku2065hard" in {
      val solution = Solver(sudoku2065hard).headOption

      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2065hard_solution)
    }
    "solve sudoku2066easy" in {
      val solution = Solver(sudoku2066easy).headOption

      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2066easy_solution)
    }

    "solve sudoku2067medium" in {
      val solution = Solver(sudoku2067medium).headOption

      solution.isDefined must beTrue
      solution.get.valid must beTrue
      solution.get.solved must beTrue

      solution.get must be_===(sudoku2067medium_solution)
    }
    "determine unique solutions" in {
      Solver(sudoku2054easy).drop(1).headOption must be_===(None)
      Solver(sudoku2055medium).drop(1).headOption must be_===(None)
      Solver(sudoku2056medium).drop(1).headOption must be_===(None)
      Solver(sudoku2057hard).drop(1).headOption must be_===(None)
      Solver(sudoku2058hard).drop(1).headOption must be_===(None)
      Solver(sudoku2059hard).drop(1).headOption must be_===(None)
      Solver(sudoku2060easy).drop(1).headOption must be_===(None)
      Solver(sudoku2061medium).drop(1).headOption must be_===(None)
      Solver(sudoku2062medium).drop(1).headOption must be_===(None)
      Solver(sudoku2063hard).drop(1).headOption must be_===(None)
      Solver(sudoku2064hard).drop(1).headOption must be_===(None)
      Solver(sudoku2065hard).drop(1).headOption must be_===(None)
      Solver(sudoku2066easy).drop(1).headOption must be_===(None)
      Solver(sudoku2067medium).drop(1).headOption must be_===(None)
    }

    "determine multiple solutions" in {
      val underconstrained = Board("""
             1 2 3 | _ _ _ | _ _ _
             4 5 6 | _ _ _ | _ _ _
             7 8 9 | _ _ _ | _ _ _
            -----------------------
             _ _ _ | _ _ _ | _ _ _
             _ _ _ | _ _ _ | _ _ _
             _ _ _ | _ _ _ | _ _ _
            -----------------------
             _ _ _ | _ _ _ | _ _ _
             _ _ _ | _ _ _ | _ _ _
             _ _ _ | _ _ _ | _ _ _
                                   """).toGraphColouringProblem

      (Solver(underconstrained) take 10).size must be_===(10)
    }
  }
}

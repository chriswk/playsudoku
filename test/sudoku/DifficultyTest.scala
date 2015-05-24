package sudoku

import play.api.test.PlaySpecification

object DifficultyTest extends PlaySpecification with TestBoards {
  "Difficulty" should {
    "determine easy boards" in {
      Difficulty(sudoku2054easy) must be_===(Easy)
      Difficulty(sudoku2055medium) must not be_=== Easy
      Difficulty(sudoku2056medium) must not be_=== Easy
      Difficulty(sudoku2057hard) must not be_=== Easy
      Difficulty(sudoku2058hard) must not be_=== Easy
      Difficulty(sudoku2059hard) must not be_=== Easy
      Difficulty(sudoku2060easy) must be_===(Easy)
      Difficulty(sudoku2061medium) must not be_=== Easy
      Difficulty(sudoku2062medium) must not be_=== Easy
      Difficulty(sudoku2063hard) must not be_=== Easy
      Difficulty(sudoku2064hard) must not be_=== Easy
      Difficulty(sudoku2065hard) must not be_=== Easy
      Difficulty(sudoku2066easy) must be_===(Easy)
      Difficulty(sudoku2067medium) must not be_=== Easy
    }

    "determine medium boards" in {
      Difficulty(sudoku2054easy) must not be_=== Medium
      Difficulty(sudoku2055medium) must be_===(Medium)
      Difficulty(sudoku2056medium) must be_===(Medium)
      Difficulty(sudoku2057hard) must not be_=== Medium
      Difficulty(sudoku2058hard) must not be_=== Medium
      Difficulty(sudoku2059hard) must not be_=== Medium
      Difficulty(sudoku2060easy) must not be_=== Medium
      Difficulty(sudoku2061medium) must be_===(Medium)
      Difficulty(sudoku2062medium) must be_===(Medium)
      Difficulty(sudoku2063hard) must not be_=== Medium
      Difficulty(sudoku2064hard) must not be_=== Medium
      Difficulty(sudoku2065hard) must not be_=== Medium
      Difficulty(sudoku2066easy) must not be_=== Medium
      Difficulty(sudoku2067medium) must be_===(Medium)
    }

    "determine hard boards" in {
      Difficulty(sudoku2054easy) must not be_=== Hard
      Difficulty(sudoku2055medium) must not be_=== Hard
      Difficulty(sudoku2056medium) must not be_=== Hard
      Difficulty(sudoku2057hard) must be_===(Hard)
      Difficulty(sudoku2058hard) must be_===(Hard)
      Difficulty(sudoku2059hard) must be_===(Hard)
      Difficulty(sudoku2060easy) must not be_=== Hard
      Difficulty(sudoku2061medium) must not be_=== Hard
      Difficulty(sudoku2062medium) must not be_=== Hard
      Difficulty(sudoku2063hard) must be_===(Hard)
      Difficulty(sudoku2064hard) must be_===(Hard)
      Difficulty(sudoku2065hard) must be_===(Hard)
      Difficulty(sudoku2066easy) must not be_=== Hard
      Difficulty(sudoku2067medium) must not be_=== Hard
    }

    "determine permitted boards" in {
      Difficulty.isPermitted(sudoku2054easy) must be_===(true)
      Difficulty.isPermitted(sudoku2055medium) must be_===(true)
      Difficulty.isPermitted(sudoku2056medium) must be_===(true)
      Difficulty.isPermitted(sudoku2057hard) must be_===(true)
      Difficulty.isPermitted(sudoku2058hard) must be_===(true)
      Difficulty.isPermitted(sudoku2059hard) must be_===(true)
      Difficulty.isPermitted(sudoku2060easy) must be_===(true)
      Difficulty.isPermitted(sudoku2061medium) must be_===(true)
      Difficulty.isPermitted(sudoku2062medium) must be_===(true)
      Difficulty.isPermitted(sudoku2063hard) must be_===(true)
      Difficulty.isPermitted(sudoku2064hard) must be_===(true)
      Difficulty.isPermitted(sudoku2065hard) must be_===(true)
      Difficulty.isPermitted(sudoku2066easy) must be_===(true)
      Difficulty.isPermitted(sudoku2067medium) must be_===(true)
    }
    "determine unpermitted boards" in {
      val notPermitted = Board("""
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

      Difficulty.isPermitted(notPermitted) must be_===(false)
    }
  }

}

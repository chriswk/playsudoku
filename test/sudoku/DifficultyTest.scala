package sudoku

import play.api.test.PlaySpecification

object DifficultyTest extends PlaySpecification with TestBoards {
  "Difficulty" should {
    "determine easy boards" in {
      Difficulty(sudoku2054easy) must equalTo(Easy)
      Difficulty(sudoku2055medium) must not equalTo(Easy)
      Difficulty(sudoku2056medium) must not equalTo(Easy)
      Difficulty(sudoku2057hard) must not equalTo(Easy)
      Difficulty(sudoku2058hard) must not equalTo(Easy)
      Difficulty(sudoku2059hard) must not equalTo(Easy)
      Difficulty(sudoku2060easy) must equalTo(Easy)
      Difficulty(sudoku2061medium) must not equalTo(Easy)
      Difficulty(sudoku2062medium) must not equalTo(Easy)
      Difficulty(sudoku2063hard) must not equalTo(Easy)
      Difficulty(sudoku2064hard) must not equalTo(Easy)
      Difficulty(sudoku2065hard) must not equalTo(Easy)
      Difficulty(sudoku2066easy) must equalTo(Easy)
      Difficulty(sudoku2067medium) must not equalTo(Easy)
    }

    "determine medium boards" in {
      Difficulty(sudoku2054easy) must not equalTo(Medium)
      Difficulty(sudoku2055medium) must equalTo(Medium)
      Difficulty(sudoku2056medium) must equalTo(Medium)
      Difficulty(sudoku2057hard) must not equalTo(Medium)
      Difficulty(sudoku2058hard) must not equalTo(Medium)
      Difficulty(sudoku2059hard) must not equalTo(Medium)
      Difficulty(sudoku2060easy) must not equalTo(Medium)
      Difficulty(sudoku2061medium) must equalTo(Medium)
      Difficulty(sudoku2062medium) must equalTo(Medium)
      Difficulty(sudoku2063hard) must not equalTo(Medium)
      Difficulty(sudoku2064hard) must not equalTo(Medium)
      Difficulty(sudoku2065hard) must not equalTo(Medium)
      Difficulty(sudoku2066easy) must not equalTo(Medium)
      Difficulty(sudoku2067medium) must equalTo(Medium)
    }

    "determine hard boards" in {
      Difficulty(sudoku2054easy) must not equalTo(Hard)
      Difficulty(sudoku2055medium) must not equalTo(Hard)
      Difficulty(sudoku2056medium) must not equalTo(Hard)
      Difficulty(sudoku2057hard) must equalTo(Hard)
      Difficulty(sudoku2058hard) must equalTo(Hard)
      Difficulty(sudoku2059hard) must equalTo(Hard)
      Difficulty(sudoku2060easy) must not equalTo(Hard)
      Difficulty(sudoku2061medium) must not equalTo(Hard)
      Difficulty(sudoku2062medium) must not equalTo(Hard)
      Difficulty(sudoku2063hard) must equalTo(Hard)
      Difficulty(sudoku2064hard) must equalTo(Hard)
      Difficulty(sudoku2065hard) must equalTo(Hard)
      Difficulty(sudoku2066easy) must not equalTo(Hard)
      Difficulty(sudoku2067medium) must not equalTo(Hard)
    }

    "determine permitted boards" in {
      Difficulty.isPermitted(sudoku2054easy) must equalTo(true)
      Difficulty.isPermitted(sudoku2055medium) must equalTo(true)
      Difficulty.isPermitted(sudoku2056medium) must equalTo(true)
      Difficulty.isPermitted(sudoku2057hard) must equalTo(true)
      Difficulty.isPermitted(sudoku2058hard) must equalTo(true)
      Difficulty.isPermitted(sudoku2059hard) must equalTo(true)
      Difficulty.isPermitted(sudoku2060easy) must equalTo(true)
      Difficulty.isPermitted(sudoku2061medium) must equalTo(true)
      Difficulty.isPermitted(sudoku2062medium) must equalTo(true)
      Difficulty.isPermitted(sudoku2063hard) must equalTo(true)
      Difficulty.isPermitted(sudoku2064hard) must equalTo(true)
      Difficulty.isPermitted(sudoku2065hard) must equalTo(true)
      Difficulty.isPermitted(sudoku2066easy) must equalTo(true)
      Difficulty.isPermitted(sudoku2067medium) must equalTo(true)
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

      Difficulty.isPermitted(notPermitted) must equalTo(false)
    }
  }

}

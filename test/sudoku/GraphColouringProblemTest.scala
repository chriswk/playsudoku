package sudoku

import play.api.test.PlaySpecification

class GraphColouringProblemTest extends PlaySpecification with TestBoards {
  val emptyBoard = Board(
    Row.empty, Row.empty, Row.empty,
    Row.empty, Row.empty, Row.empty,
    Row.empty, Row.empty, Row.empty
  ).toGraphColouringProblem

  val partialBoard = Board( """
     1 _ _ | 2 _ _ | 3 _ _
     _ 4 _ | _ 5 _ | _ 6 _
     _ _ 7 | _ _ 8 | _ _ 9
    -----------------------
     2 _ _ | 3 _ _ | 1 _ _
     _ 5 _ | _ 6 _ | _ 4 _
     _ _ 8 | _ _ 9 | _ _ 7
    -----------------------
     3 _ _ | 1 _ _ | 2 _ _
     _ 6 _ | _ 4 _ | _ 5 _
     _ _ 9 | _ _ 7 | _ _ 8
                            """).toGraphColouringProblem

  val fullBoard = Board( """
     1 2 3 | 4 5 6 | 7 8 9
     4 5 6 | 7 8 9 | 1 2 3
     7 8 9 | 1 2 3 | 4 5 6
    -----------------------
     2 3 4 | 5 6 7 | 8 9 1
     5 6 7 | 8 9 1 | 2 3 4
     8 9 1 | 2 3 4 | 5 6 7
    -----------------------
     3 4 5 | 6 7 8 | 9 1 2
     6 7 8 | 9 1 2 | 3 4 5
     9 1 2 | 3 4 5 | 6 7 8
                         """).toGraphColouringProblem

  val invalidBoard = {
    // 1 1 _ | 2 _ _ | 3 _ _
    // _ 4 _ | _ 5 _ | _ 6 _
    // _ _ 7 | _ _ 8 | _ _ 9
    //-----------------------
    // 2 _ _ | 3 _ _ | 1 _ _
    // _ 5 _ | _ 6 _ | _ 4 _
    // _ _ 8 | _ _ 9 | _ _ 7
    //-----------------------
    // 3 _ _ | 1 _ _ | 2 _ _
    // _ 6 _ | _ 4 _ | _ 5 _
    // _ _ 9 | _ _ 7 | _ _ 8
    partialBoard.toBoard.copy(
      first = partialBoard.toBoard.first.copy(second = Some(One))
    ).toGraphColouringProblem
  }
  "GraphColuringProblem" should {
    "calculate graphs" in {
      // 6 9 8 | _ _ _ | _ _ _
      // 4 _ _ | 5 _ _ | _ 2 _
      // 3 _ _ | 6 _ _ | _ _ _
      //-----------------------
      // 5 6 1 | _ _ _ | _ _ _
      // _ _ _ | _ _ _ | _ _ _
      // _ _ _ | _ _ _ | 7 3 9
      //-----------------------
      // _ _ _ | _ _ 4 | _ _ 2
      // _ 3 _ | _ _ 7 | _ _ 6
      // _ _ _ | _ _ _ | 5 9 1

      val graph = sudoku2059hard
      graph(One, One).candidates must be_===(Set(Six))
      graph(Two, One).candidates must be_===(Set(Nine))
      graph(Three, One).candidates must be_===(Set(Eight))
      graph(Four, One).candidates must be_===(SudokuNumber.set)
      graph(Five, One).candidates must be_===(SudokuNumber.set)
      graph(Six, One).candidates must be_===(SudokuNumber.set)
      graph(Seven, One).candidates must be_===(SudokuNumber.set)
      graph(Eight, One).candidates must be_===(SudokuNumber.set)
      graph(Nine, One).candidates must be_===(SudokuNumber.set)

      graph(One, Two).candidates must be_===(Set(Four))
      graph(Two, Two).candidates must be_===(SudokuNumber.set)
      graph(Three, Two).candidates must be_===(SudokuNumber.set)
      graph(Four, Two).candidates must be_===(Set(Five))
      graph(Five, Two).candidates must be_===(SudokuNumber.set)
      graph(Six, Two).candidates must be_===(SudokuNumber.set)
      graph(Seven, Two).candidates must be_===(SudokuNumber.set)
      graph(Eight, Two).candidates must be_===(Set(Two))
      graph(Nine, Two).candidates must be_===(SudokuNumber.set)

      graph(One, Three).candidates must be_===(Set(Three))
      graph(Two, Three).candidates must be_===(SudokuNumber.set)
      graph(Three, Three).candidates must be_===(SudokuNumber.set)
      graph(Four, Three).candidates must be_===(Set(Six))
      graph(Five, Three).candidates must be_===(SudokuNumber.set)
      graph(Six, Three).candidates must be_===(SudokuNumber.set)
      graph(Seven, Three).candidates must be_===(SudokuNumber.set)
      graph(Eight, Three).candidates must be_===(SudokuNumber.set)
      graph(Nine, Three).candidates must be_===(SudokuNumber.set)

    }

    "reduce three element coverings" in {
      val graph = Board(
        """
       _ _ _ | 4 5 6 | 7 8 9   <<--- Three element covering in first zone.
	   _ _ _ | _ _ _ | _ _ _
       _ _ _ | _ _ _ | _ _ _
	  -----------------------
	   _ _ _ | _ _ _ | _ _ _
	   _ _ _ | _ _ _ | _ _ _
	   _ _ _ | _ _ _ | _ _ _
	  -----------------------
	   _ _ _ | _ _ _ | _ _ _
	   _ _ _ | _ _ _ | _ _ _
	   _ _ _ | _ _ _ | _ _ _
        """).toGraphColouringProblem.eliminateByLatinBlockExclusion()
      graph(One, One).candidates must be_===(Set(One, Two, Three))
      graph(Two, One).candidates must be_===(Set(One, Two, Three))
      graph(Three, One).candidates must be_===(Set(One, Two, Three))
      graph(One, Two).candidates must be_===(SudokuNumber.set)
      graph(Two, Two).candidates must be_===(SudokuNumber.set)
      graph(Three, Two).candidates must be_===(SudokuNumber.set)
      graph(One, Three).candidates must be_===(SudokuNumber.set)
      graph(Two, Three).candidates must be_===(SudokuNumber.set)
      graph(Three, Three).candidates must be_===(SudokuNumber.set)

      val reduced = graph.eliminateByThreeElementCoverings()

      reduced(One, One).candidates must be_===(Set(One, Two, Three))
      reduced(Two, One).candidates must be_===(Set(One, Two, Three))
      reduced(Three, One).candidates must be_===(Set(One, Two, Three))
      reduced(One, Two).candidates must be_===(SudokuNumber.set -- Set(One, Two, Three))
      reduced(Two, Two).candidates must be_===(SudokuNumber.set -- Set(One, Two, Three))
      reduced(Three, Two).candidates must be_===(SudokuNumber.set -- Set(One, Two, Three))
      reduced(One, Three).candidates must be_===(SudokuNumber.set -- Set(One, Two, Three))
      reduced(Two, Three).candidates must be_===(SudokuNumber.set -- Set(One, Two, Three))
      reduced(Three, Three).candidates must be_===(SudokuNumber.set -- Set(One, Two, Three))
    }
    "reduce using latin block single row placements" in {
      val graph = Board(
        """
	   _ _ _ | _ _ _ | _ _ _   <<--- Single placing in first column by row. 
	   _ _ _ | 1 _ _ | _ _ _
	   _ _ _ | _ _ _ | 1 _ _ 
	  -----------------------
	   _ 1 _ | _ _ _ | _ _ _ 
	   _ _ _ | _ _ _ | _ _ _ 
	   _ _ _ | _ _ _ | _ _ _ 
	  -----------------------
	   _ _ 1 | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _
        		""").toGraphColouringProblem.eliminateByLatinBlockExclusion()

      graph(One, One).candidates must be_===(SudokuNumber.set)
      graph(Two, One).candidates must be_===(SudokuNumber.set - One)
      graph(Three, One).candidates must be_===(SudokuNumber.set - One)
      graph(One, Two).candidates must be_===(SudokuNumber.set - One)
      graph(Two, Two).candidates must be_===(SudokuNumber.set - One)
      graph(Three, Two).candidates must be_===(SudokuNumber.set - One)
      graph(One, Three).candidates must be_===(SudokuNumber.set - One)
      graph(Two, Three).candidates must be_===(SudokuNumber.set - One)
      graph(Three, Three).candidates must be_===(SudokuNumber.set - One)

      val reduced = graph.singleEliminateByLatinBlockSingleRowPlacements()

      reduced(One, One).candidates must be_===(Set(One))
      reduced(Two, One).candidates must be_===(SudokuNumber.set - One)
      reduced(Three, One).candidates must be_===(SudokuNumber.set - One)
      reduced(One, Two).candidates must be_===(SudokuNumber.set - One)
      reduced(Two, Two).candidates must be_===(SudokuNumber.set - One)
      reduced(Three, Two).candidates must be_===(SudokuNumber.set - One)
      reduced(One, Three).candidates must be_===(SudokuNumber.set - One)
      reduced(Two, Three).candidates must be_===(SudokuNumber.set - One)
      reduced(Three,
        Three).candidates must be_===(SudokuNumber.set - One)
    }
    "reduce using latin block single column placements" in {
      val graph = Board(
        """
	   _ _ _ | _ _ _ | _ _ _   <<--- Single placing in first column by column. 
	   _ _ _ | 1 _ _ | _ _ _
	   _ _ _ | _ _ _ | 1 _ _ 
	  -----------------------
	   _ 1 _ | _ _ _ | _ _ _ 
	   _ _ _ | _ _ _ | _ _ _ 
	   _ _ _ | _ _ _ | _ _ _ 
	  -----------------------
	   _ _ 1 | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _
        		""").toGraphColouringProblem.eliminateByLatinBlockExclusion()

      graph(One, One).candidates must be_===(SudokuNumber.set)
      graph(Two, One).candidates must be_===(SudokuNumber.set - One)
      graph(Three, One).candidates must be_===(SudokuNumber.set - One)
      graph(One, Two).candidates must be_===(SudokuNumber.set - One)
      graph(Two, Two).candidates must be_===(SudokuNumber.set - One)
      graph(Three, Two).candidates must be_===(SudokuNumber.set - One)
      graph(One, Three).candidates must be_===(SudokuNumber.set - One)
      graph(Two, Three).candidates must be_===(SudokuNumber.set - One)
      graph(Three, Three).candidates must be_===(SudokuNumber.set - One)

      val reduced = graph.singleEliminateByLatinBlockSingleColumnPlacements()

      reduced(One, One).candidates must be_===(Set(One))
      reduced(Two, One).candidates must be_===(SudokuNumber.set - One)
      reduced(Three, One).candidates must be_===(SudokuNumber.set - One)
      reduced(One, Two).candidates must be_===(SudokuNumber.set - One)
      reduced(Two, Two).candidates must be_===(SudokuNumber.set - One)
      reduced(Three, Two).candidates must be_===(SudokuNumber.set - One)
      reduced(One, Three).candidates must be_===(SudokuNumber.set - One)
      reduced(Two, Three).candidates must be_===(SudokuNumber.set - One)
      reduced(Three, Three).candidates must be_===(SudokuNumber.set - One)
    }
    "reduce using latin block single zone placements" in {
      val graph = Board(
        """
	   _ _ _ | _ _ _ | _ _ _   <<--- Single placing in first column by zone. 
	   _ _ _ | 1 _ _ | _ _ _
	   _ _ _ | _ _ _ | 1 _ _ 
	  -----------------------
	   _ 1 _ | _ _ _ | _ _ _ 
	   _ _ _ | _ _ _ | _ _ _ 
	   _ _ _ | _ _ _ | _ _ _ 
	  -----------------------
	   _ _ 1 | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _ 
       _ _ _ | _ _ _ | _ _ _
        		""").toGraphColouringProblem.eliminateByLatinBlockExclusion()

      graph(One, One).candidates must be_===(SudokuNumber.set)
      graph(Two, One).candidates must be_===(SudokuNumber.set - One)
      graph(Three, One).candidates must be_===(SudokuNumber.set - One)
      graph(One, Two).candidates must be_===(SudokuNumber.set - One)
      graph(Two, Two).candidates must be_===(SudokuNumber.set - One)
      graph(Three, Two).candidates must be_===(SudokuNumber.set - One)
      graph(One, Three).candidates must be_===(SudokuNumber.set - One)
      graph(Two, Three).candidates must be_===(SudokuNumber.set - One)
      graph(Three, Three).candidates must be_===(SudokuNumber.set - One)

      val reduced = graph.singleEliminateByLatinBlockSingleZonePlacements()

      reduced(One, One).candidates must be_===(Set(One))
      reduced(Two, One).candidates must be_===(SudokuNumber.set - One)
      reduced(Three, One).candidates must be_===(SudokuNumber.set - One)
      reduced(One, Two).candidates must be_===(SudokuNumber.set - One)
      reduced(Two, Two).candidates must be_===(SudokuNumber.set - One)
      reduced(Three, Two).candidates must be_===(SudokuNumber.set - One)
      reduced(One, Three).candidates must be_===(SudokuNumber.set - One)
      reduced(Two, Three).candidates must be_===(SudokuNumber.set - One)
      reduced(Three, Three).candidates must be_===(SudokuNumber.set - One)
    }
    "return valid for empty board" in {
      emptyBoard.valid must beTrue
    }
    "return valid for partial board" in {
      partialBoard.valid must beTrue
    }
    "return valid for full board" in {
      fullBoard.valid must beTrue
    }
    "return invalid for invalid board" in {
      invalidBoard.valid must beFalse
    }
    "return unsolved for empty board" in {
      emptyBoard.solved must beFalse
    }
    "return unsolved for partial board" in {
      partialBoard.solved must beFalse
    }
    "return solved for full board" in {
      fullBoard.solved must beTrue
    }
    "return unsolved for invalid board" in {
      invalidBoard.solved must beFalse
    }


  }
}

package sudoku

import org.specs2.matcher.ShouldMatchers
import play.api.test.PlaySpecification

object RowTest extends PlaySpecification {
  val emptyRow = Row.empty
  val partialRow = Row(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
  val fullRow = Row(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
  val invalidRow = partialRow.copy(sixth = Some(Five))

  "Row" should {
    "deindex correctly for empty row" in {
      SudokuNumber.all map { emptyRow(_) } must be_===(List.fill(9)(None))
    }

    "deindex correctly for partial row" in {
      SudokuNumber.all.map { partialRow(_) } must be_===(
        List(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
      )
    }

    "deindex correctly for full row" in {
      SudokuNumber.all.map { fullRow(_) } must be_===(
        List(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
      )
    }

    "deindex correctly for invalid row" in {
      SudokuNumber.all.map { invalidRow(_) } must be_===(
        List(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine))
      )
    }

    "have equality test based on immutability" in {
      emptyRow must be_===(Row.empty)
      partialRow must be_===(Row(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
      fullRow must be_===(Row(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
      invalidRow must be_===(partialRow.copy(sixth = Some(Five)))
    }
  }

}

object ColumnTest extends PlaySpecification {
  val emptyColumn = Column(None, None, None, None, None, None, None, None, None)
  val partialColumn = Column(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
  val fullColumn = Column(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
  val invalidColumn = partialColumn.copy(sixth = Some(Five))

  "Column" should {
    "deindex correctly for empty column" in {
      SudokuNumber.all map {
        emptyColumn(_)
      } must be_===(
        List(None, None, None, None, None, None, None, None, None))
    }
    "deindex correctly for partial column" in {
      SudokuNumber.all map {
        partialColumn(_)
      } must be_===(
        List(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
    }

    "deindex correctly for full column" in {
      SudokuNumber.all map {
        fullColumn(_)
      } must be_===(
        List(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
    }

    "deindex correctly for invalid column" in {
      SudokuNumber.all map {
        invalidColumn(_)
      } must be_===(
        List(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
    }

    "have equality test based on immutability" in {
      emptyColumn must be_===(Column(None, None, None, None, None, None, None, None, None))
      partialColumn must be_===(Column(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
      fullColumn must be_===(Column(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
      invalidColumn must be_===(Column(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
    }
  }
}

object BoardTest extends PlaySpecification {
  val emptyBoard = {
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    //-----------------------
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    //-----------------------
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _
    // _ _ _ | _ _ _ | _ _ _

    Board(
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty,
      Row.empty)
  }

  val partialBoard = {
    // 1 _ _ | 2 _ _ | 3 _ _
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

    Board(
      Row(Some(One), None, None, Some(Two), None, None, Some(Three), None, None),
      Row(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
      Row(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

      Row(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
      Row(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
      Row(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

      Row(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
      Row(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
      Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight)))
  }

  val fullBoard = {
    // 1 2 3 | 4 5 6 | 7 8 9
    // 4 5 6 | 7 8 9 | 1 2 3
    // 7 8 9 | 1 2 3 | 4 5 6
    //-----------------------
    // 2 3 4 | 5 6 7 | 8 9 1
    // 5 6 7 | 8 9 1 | 2 3 4
    // 8 9 1 | 2 3 4 | 5 6 7
    //-----------------------
    // 3 4 5 | 6 7 8 | 9 1 2
    // 6 7 8 | 9 1 2 | 3 4 5
    // 9 1 2 | 3 4 5 | 6 7 8
    Board(
      Row(Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine)),
      Row(Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three)),
      Row(Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six)),

      Row(Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One)),
      Row(Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four)),
      Row(Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven)),

      Row(Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two)),
      Row(Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five)),
      Row(Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight)))
  }

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
    partialBoard.copy(first = partialBoard.first.copy(second = Some(One)))
  }
  val emptyIdString = List.fill(81)("_").mkString("")
  val partialIdString = "1__2__3___4__5__6___7__8__92__3__1___5__6__4___8__9__73__1__2___6__4__5___9__7__8"
  val fullIdString = "123456789456789123789123456234567891567891234891234567345678912678912345912345678"
  val invalidIdString = "11_2__3___4__5__6___7__8__92__3__1___5__6__4___8__9__73__1__2___6__4__5___9__7__8"


    "Board" should {
    "have apply notation" in {
      emptyBoard(One, Seven) must be_===(None)
      emptyBoard(Three, Three) must be_===(None)
      emptyBoard(Eight, Two) must be_===(None)

      partialBoard(One, Seven) must be_===(Some(Three))
      partialBoard(Three, Three) must be_===(Some(Seven))
      partialBoard(Eight, Two) must be_===(Some(Six))

      fullBoard(One, Seven) must be_===(Some(Three))
      fullBoard(Three, Three) must be_===(Some(Nine))
      fullBoard(Eight, Two) must be_===(Some(Two))

      invalidBoard(One, Seven) must be_===(Some(Three))
      invalidBoard(Three, Three) must be_===(Some(Seven))
      invalidBoard(Eight, Two) must be_===(Some(Six))
    }

    "return rows in empty board" in {
      SudokuNumber.all map { emptyBoard.row } must be_===(List(
        Row.empty,
        Row.empty,
        Row.empty,

        Row.empty,
        Row.empty,
        Row.empty,

        Row.empty,
        Row.empty,
        Row.empty)) 
    }

    "return rows in partial board" in {
      SudokuNumber.all map { partialBoard.row } must be_===(List(
        Row(Some(One), None, None, Some(Two), None, None, Some(Three), None, None),
        Row(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
        Row(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

        Row(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
        Row(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
        Row(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

        Row(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
        Row(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
        Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight)))
      )
    }

    "return rows in full board" in {
      SudokuNumber.all map { fullBoard.row } must be_===(List(
        Row(Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine)),
        Row(Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three)),
        Row(Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six)),

        Row(Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One)),
        Row(Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four)),
        Row(Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven)),

        Row(Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two)),
        Row(Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five)),
        Row(Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight))
      ))
    }

    "return rows in invalid board" in {
        SudokuNumber.all.map { invalidBoard.row } must be_===(List(
          Row(Some(One), Some(One), None, Some(Two), None, None, Some(Three), None, None),
          Row(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
          Row(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

          Row(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
          Row(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
          Row(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

          Row(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
          Row(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
          Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))
        ))
    }

    "return columns in empty board" in {
      SudokuNumber.all map { emptyBoard.column } must be_===(List(
        Column(None, None, None, None, None, None, None, None, None),
        Column(None, None, None, None, None, None, None, None, None),
        Column(None, None, None, None, None, None, None, None, None),

        Column(None, None, None, None, None, None, None, None, None),
        Column(None, None, None, None, None, None, None, None, None),
        Column(None, None, None, None, None, None, None, None, None),

        Column(None, None, None, None, None, None, None, None, None),
        Column(None, None, None, None, None, None, None, None, None),
        Column(None, None, None, None, None, None, None, None, None)
      ))
    }

    "return columns in partial board" in {
      SudokuNumber.all map { partialBoard.column } must be_===(List(
        Column(Some(One), None, None, Some(Two), None, None, Some(Three), None, None),
        Column(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
        Column(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

        Column(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
        Column(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
        Column(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

        Column(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
        Column(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
        Column(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))))
    }

    "return columns in full board" in {
      SudokuNumber.all map { fullBoard.column } must be_===(List(
        Column(Some(One), Some(Four), Some(Seven), Some(Two), Some(Five), Some(Eight), Some(Three), Some(Six), Some(Nine)),
        Column(Some(Two), Some(Five), Some(Eight), Some(Three), Some(Six), Some(Nine), Some(Four), Some(Seven), Some(One)),
        Column(Some(Three), Some(Six), Some(Nine), Some(Four), Some(Seven), Some(One), Some(Five), Some(Eight), Some(Two)),

        Column(Some(Four), Some(Seven), Some(One), Some(Five), Some(Eight), Some(Two), Some(Six), Some(Nine), Some(Three)),
        Column(Some(Five), Some(Eight), Some(Two), Some(Six), Some(Nine), Some(Three), Some(Seven), Some(One), Some(Four)),
        Column(Some(Six), Some(Nine), Some(Three), Some(Seven), Some(One), Some(Four), Some(Eight), Some(Two), Some(Five)),

        Column(Some(Seven), Some(One), Some(Four), Some(Eight), Some(Two), Some(Five), Some(Nine), Some(Three), Some(Six)),
        Column(Some(Eight), Some(Two), Some(Five), Some(Nine), Some(Three), Some(Six), Some(One), Some(Four), Some(Seven)),
        Column(Some(Nine), Some(Three), Some(Six), Some(One), Some(Four), Some(Seven), Some(Two), Some(Five), Some(Eight))))
    }

    "return columns in invalid board" in {
      SudokuNumber.all map { invalidBoard.column } must be_===(List(
        Column(Some(One), None, None, Some(Two), None, None, Some(Three), None, None),
        Column(Some(One), Some(Four), None, None, Some(Five), None, None, Some(Six), None),
        Column(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

        Column(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
        Column(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
        Column(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

        Column(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
        Column(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
        Column(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))))
    }

    "have equality test based on immutability" in {
      emptyBoard must be_===(
        Board(
          Row.empty,
          Row.empty,
          Row.empty,

          Row.empty,
          Row.empty,
          Row.empty,

          Row.empty,
          Row.empty,
          Row.empty))

      partialBoard must be_===(
        Board(
          Row(Some(One), None, None, Some(Two), None, None, Some(Three), None, None),
          Row(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
          Row(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

          Row(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
          Row(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
          Row(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

          Row(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
          Row(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
          Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))))

      fullBoard must be_===(
        Board(
          Row(Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine)),
          Row(Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three)),
          Row(Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six)),

          Row(Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One)),
          Row(Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four)),
          Row(Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven)),

          Row(Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two)),
          Row(Some(Six), Some(Seven), Some(Eight), Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five)),
          Row(Some(Nine), Some(One), Some(Two), Some(Three), Some(Four), Some(Five), Some(Six), Some(Seven), Some(Eight))))

      invalidBoard must be_===(
        Board(
          Row(Some(One), Some(One), None, Some(Two), None, None, Some(Three), None, None),
          Row(None, Some(Four), None, None, Some(Five), None, None, Some(Six), None),
          Row(None, None, Some(Seven), None, None, Some(Eight), None, None, Some(Nine)),

          Row(Some(Two), None, None, Some(Three), None, None, Some(One), None, None),
          Row(None, Some(Five), None, None, Some(Six), None, None, Some(Four), None),
          Row(None, None, Some(Eight), None, None, Some(Nine), None, None, Some(Seven)),

          Row(Some(Three), None, None, Some(One), None, None, Some(Two), None, None),
          Row(None, Some(Six), None, None, Some(Four), None, None, Some(Five), None),
          Row(None, None, Some(Nine), None, None, Some(Seven), None, None, Some(Eight))))
    
    }

    "toString boards as grids" in {
      emptyBoard.toString must be_===(
        "" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          "-----------------------\n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          "-----------------------\n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n")

      partialBoard.toString must be_===(
        "" +
          " 1 _ _ | 2 _ _ | 3 _ _ \n" +
          " _ 4 _ | _ 5 _ | _ 6 _ \n" +
          " _ _ 7 | _ _ 8 | _ _ 9 \n" +
          "-----------------------\n" +
          " 2 _ _ | 3 _ _ | 1 _ _ \n" +
          " _ 5 _ | _ 6 _ | _ 4 _ \n" +
          " _ _ 8 | _ _ 9 | _ _ 7 \n" +
          "-----------------------\n" +
          " 3 _ _ | 1 _ _ | 2 _ _ \n" +
          " _ 6 _ | _ 4 _ | _ 5 _ \n" +
          " _ _ 9 | _ _ 7 | _ _ 8 \n")

      fullBoard.toString must be_===(
        "" +
          " 1 2 3 | 4 5 6 | 7 8 9 \n" +
          " 4 5 6 | 7 8 9 | 1 2 3 \n" +
          " 7 8 9 | 1 2 3 | 4 5 6 \n" +
          "-----------------------\n" +
          " 2 3 4 | 5 6 7 | 8 9 1 \n" +
          " 5 6 7 | 8 9 1 | 2 3 4 \n" +
          " 8 9 1 | 2 3 4 | 5 6 7 \n" +
          "-----------------------\n" +
          " 3 4 5 | 6 7 8 | 9 1 2 \n" +
          " 6 7 8 | 9 1 2 | 3 4 5 \n" +
          " 9 1 2 | 3 4 5 | 6 7 8 \n")

      invalidBoard.toString must be_===(
        "" +
          " 1 1 _ | 2 _ _ | 3 _ _ \n" +
          " _ 4 _ | _ 5 _ | _ 6 _ \n" +
          " _ _ 7 | _ _ 8 | _ _ 9 \n" +
          "-----------------------\n" +
          " 2 _ _ | 3 _ _ | 1 _ _ \n" +
          " _ 5 _ | _ 6 _ | _ 4 _ \n" +
          " _ _ 8 | _ _ 9 | _ _ 7 \n" +
          "-----------------------\n" +
          " 3 _ _ | 1 _ _ | 2 _ _ \n" +
          " _ 6 _ | _ 4 _ | _ 5 _ \n" +
          " _ _ 9 | _ _ 7 | _ _ 8 \n")
    }
    "construct idstrings from Boards" in {
      emptyBoard.toIdString must be_===(emptyIdString)
      partialBoard.toIdString must be_===(partialIdString)
      fullBoard.toIdString must be_===(fullIdString)
      invalidBoard.toIdString must be_===(invalidIdString)
    }
    "construct boards from grid Strings" in {
      Board(
        "" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          "-----------------------\n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          "-----------------------\n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n" +
          " _ _ _ | _ _ _ | _ _ _ \n") must be_===(emptyBoard)

      Board(
        "" +
          " 1 _ _ | 2 _ _ | 3 _ _ \n" +
          " _ 4 _ | _ 5 _ | _ 6 _ \n" +
          " _ _ 7 | _ _ 8 | _ _ 9 \n" +
          "-----------------------\n" +
          " 2 _ _ | 3 _ _ | 1 _ _ \n" +
          " _ 5 _ | _ 6 _ | _ 4 _ \n" +
          " _ _ 8 | _ _ 9 | _ _ 7 \n" +
          "-----------------------\n" +
          " 3 _ _ | 1 _ _ | 2 _ _ \n" +
          " _ 6 _ | _ 4 _ | _ 5 _ \n" +
          " _ _ 9 | _ _ 7 | _ _ 8 \n") must be_===(partialBoard)

      Board(
        "" +
          " 1 2 3 | 4 5 6 | 7 8 9 \n" +
          " 4 5 6 | 7 8 9 | 1 2 3 \n" +
          " 7 8 9 | 1 2 3 | 4 5 6 \n" +
          "-----------------------\n" +
          " 2 3 4 | 5 6 7 | 8 9 1 \n" +
          " 5 6 7 | 8 9 1 | 2 3 4 \n" +
          " 8 9 1 | 2 3 4 | 5 6 7 \n" +
          "-----------------------\n" +
          " 3 4 5 | 6 7 8 | 9 1 2 \n" +
          " 6 7 8 | 9 1 2 | 3 4 5 \n" +
          " 9 1 2 | 3 4 5 | 6 7 8 \n") must be_===(fullBoard)

      Board(
        "" +
          " 1 1 _ | 2 _ _ | 3 _ _ \n" +
          " _ 4 _ | _ 5 _ | _ 6 _ \n" +
          " _ _ 7 | _ _ 8 | _ _ 9 \n" +
          "-----------------------\n" +
          " 2 _ _ | 3 _ _ | 1 _ _ \n" +
          " _ 5 _ | _ 6 _ | _ 4 _ \n" +
          " _ _ 8 | _ _ 9 | _ _ 7 \n" +
          "-----------------------\n" +
          " 3 _ _ | 1 _ _ | 2 _ _ \n" +
          " _ 6 _ | _ 4 _ | _ 5 _ \n" +
          " _ _ 9 | _ _ 7 | _ _ 8 \n") must be_===(invalidBoard)
    }
    "construct boards from idStrings" in {
      Board(emptyIdString, id = true) must be_===(emptyBoard)
      Board(partialIdString, id = true) must be_===(partialBoard)
      Board(fullIdString, id = true) must be_===(fullBoard)
      Board(invalidIdString, id = true) must be_===(invalidBoard)
    }
     "construct md5 from idStrings)" in {
       emptyBoard.md5 must be_===(md5Hex(emptyIdString))
       partialBoard.md5 must be_===(md5Hex(partialIdString))
       fullBoard.md5 must be_===(md5Hex(fullIdString))
       invalidBoard.md5 must be_===(md5Hex(invalidIdString))
     }
  }
}

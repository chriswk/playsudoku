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
      SudokuNumber.all map { emptyRow(_) } must equalTo(List.fill(9)(None))
    }

    "deindex correctly for partial row" in {
      SudokuNumber.all.map { partialRow(_) } must equalTo(
        List(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine))
      )
    }

    "deindex correctly for full row" in {
      SudokuNumber.all.map { fullRow(_) } must equalTo(
        List(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine))
      )
    }

    "deindex correctly for invalid row" in {
      SudokuNumber.all.map { invalidRow(_) } must equalTo(
        List(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine))
      )
    }

    "have equality test based on immutability" in {
      emptyRow must equalTo(Row.empty)
      partialRow must equalTo(Row(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
      fullRow must equalTo(Row(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
      invalidRow must equalTo(partialRow.copy(sixth = Some(Five)))
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
      } must equalTo(
        List(None, None, None, None, None, None, None, None, None))
    }
    "deindex correctly for partial column" in {
      SudokuNumber.all map {
        partialColumn(_)
      } must equalTo(
        List(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
    }

    "deindex correctly for full column" in {
      SudokuNumber.all map {
        fullColumn(_)
      } must equalTo(
        List(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
    }

    "deindex correctly for invalid column" in {
      SudokuNumber.all map {
        invalidColumn(_)
      } must equalTo(
        List(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
    }

    "have equality test based on immutability" in {
      emptyColumn must equalTo(Column(None, None, None, None, None, None, None, None, None))
      partialColumn must equalTo(Column(Some(Eight), None, None, Some(Five), Some(Four), None, None, Some(One), Some(Nine)))
      fullColumn must equalTo(Column(Some(Eight), Some(Seven), Some(Six), Some(Five), Some(Four), Some(Three), Some(Two), Some(One), Some(Nine)))
      invalidColumn must equalTo(Column(Some(Eight), None, None, Some(Five), Some(Four), Some(Five), None, Some(One), Some(Nine)))
    }
  }
}

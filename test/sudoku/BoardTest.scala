package sudoku

import org.specs2.Specification
import org.specs2.matcher.ShouldMatchers
import play.api.test.PlaySpecification

object BoardTest extends PlaySpecification with ShouldMatchers {
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

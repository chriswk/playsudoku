package sudoku

sealed abstract class SudokuNumber(val representative: Int) {
  override def toString(): String = "[%d]" format representative
}
object SudokuNumber {
  implicit val ordering: Ordering[SudokuNumber] = Ordering[Int] on { _.representative}
  val all = List(One, Two, Three, Four, Five, Six, Seven, Eight, Nine)
  val set = all.toSet

  def apply(i: Int): SudokuNumber = all.rotate(8)(i % 9)
}

object One extends SudokuNumber(1)
object Two extends SudokuNumber(2)
object Three extends SudokuNumber(3)
object Four extends SudokuNumber(4)
object Five extends SudokuNumber(5)
object Six extends SudokuNumber(6)
object Seven extends SudokuNumber(7)
object Eight extends SudokuNumber(8)
object Nine extends SudokuNumber(9)
}

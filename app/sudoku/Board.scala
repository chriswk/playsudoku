package sudoku

case class Row(
                first: Option[SudokuNumber], second: Option[SudokuNumber], third: Option[SudokuNumber],
                fourth: Option[SudokuNumber], fifth: Option[SudokuNumber], sixth: Option[SudokuNumber],
                seventh: Option[SudokuNumber], eight: Option[SudokuNumber], ninth: Option[SudokuNumber]
                ) {
  private val values = Array(
    first, second, third,
    fourth, fifth, sixth,
    seventh, eight, ninth
  )

  def apply(i: SudokuNumber): Option[SudokuNumber] = values(i.representative - 1)
}

object Row {
  val empty = Row(None, None, None, None, None, None, None, None, None)
}

case class Column(
                   first: Option[SudokuNumber], second: Option[SudokuNumber], third: Option[SudokuNumber],
                   fourth: Option[SudokuNumber], fifth: Option[SudokuNumber], sixth: Option[SudokuNumber],
                   seventh: Option[SudokuNumber], eight: Option[SudokuNumber], ninth: Option[SudokuNumber]
                   ) {
  private val values = Array(
    first, second, third,
    fourth, fifth, sixth,
    seventh, eight, ninth
  )

  def apply(i: SudokuNumber): Option[SudokuNumber] = values(i.representative - 1)
}

case class Board(
  first: Row, second: Row, third: Row,
  fourth: Row, fifth: Row, sixth: Row,
  seventh: Row, eight: Row, ninth: Row
) {
  private lazy val rows = Array(
    first, second, third,
    fourth, fifth, sixth,
    seventh, eight, ninth
  )

  private lazy val columns = SudokuNumber.all map { i =>
    Column(first(i), second(i), third(i), fourth(i), fifth(i), sixth(i), seventh(i), eight(i), ninth(i))
  }
  def apply(i: SudokuNumber, j: SudokuNumber): Option[SudokuNumber] = column(i)(j)

  def row(i: SudokuNumber) = rows(i.representative - 1)
  def column(j: SudokuNumber) = columns(j.representative - 1)
  def md5 = md5Hex(toIdString)
  lazy val toGraphColouringProblem: GraphColouringProblem = GraphColouringProblem(this)

  def element(value: Option[SudokuNumber]): String = value map { _.representative.toString } getOrElse "_"

  override def toString = {
    val rowStrings = rows map { row =>
      List(
      element(row.first), element(row.second), element(row.third),
      "|",
      element(row.fourth), element(row.fifth), element(row.sixth),
      "|",
      element(row.seventh), element(row.eight), element(row.ninth)
      ).mkString(" ", " ", " ")
    }

    val allRows = List(
    rowStrings(0), rowStrings(1), rowStrings(2),
      "-----------------------",
      rowStrings(3), rowStrings(4), rowStrings(5),
      "-----------------------",
      rowStrings(6), rowStrings(7), rowStrings(8)
    )

    allRows.mkString("", "\n", "\n")
  }

  def toIdString = {
    toString.replaceAll("[^0-9_]", "")
  }
}

object Board {
  private val Line = """(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)(\d|_)""".r
  private def toElement(token: String): Option[SudokuNumber] = token.toIntOption map { SudokuNumber(_) }

  def apply(board: String): Board = {
    val lines = board.split("\n").toList
    val trimmed = lines map { _.replaceAll("[^0-9_]", "") } filter { _ != "" }

    val rows = trimmed map { line =>
      val Line(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth) = line
      Row(
        toElement(first), toElement(second), toElement(third),
        toElement(fourth), toElement(fifth), toElement(sixth),
        toElement(seventh), toElement(eighth), toElement(ninth)
      )
    }

    Board(
      rows(0), rows(1), rows(2),
      rows(3), rows(4), rows(5),
      rows(6), rows(7), rows(8)
    )
  }
  def apply(board:String, id: Boolean): Board = {
    val lines = board.grouped(9).toList
    val rows = lines map { line =>
      val Line(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth) = line
      Row(
        toElement(first), toElement(second), toElement(third),
        toElement(fourth), toElement(fifth), toElement(sixth),
        toElement(seventh), toElement(eighth), toElement(ninth)
      )
    }

    Board(
      rows(0), rows(1), rows(2),
      rows(3), rows(4), rows(5),
      rows(6), rows(7), rows(8)
    )
  }

  def apply(graphColouringProblem: GraphColouringProblem): Board = {
    def boardValue(i: SudokuNumber, j: SudokuNumber) = SingletonSet.unapply(graphColouringProblem(i, j).candidates)

    val rows = (SudokuNumber.all map { j =>
      (j, Row(
        boardValue(One, j), boardValue(Two, j), boardValue(Three, j),
        boardValue(Four, j), boardValue(Five, j), boardValue(Six, j),
        boardValue(Seven, j), boardValue(Eight, j), boardValue(Nine, j)
      )
        )
    }).toMap

    Board(
      rows(One), rows(Two), rows(Three),
      rows(Four), rows(Five), rows(Six),
      rows(Seven), rows(Eight), rows(Nine)
    )
  }
}

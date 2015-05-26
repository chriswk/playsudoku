package sudoku

object Slugger {
  val chars = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ_abcdefghijkmnopqrstuvwxyz"
  def num2Sxg(num: Int): String = {
    def recur(num: Int, soFar: List[Char]): List[Char] = {
      if(num == 0 && soFar.isEmpty) List('0')
      else if (num == 0) soFar
      else {
        val identifier = math.abs(num % 60)
        val char = chars(identifier)
        recur((num - identifier) / 60, char :: soFar)
      }
    }
    recur(math.abs(num), List()).mkString
  }

  def string2Slug(s: String): String = {
    num2Sxg(s.hashCode)
  }

  def sxg2Num(s: String): Int = {
    def recur(s: String, number: Int): Int = {
      if (s.isEmpty) number
      else {
        val c = s.head.toInt
        if(c >= 48 && c <= 57) recur(s.tail, 60 * number + (c - 48))
        else if (c >= 65 && c <= 72) recur(s.tail, 60 * number + (c - 55))
        else if (c == 73 || c == 108) recur(s.tail, 60 * number + (1))
        else if (c >= 74 && c <= 78) recur(s.tail, 60 * number + (c - 56))
        else if (c == 79) recur(s.tail, 60 * number + 0)
        else if (c >= 80 && c <= 90) recur(s.tail, 60 * number + (c - 57))
        else if (c == 95) recur(s.tail, 60 * number + 34)
        else if (c >= 97 && c <= 107) recur(s.tail, 60 * number + (c - 62))
        else if (c >= 109 && c <= 122) recur(s.tail, 60 * number + (c - 63))
        else recur(s.tail, 60 * number + 0)
      }
    }
    recur(s, 0)
  }

}

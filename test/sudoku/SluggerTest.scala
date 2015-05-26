package sudoku

import org.scalacheck.{Prop, Gen, Properties}
import org.scalacheck.Prop.{forAll, BooleanOperators}
class SluggerTest extends Properties("Slugger") {

  property("String to slug") = forAll { (a: String) => {
    Slugger.string2Slug(a) == Slugger.string2Slug(a)
  }}

  val positiveNumbers = Gen.choose(0, Integer.MAX_VALUE)
  property("sxg2Num(num2Sxg)") = Prop.forAll(positiveNumbers)(a =>
    Slugger.sxg2Num(Slugger.num2Sxg(a)) == a
  )
}

import org.scalatest.FunSuite

class FunctionalStyle extends FunSuite {

  test("reduce left") {
    // find longest word
    val words = List("a", "bbb", "cc")

    // imperative
    def imperative(): Option[String] = {
      if (words.isEmpty) {
        return None
      }
      var longestWord = words(0)
      for (word <- words) {
        if (longestWord.length < word.length) {
          longestWord = word
        }
      }
      Some(longestWord)
    }
    assert(imperative().get == "bbb")

    // functional
    def functional(): Option[String] = {
      words.reduceLeftOption((a, b) => if (a.length > b.length) a else b)
    }
    assert(functional().get == "bbb")
  }
}

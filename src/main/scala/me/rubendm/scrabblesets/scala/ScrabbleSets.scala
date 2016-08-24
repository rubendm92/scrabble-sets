package me.rubendm.scrabblesets.scala

class ScrabbleSets(private val initialTileCount: Map[Char, Int]) {

  def tilesLeft(input: String) = {
    val leftChars = calculateLeftChars(input)
    if (leftChars.keys.exists(_ < 0)) invalidInputMessage(leftChars.filter(_._1 < 0))
    else formatOutput(leftChars)
  }

  private def calculateLeftChars(input: String) = {
    val inputCharsByCount = ((input toCharArray) groupBy identity) mapValues (_.length) withDefaultValue 0
    initialTileCount
      .map(entry => (entry._1, entry._2 - inputCharsByCount(entry._1)))
      .groupBy(_._2)
      .mapValues(_.keys.toList)
  }

  private def invalidInputMessage(chars: Map[Int, List[Char]]) = {
    val string: String = chars.values.flatten.map(_ + "'s").mkString(", ")
    s"Invalid input. More $string have been taken from the bag than possible."
  }

  private def formatOutput(chars: Map[Int, List[Char]]) = {
    chars.toSeq
      .sortBy(_._1)
      .reverse
      .map(entry => s"${entry._1}: ${entry._2.sorted.mkString(", ")}")
      .mkString("\n")
  }
}

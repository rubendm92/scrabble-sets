package me.rubendm.scrabblesets.scala

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class ScrabbleSetsSpec extends FlatSpec with TableDrivenPropertyChecks with Matchers {

  val scrabble = new ScrabbleSets(Map(
    'A' -> 9,
    'B' -> 2,
    'C' -> 2,
    'D' -> 4,
    'E' -> 12,
    'F' -> 2,
    'G' -> 3,
    'H' -> 2,
    'I' -> 9,
    'J' -> 1,
    'K' -> 1,
    'L' -> 4,
    'M' -> 2,
    'N' -> 6,
    'O' -> 8,
    'P' -> 2,
    'Q' -> 1,
    'R' -> 6,
    'S' -> 4,
    'T' -> 6,
    'U' -> 4,
    'V' -> 2,
    'W' -> 2,
    'X' -> 1,
    'Y' -> 2,
    'Z' -> 1,
    '_' -> 2
  ))
  val testCases =
    Table(
      ("usedTiles", "output"),
      ("PQAREIOURSTHGWIOAE_", "10: E\n" +
        "7: A, I\n" +
        "6: N, O\n" +
        "5: T\n" +
        "4: D, L, R\n" +
        "3: S, U\n" +
        "2: B, C, F, G, M, V, Y\n" +
        "1: H, J, K, P, W, X, Z, _\n" +
        "0: Q"),
      ("LQTOONOEFFJZT", "11: E\n" +
        "9: A, I\n" +
        "6: R\n" +
        "5: N, O\n" +
        "4: D, S, T, U\n" +
        "3: G, L\n" +
        "2: B, C, H, M, P, V, W, Y, _\n" +
        "1: K, X\n" +
        "0: F, J, Q, Z"),
      ("AXHDRUIOR_XHJZUQEE", "Invalid input. More X's have been taken from the bag than possible.")
    )

  "ScrabbleSets" should "output the tiles that are left in the bag" in {
    forAll (testCases) { (usedTiles: String, output: String) =>
      scrabble.tilesLeft(usedTiles) should be(output)
    }
  }
}

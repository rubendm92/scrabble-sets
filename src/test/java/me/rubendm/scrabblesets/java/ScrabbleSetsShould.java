package me.rubendm.scrabblesets.java;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class ScrabbleSetsShould {

    private ScrabbleSets scrabble;

    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        return asList(new Object[][] {
                { "PQAREIOURSTHGWIOAE_", "10: E\n" +
                        "7: A, I\n" +
                        "6: N, O\n" +
                        "5: T\n" +
                        "4: D, L, R\n" +
                        "3: S, U\n" +
                        "2: B, C, F, G, M, V, Y\n" +
                        "1: H, J, K, P, W, X, Z, _\n" +
                        "0: Q" },
                {"LQTOONOEFFJZT", "11: E\n" +
                        "9: A, I\n" +
                        "6: R\n" +
                        "5: N, O\n" +
                        "4: D, S, T, U\n" +
                        "3: G, L\n" +
                        "2: B, C, H, M, P, V, W, Y, _\n" +
                        "1: K, X\n" +
                        "0: F, J, Q, Z"},
                {"AXHDRUIOR_XHJZUQEE", "Invalid input. More X's have been taken from the bag than possible."},
        });
    }

    private final String input;
    private final String expectedOutput;

    public ScrabbleSetsShould(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    private static final Map<Character, Long> englishScrabbleSet = new HashMap<>();
    static {
        englishScrabbleSet.put('A', 9L);
        englishScrabbleSet.put('B', 2L);
        englishScrabbleSet.put('C', 2L);
        englishScrabbleSet.put('D', 4L);
        englishScrabbleSet.put('E', 12L);
        englishScrabbleSet.put('F', 2L);
        englishScrabbleSet.put('G', 3L);
        englishScrabbleSet.put('H', 2L);
        englishScrabbleSet.put('I', 9L);
        englishScrabbleSet.put('J', 1L);
        englishScrabbleSet.put('K', 1L);
        englishScrabbleSet.put('L', 4L);
        englishScrabbleSet.put('M', 2L);
        englishScrabbleSet.put('N', 6L);
        englishScrabbleSet.put('O', 8L);
        englishScrabbleSet.put('P', 2L);
        englishScrabbleSet.put('Q', 1L);
        englishScrabbleSet.put('R', 6L);
        englishScrabbleSet.put('S', 4L);
        englishScrabbleSet.put('T', 6L);
        englishScrabbleSet.put('U', 4L);
        englishScrabbleSet.put('V', 2L);
        englishScrabbleSet.put('W', 2L);
        englishScrabbleSet.put('X', 1L);
        englishScrabbleSet.put('Y', 2L);
        englishScrabbleSet.put('Z', 1L);
        englishScrabbleSet.put('_', 2L);
    }

    @Before
    public void setUp() {
        scrabble = new ScrabbleSets(englishScrabbleSet);
    }

    @Test
    public void output_the_tiles_that_are_left_in_the_bag() {
        String output = scrabble.tilesLeft(input);

        assertThat(output, is(expectedOutput));
    }
}
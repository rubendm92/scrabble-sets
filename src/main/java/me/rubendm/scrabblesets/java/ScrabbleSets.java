package me.rubendm.scrabblesets.java;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

class ScrabbleSets {

    private final Map<Character, Long> set;

    ScrabbleSets(Map<Character, Long> set) {
        this.set = set;
    }

    String tilesLeft(String input) {
        Map<Character, Long> usedChars = findCharsByCount(input);
        Map<Character, Long> leftChars = removeUsedChars(usedChars);
        return findError(leftChars).orElseGet(() -> formatOutput(leftChars));
    }

    private static Map<Character, Long> findCharsByCount(String input) {
        return input.chars()
                .mapToObj(c -> (char) c)
                .collect(groupingBy(identity(), counting()));
    }

    private Map<Character, Long> removeUsedChars(Map<Character, Long> charsByCount) {
        return Stream.of(set.entrySet(), charsByCount.entrySet())
                .flatMap(Collection::stream)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1 - v2));
    }

    private static Optional<String> findError(Map<Character, Long> leftChars) {
        return leftChars.entrySet().stream()
                .filter(e -> e.getValue() < 0)
                .findFirst()
                .map(ScrabbleSets::formatError);
    }

    private static String formatOutput(Map<Character, Long> leftChars) {
        Map<Long, List<Character>> collect = leftChars.entrySet().stream()
                .collect(groupingBy(Map.Entry::getValue, mapping(Map.Entry::getKey, toList())));
        return collect.entrySet().stream()
                .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey()))
                .map(ScrabbleSets::formatLine)
                .collect(joining("\n"));
    }

    private static String formatError(Map.Entry<Character, Long> entry) {
        return "Invalid input. More " + entry.getKey() + "'s have been taken from the bag than possible.";
    }

    private static String formatLine(Map.Entry<Long, List<Character>> entry) {
        return entry.getKey() + ": " + entry.getValue().stream()
                .sorted()
                .map(Object::toString)
                .collect(joining(", "));
    }
}
package me.rubendm.scrabblesets.java;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

class ScrabbleSets {

    private final List<TileCount> initialTileCount;

    ScrabbleSets(Map<Character, Long> set) {
        initialTileCount = set.entrySet().stream()
                .map(TileCount::new)
                .collect(toList());
    }

    String tilesLeft(String input) {
        final Map<Long, List<Character>> outputMap = countLeftCharacters(inputCharactersByCount(input));
        if (hasBeenSubtractedMoreCharactersThanAllowed(outputMap)) {
            return invalidInputMessage(outputMap);
        }
        return formatOutput(outputMap);
    }

    private Map<Character, Long> inputCharactersByCount(String input) {
        return characters(input)
                .collect(groupingBy(identity(), counting()));
    }

    private Stream<Character> characters(String input) {
        return input.chars().mapToObj(c -> (char) c);
    }

    private Map<Long, List<Character>> countLeftCharacters(Map<Character, Long> inputMap) {
        return initialTileCount.stream()
                .map(tile -> tile.minus(inputMap.getOrDefault(tile.character, 0L)))
                .collect(groupingBy(tile -> tile.count, mapping(tile -> tile.character, toList())));
    }

    private boolean hasBeenSubtractedMoreCharactersThanAllowed(Map<Long, List<Character>> outputMap) {
        return outputMap.entrySet().stream().anyMatch(entry -> entry.getKey() < 0);
    }

    private String invalidInputMessage(Map<Long, List<Character>> outputMap) {
        return outputMap.entrySet().stream()
                .filter(entry -> entry.getKey() < 0)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .map(c -> c + "'s")
                .collect(joining(", ", "Invalid input. More ", " have been taken from the bag than possible."));
    }

    private String formatOutput(Map<Long, List<Character>> outputMap) {
        return outputMap.entrySet().stream()
                .sorted((left, right) -> right.getKey().compareTo(left.getKey()))
                .map(this::formatLine)
                .collect(joining("\n"));
    }

    private String formatLine(Map.Entry<Long, List<Character>> entry) {
        return entry.getKey() + ": " + entry.getValue().stream()
                .sorted()
                .map(Object::toString)
                .collect(joining(", "));
    }

    private static class TileCount {
        final Character character;
        final Long count;

        TileCount(Character character, Long count) {
            this.character = character;
            this.count = count;
        }

        TileCount(Map.Entry<Character, Long> entry) {
            this.character = entry.getKey();
            this.count = entry.getValue();
        }

        TileCount minus(Long minus) {
            return new TileCount(character, count - minus);
        }
    }
}
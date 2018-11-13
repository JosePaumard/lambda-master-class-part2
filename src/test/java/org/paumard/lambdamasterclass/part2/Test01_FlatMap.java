package org.paumard.lambdamasterclass.part2;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class Test01_FlatMap {

    private List<String> alphabet = List.of("alfa", "bravo", "charlie", "delta");

    private List<String> sonnet = List.of(
            "From fairest creatures we desire increase,",
            "That thereby beauty's rose might never die,",
            "But as the riper should by time decease,",
            "His tender heir might bear his memory:",
            "But thou contracted to thine own bright eyes,",
            "Feed'st thy light's flame with self-substantial fuel,",
            "Making a famine where abundance lies,",
            "Thy self thy foe, to thy sweet self too cruel:",
            "Thou that art now the world's fresh ornament,",
            "And only herald to the gaudy spring,",
            "Within thine own bud buriest thy content,",
            "And, tender churl, mak'st waste in niggarding:",
            "Pity the world, or else this glutton be,",
            "To eat the world's due, by the grave and thee.");


    private List<String> expand(String s) {
        return s.codePoints()
                .mapToObj(codePoint -> Character.toString((char)codePoint))
                .collect(toList());
    }

    private String[] splitToWords(String line) {
        return line.split("[ ,:.]+");
    }

    @Test
    public void flatMap_1() {

        List<List<String>> result = alphabet.stream()
                .map(s -> expand(s))
                .collect(toList());

        assertThat(result).containsExactly(
                List.of("a", "l", "f", "a"),
                List.of("b", "r", "a", "v", "o"),
                List.of("c", "h", "a", "r", "l", "i", "e"),
                List.of("d", "e", "l", "t", "a")
        );

    }

    /**
     * Given the lines of the sonnet, split each line into words and
     * return a single list of all words.
     */
    @Test
    public void flatMap_2() {

        List<String> words = null; // TODO

        assertThat(words.size()).isEqualTo(106);
        assertThat(words).contains("From", "fairest", "creatures", "we", "desire", "increase");
    }
}

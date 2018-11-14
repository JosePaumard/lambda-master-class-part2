package org.paumard.lambdamasterclass.part2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;

public class Test06_GroupingBy {

    private List<String> alphabet =
            List.of("alfa", "bravo", "charlie", "delta", "echo",
                    "foxtrot", "golf", "hotel", "india", "juliet",
                    "kilo", "lima", "mike", "november", "oscar",
                    "papa", "quebec", "romeo", "sierra", "tango",
                    "uniform", "victor", "whiskey", "x-ray", "yankee",
                    "zulu");

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

    @Test
    public void groupingBy_1() {

        Map<Integer, List<String>> result =
                alphabet.stream()
                        .collect(toMap(
                                String::length,
                                s -> new ArrayList<>(Arrays.asList(s)),
                                (a, b) -> {
                                    a.addAll(b);
                                    return a;
                                }));

        assertThat(result).containsExactly(
                Map.entry(4, List.of("alfa", "echo", "golf", "kilo", "lima", "mike", "papa", "zulu")),
                Map.entry(5, List.of("bravo", "delta", "hotel", "india", "oscar", "romeo", "tango", "x-ray")),
                Map.entry(6, List.of("juliet", "quebec", "sierra", "victor", "yankee")),
                Map.entry(7, List.of("charlie", "foxtrot", "uniform", "whiskey")),
                Map.entry(8, List.of("november")));
    }

    @Test
    public void groupingBy_2() {

        Map<Integer, List<String>> result =
                alphabet.stream()
                        .collect(groupingBy(
                                String::length));

        assertThat(result).containsExactly(
                Map.entry(4, List.of("alfa", "echo", "golf", "kilo", "lima", "mike", "papa", "zulu")),
                Map.entry(5, List.of("bravo", "delta", "hotel", "india", "oscar", "romeo", "tango", "x-ray")),
                Map.entry(6, List.of("juliet", "quebec", "sierra", "victor", "yankee")),
                Map.entry(7, List.of("charlie", "foxtrot", "uniform", "whiskey")),
                Map.entry(8, List.of("november")));
    }

    /**
     * Collect the lines of the sonnet into a map,
     * whose keys are the first letter (as a String) of each line,
     * and whose values are a list of lines beginning with that letter.
     */
    @Test
    public void groupingBy_3() {

        Map<String, List<String>> result =
                sonnet.stream()
                        .collect(
                                Collectors.groupingBy(
                                        line -> line.substring(0, 1)
                                )
                        );


        assertThat(result.size()).isEqualTo(8);
        assertThat(result).contains(
                Map.entry("P", List.of("Pity the world, or else this glutton be,")),
                Map.entry("A", List.of("And only herald to the gaudy spring,",
                        "And, tender churl, mak'st waste in niggarding:")),
                Map.entry("B", List.of("But as the riper should by time decease,",
                        "But thou contracted to thine own bright eyes,")),
                Map.entry("T", List.of("That thereby beauty's rose might never die,",
                        "Thy self thy foe, to thy sweet self too cruel:",
                        "Thou that art now the world's fresh ornament,",
                        "To eat the world's due, by the grave and thee.")),
                Map.entry("F", List.of("From fairest creatures we desire increase,",
                        "Feed'st thy light's flame with self-substantial fuel,")),
                Map.entry("W", List.of("Within thine own bud buriest thy content,")),
                Map.entry("H", List.of("His tender heir might bear his memory:")),
                Map.entry("M", List.of("Making a famine where abundance lies,"))
        );
    }
}

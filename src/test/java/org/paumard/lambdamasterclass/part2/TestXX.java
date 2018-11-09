package org.paumard.lambdamasterclass.part2;

import org.junit.Test;
import org.paumard.lambdamasterclass.part2.util.FBEnum;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class TestXX {

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

    /**
     * Create a collectors that counts how many
     * times each letter is used in the Sonnet text.
     */
    @Test
    public void test_count_number_of_occurences() {

        Function<String, Stream<String>> lineToLetters =
                line -> line.toLowerCase().chars().mapToObj(letter -> Character.toString((char) letter));

        Map<String, Long> collect = sonnet.stream().flatMap(lineToLetters).filter(letter -> !letter.trim().isEmpty())
                .collect(groupingBy(Function.identity(), Collectors.counting()));
        collect.forEach((key, value) -> System.out.println(key + " -> " + value));
    }


    /**
     * Use the previous collector to extract the most used letter.
     */
    @Test
    public void test_count_number_of_occurences_then_get_the_max() {

        Function<String, Stream<String>> lineToLetters =
                line -> line.toLowerCase().chars().mapToObj(letter -> Character.toString((char) letter));

        Map<String, Long> collect = sonnet.stream().flatMap(lineToLetters).filter(letter -> !letter.trim().isEmpty())
                .collect(groupingBy(Function.identity(), Collectors.counting()));

        Map.Entry<String, Long> entry = collect.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get();

        System.out.println(entry.getKey() + " -> " + entry.getValue());
    }

    /**
     * Use the previous collector to extract the most used letters.
     */
    @Test
    public void test_count_number_of_occurences_then_get_the_maxes() {

        Function<String, Stream<String>> lineToLetters =
                line -> line.toLowerCase().chars().mapToObj(letter -> Character.toString((char) letter));

        Map<String, Long> collect = sonnet.stream().flatMap(lineToLetters).filter(letter -> !letter.trim().isEmpty())
                .collect(groupingBy(Function.identity(), Collectors.counting()));

        Map<Long, List<String>> map =
                collect.entrySet().stream()
                        .collect(
                                groupingBy(
                                        Map.Entry::getValue,
                                        Collectors.mapping(Map.Entry::getKey, toList())
                                ));

        Map.Entry<Long, List<String>> entry = map.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getKey))
                .get();

        System.out.println(entry.getKey() + " -> " + entry.getValue());
    }

    @Test
    public void test_fizzbuzz() {
        Function<Integer, String> f =
                Stream.of(FBEnum.values())
                        .map(FBEnum::fizzer)
                        .reduce(i -> "", (f1, f2) -> i -> f1.apply(i) + f2.apply(i));

        BiFunction<Integer, String, String> f1 = (index, x) -> x.isEmpty() ? "" + index : x;
        IntFunction<String> fizzBuzzer =
                index -> f1.apply(index, f.apply(index));


        IntStream.rangeClosed(1, 25)
                .mapToObj(fizzBuzzer)
                .forEach(System.out::println);
    }
}

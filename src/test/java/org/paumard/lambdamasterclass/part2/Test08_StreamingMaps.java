package org.paumard.lambdamasterclass.part2;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class Test08_StreamingMaps {

    private Set<String> dictionnaryWords;
    private Set<String> shakespeareWords;
    private Set<String> prideAndPrejudice;
    private Set<String> prideAndPrejudiceLines;

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
            "To eat the world's due, by the grave and thee.",
            "to to thy");

    @Before
    public void before() {
        Path dictionnary = Paths.get("data/ospd.txt.gz");
        dictionnaryWords = readWordsFrom(dictionnary);

        Path shakespeare = Paths.get("data/words.shakespeare.txt.gz");
        shakespeareWords = readWordsFrom(shakespeare);

        Path prideAndPrejudice = Paths.get("data/Pride-and-Prejudice.txt.gz");
        prideAndPrejudiceLines = readWordsFrom(prideAndPrejudice);
    }

    private Set<String> readWordsFrom(Path dictionnary) {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(Files.newInputStream(dictionnary));
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream));
             Stream<String> lines = reader.lines();) {

            return lines.map(String::toLowerCase).collect(Collectors.toSet());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Set.of();
    }

    @Test
    public void streamingMaps_1() {

        assertThat(dictionnaryWords.size()).isEqualTo(79339);
        assertThat(shakespeareWords.size()).isEqualTo(23688);
    }

    /**
     * Find the longest word from the Shakespeare words
     */
    @Test
    public void streamingMaps_2() {

        String longestWord = shakespeareWords.stream()
                .max(Comparator.comparing(String::length)).get();
        System.out.println("longestWord = " + longestWord);
    }

    /**
     * Find the longest word from the Shakespeare words
     * that is in the dictionnary
     */
    @Test
    public void streamingMaps_3() {

        String longestWord = shakespeareWords.stream()
                .filter(word -> dictionnaryWords.contains(word))
                .max(Comparator.comparing(String::length)).get();
        System.out.println("longestWord = " + longestWord);
    }


    /**
     * Find the most used Pride and Prejudice words of more than
     * 4 letter
     */
    @Test
    public void streamingMaps_4() {

        IntPredicate isLetter = letter -> letter >= 'a' && letter <= 'z';
        String nonLetters = prideAndPrejudiceLines.stream()
                .flatMapToInt(line -> line.chars())
                .filter(isLetter.negate())
                .distinct()
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
        System.out.println("nonLetters = " + nonLetters);

        Pattern pattern = Pattern.compile("[“ ,”';\\.\\?_\\-!:2436501879()\\*]+");

        Set<String> words = prideAndPrejudiceLines.stream()
                .flatMap(line -> pattern.splitAsStream(line))
                .collect(Collectors.toSet());
        System.out.println("words.size() = " + words.size());

        Map<String, Long> wordsAppearance =
                prideAndPrejudiceLines.stream()
                        .flatMap(line -> pattern.splitAsStream(line))
                        .collect(
                                Collectors.groupingBy(
                                        Function.identity(), Collectors.counting()
                                )
                        );

        Map.Entry<String, Long> mostSeenWord = wordsAppearance.entrySet().stream()
                .filter(entry -> entry.getKey().length() > 3)
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("mostSeenWord = " + mostSeenWord);

//        Map<Long, List<Map.Entry<String, Long>>> collect =
        Map<Long, List<String>> collect =
                wordsAppearance.entrySet().stream()
                        .filter(entry -> entry.getKey().length() > 3)
                        .collect(Collectors.groupingBy(
                                entry -> entry.getValue(),
                                Collectors.mapping(entry -> entry.getKey(), Collectors.toList())));

        Map.Entry<Long, List<String>> entry = collect.entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .get();
        System.out.println("entry = " + entry);

    }

    @Test
    public void streamingMaps_5() {

        Pattern pattern = Pattern.compile(("[ ,':\\-]+"));

        Map<String, Long> map = sonnet.stream()
                .map(String::toLowerCase)
                .flatMap(line -> pattern.splitAsStream(line))
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        Function.identity(),
                                        Collectors.counting()
                                ),
                                Map::copyOf
                        )

                );
        System.out.println("map.size() = " + map.size());

        Map.Entry<String, Long> mostSeenWord =
                map.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .get();
        System.out.println("mostSeenWord = " + mostSeenWord);

//        Map<Long, List<Map.Entry<String, Long>>> collect =
        Map<Long, List<String>> collect = map.entrySet().stream()
                .collect(
                        Collectors.groupingBy(
                                entry -> entry.getValue(),
                                Collectors.mapping(entry -> entry.getKey(), Collectors.toList())));

        Map.Entry<Long, List<String>> mostSeenWords = collect.entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .get();
        System.out.println("mostSeenWords = " + mostSeenWords);


    }
}

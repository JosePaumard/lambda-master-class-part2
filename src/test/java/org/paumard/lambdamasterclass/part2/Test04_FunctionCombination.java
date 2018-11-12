package org.paumard.lambdamasterclass.part2;

import org.junit.Test;

import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

public class Test04_FunctionCombination {

    @Test
    public void functionCombination_1() {

        List<Predicate<String>> predicates =
                List.of(s -> s != null, s -> !s.isEmpty(), s -> s.length() < 5);

        Predicate<String> combinedPredicate =
                predicates.stream().reduce(s -> true, Predicate::and);

        assertThat(combinedPredicate.test("")).isFalse();
        assertThat(combinedPredicate.test(null)).isFalse();
        assertThat(combinedPredicate.test("Hello")).isFalse();
        assertThat(combinedPredicate.test("Java")).isTrue();
    }

    @Test
    public void functionCombination_2() {

        List<IntUnaryOperator> operators =
                List.of(i -> i + 1, i -> i * 2, i -> i + 3);

        IntUnaryOperator combinedOperator = null; // TODO

        assertThat(combinedOperator.applyAsInt(5)).isEqualTo(15);
    }
}

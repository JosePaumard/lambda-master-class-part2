package org.paumard.lambdamasterclass.part2.util;

import java.util.function.Function;

public enum FBEnum {
    Fizz(3), Buzz(5), Woof(7);

    int factor;

    private FBEnum(int factor) {
        this.factor = factor;
    }

    public Function<Integer, String> fizzer() {
        return i -> i % factor == 0 ? toString() : "";
    }
}
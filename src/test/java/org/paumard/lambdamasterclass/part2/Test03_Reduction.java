package org.paumard.lambdamasterclass.part2;

import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class Test03_Reduction {

    @Test
    public void reduction_1() {
        long number = 21;

        BigInteger result = null; // TODO

        assertThat(result).isEqualTo(new BigInteger("51090942171709440000"));
    }
}

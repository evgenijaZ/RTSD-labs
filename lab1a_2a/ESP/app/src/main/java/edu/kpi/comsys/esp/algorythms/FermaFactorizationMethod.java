package edu.kpi.comsys.esp.algorythms;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

import static java.lang.Math.sqrt;
import static java.time.Instant.now;

public class FermaFactorizationMethod {

    public Multipliers findMultipliers(int n) {
        Instant start = now();
        Multipliers result = new Multipliers();
        int x = (int) (sqrt(n));
        double y;
        int counter = 0;
        do {
            x++;
            y = sqrt(x * x - n);
            if (counter++ > 1000) {
                result.setA(1);
                result.setB(n);
                return result;
            }
        } while (y != (int) y);
        result.setA((int) (x - y));
        result.setB((int) (x + y));
        Instant finish = now();
        result.setTimeElapsed(Duration.between(start, finish).toMillis());
        return result;
    }

    @Setter
    @Getter
    public static class Multipliers {
        private int a;
        private int b;
        private long timeElapsed;
    }
}

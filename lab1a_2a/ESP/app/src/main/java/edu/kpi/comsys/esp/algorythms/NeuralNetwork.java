package edu.kpi.comsys.esp.algorythms;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

public class NeuralNetwork {

    public Optional<Result> run(Dot d1, Dot d2, final double p, final double sigma) {
        double y1 = 0, y2 = 0;
        double w1 = 0, w2 = 0;
        int counter = 0;
        while (!isFinish(y1, y2, p) && counter < 1000) {
            y1 = getY(d1, w1, w2);
            w1 = recalculateW(d1.x, w1, p - y1, sigma);
            w2 = recalculateW(d1.y, w2, p - y1, sigma);

            if (isFinish(y1, y2, p))
                break;

            y2 = getY(d2, w1, w2);
            w1 = recalculateW(d2.x, w1, p - y2, sigma);
            w2 = recalculateW(d2.y, w2, p - y2, sigma);
            counter++;
        }
        if (counter == 1000)
            return Optional.empty();
        return  Optional.of(new Result(w1, w2));
    }

    private boolean isFinish(double y1, double y2, double p) {
        return (y1 > p) && (y2 < p);
    }

    private double getY(Dot dot, double w1, double w2) {
        return w1 * dot.x + w2 * dot.y;
    }

    private double recalculateW(double x, double w, double delta, double sigma) {
        return w + delta * x * sigma;
    }

    @Data
    @AllArgsConstructor
    public static class Dot {
        private double x;
        private double y;
    }

    @Data
    @AllArgsConstructor
    public static class Result {
        private double w1;
        private double w2;
    }
}

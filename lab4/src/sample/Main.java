package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static final int n = 5;
    private static final int W_MAX = 1200;
    private static final int N = 1024;
    private static final double LOWER_T = 0;
    private static final double UPPER_T = 1;

    public static void main(String[] args) {
        launch(args);
    }

    private static List<Double> doubleRange(double lower, double upper, double step) {
        List<Double> range = new ArrayList<>();
        for (double i = lower; i < upper; i += step) {
            range.add(i);
        }
        return range;
    }

    private static List<Integer> intRange(int lower, int upper, int step) {
        List<Integer> range = new ArrayList<>();
        for (int i = lower; i < upper; i += step) {
            range.add(i);
        }
        return range;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lab4");

        List<Double> t = new ArrayList<>();
        List<Double> x = new ArrayList<>();
        List<Double> dftData = new ArrayList<>();
        List<Long> times = new ArrayList<>();
        List<Double> dft2Data = new ArrayList<>();
        List<Long> times2 = new ArrayList<>();
        List<Double> fftData = new ArrayList<>();
        List<Long> times3 = new ArrayList<>();
        Functions functions = new Functions();
        for (int i = 256; i <= N; i *= 2) {

            t = doubleRange(LOWER_T, UPPER_T, (UPPER_T - LOWER_T) / i);
            x = functions.generateSignal(n, W_MAX, N);

            long timestamp = System.currentTimeMillis();
            dftData = functions.DFT(x);
            timestamp = System.currentTimeMillis() - timestamp;
            times.add(timestamp);

            timestamp = System.currentTimeMillis();
            dft2Data = functions.DFT2(x);
            timestamp = System.currentTimeMillis() - timestamp;
            times2.add(timestamp);

            timestamp = System.currentTimeMillis();
            fftData = functions.FFT(x);
            timestamp = System.currentTimeMillis() - timestamp;
            times3.add(timestamp);
        }

        Plot signalPlot = new Plot("X", "t", "");
        signalPlot.addSeries("X", t, x);

        Plot dftPlot = new Plot("DFT", "p", "");
        dftPlot.addSeries("DFT", intRange(0, dftData.size(), 1), dftData);

        Plot dft2Plot = new Plot("DFT2", "p", "");
        dft2Plot.addSeries("DFT2", intRange(0, dft2Data.size(), 1), dft2Data);

        Plot fftPlot = new Plot("FFT", "p", "");
        fftPlot.addSeries("FFT", intRange(0, fftData.size(), 1), fftData);

        Plot timePlot = new Plot("T", "N", "");
        timePlot.addSeries("T1", intRange(0, N, 1), times);
        timePlot.addSeries("T2", intRange(0, N, 1), times2);
        timePlot.addSeries("T3", intRange(0, N, 1), times3);

        TabPane root = new TabPane();
        root.getTabs().addAll(new Tab("Signal", signalPlot), new Tab("DFT", dftPlot), new Tab("DFT2", dft2Plot), new Tab("FFT", fftPlot), new Tab("T", timePlot));

        primaryStage.setScene(new Scene(root, 1024, 640));
        primaryStage.show();
    }
}

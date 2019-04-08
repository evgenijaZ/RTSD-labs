package edu.kpi.comsys;

import edu.kpi.comsys.model.Scheduler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static final String LOAD = "Load";
    public static final String REJECTS = "Rejects";
    public static final String FINISHED = "Finished";
    public static final String TIMES = "Process times";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lab5");
        Scheduler scheduler = new Scheduler();
        scheduler.run();

        List<Integer> loadData = scheduler.getLoad();
        List<Integer> rejectsData = scheduler.getRejects();
        List<Integer> finishedData = scheduler.getFinished();
        List<Integer> processTimesData = scheduler.getTimes();


        Plot loadPlot = new Plot(LOAD, "time", "load");
        loadPlot.addSeries(LOAD, intRange(0, loadData.size(), 1), loadData);
        Plot rejectsPlot = new Plot(REJECTS, "time", "rejects");
        rejectsPlot.addSeries(REJECTS, intRange(0, rejectsData.size(), 1), rejectsData);
        Plot finishedPlot = new Plot(FINISHED, "time", "finished");
        finishedPlot.addSeries(FINISHED, intRange(0, finishedData.size(), 1), finishedData);
        Plot processTimesPlot = new Plot(TIMES, "time", "process times");
        processTimesPlot.addSeries(TIMES, intRange(0, processTimesData.size(), 1), processTimesData);


        TabPane root = new TabPane();
        root.getTabs().addAll(new Tab(LOAD, loadPlot), new Tab(REJECTS, rejectsPlot), new Tab(FINISHED, finishedPlot), new Tab(TIMES, processTimesPlot));

        primaryStage.setScene(new Scene(root, 1024, 640));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    private static List<Integer> intRange(int lower, int upper, int step) {
        List<Integer> range = new ArrayList<>();
        for (int i = lower; i < upper; i += step) {
            range.add(i);
        }
        return range;
    }
}
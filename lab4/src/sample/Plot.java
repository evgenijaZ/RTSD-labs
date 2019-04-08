package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.util.List;

class Plot extends LineChart<Number, Number> {

    Plot(String title, String xLabel, String yLabel) {
        super(new NumberAxis(), new NumberAxis());
        this.getXAxis().setLabel(xLabel);
        this.getYAxis().setLabel(yLabel);
        this.setTitle(title);
        this.setCreateSymbols(false);
    }

    void addSeries(String name, List<? extends Number> xData, List<? extends Number> yData) {
        Series<Number, Number> series = new Series<>();
        series.setName(name);
        for (int i = 0; i < xData.size() && i < yData.size(); i++) {
            series.getData().add(new Data<>(xData.get(i), yData.get(i)));
        }
        this.getData().add(series);
    }
}

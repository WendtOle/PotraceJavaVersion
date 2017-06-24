package original;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class PlotterRunTime extends Application {

        public static String title = "Line Chart";
        public static final String xAxisLabel = "AmountOfRuns";
        public static String yAxisLabel = "MS per Run";
        public static int stepss;

        private static double[] dataY;

        public static void plot(int steps, double[] yValues, String name) {
            title = name;
            dataY = yValues;
            stepss = steps;
            Application.launch(new String[0]);
        }

        @SuppressWarnings("unchecked")
        @Override public void start(Stage stage) {

            stage.setTitle(title);

            final NumberAxis xAxis = new NumberAxis();
            xAxis.setLabel(xAxisLabel);
            final NumberAxis yAxis = new NumberAxis(dataY[99] - 0.1, dataY[99]+0.1, 0.01);
            yAxis.setLabel(yAxisLabel);

            final LineChart<Number, Number> sc = new LineChart<>(xAxis, yAxis);

            XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
            series1.setName("Data");
            for (int i = 0; i < dataY.length; i++) {
                series1.getData().add(new XYChart.Data<Number, Number>(i * stepss, (float)dataY[i]));
            }

            sc.setAnimated(false);
            sc.setCreateSymbols(true);

            sc.getData().addAll(series1);

            Scene scene = new Scene(sc, 500, 400);
            stage.setScene(scene);
            stage.show();
        }
    }

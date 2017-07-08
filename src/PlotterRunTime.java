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
        public static String locationOfTestPicture;

        private static double[][] dataY;

        public static void plot(int steps, double[][] yValues, String name,String location) {
            title = name;
            dataY = yValues;
            stepss = steps;
            locationOfTestPicture = location;
            Application.launch(new String[0]);
        }

        @SuppressWarnings("unchecked")
        @Override public void start(Stage stage) {

            stage.setTitle(title + locationOfTestPicture);

            final NumberAxis xAxis = new NumberAxis();
            xAxis.setLabel(xAxisLabel);

            double middle = (dataY[0][99] + dataY[1][99]) / 2;
            double range = Math.abs(dataY[0][99] - dataY[1][99]);

            final NumberAxis yAxis = new NumberAxis(middle - range*3 >= 0 ? middle - range*3 : 0, middle + range*3 , range / 4);
            yAxis.setLabel(yAxisLabel);

            final LineChart<Number, Number> sc = new LineChart<>(xAxis, yAxis);

            XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
            XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
            series1.setName("Refactored");
            series2.setName("Original");
            for (int i = 0; i < dataY[0].length; i++) {
                series1.getData().add(new XYChart.Data<Number, Number>(i * stepss, (float)dataY[0][i]));
                series2.getData().add(new XYChart.Data<Number, Number>(i * stepss, (float)dataY[1][i]));
            }

            sc.setAnimated(false);
            sc.setCreateSymbols(true);

            sc.getData().addAll(series1);
            sc.getData().addAll(series2);

            Scene scene = new Scene(sc, 500, 400);
            stage.setScene(scene);
            stage.show();
        }
    }

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
        public static int amountOfRuns;
        public static String locationOfTestPicture;
        public static String[] decomposerKinds;

        private static double[][] dataY;

        public static void plot(int amountOfRunsInput, double[][] yValues,String location) {
            dataY = yValues;
            amountOfRuns = amountOfRunsInput -1;
            locationOfTestPicture = location;
            Application.launch(new String[0]);

        }

        @SuppressWarnings("unchecked")
        @Override public void start(Stage stage) {

            stage.setTitle(title + " " + locationOfTestPicture);

            final NumberAxis xAxis = new NumberAxis();
            xAxis.setLabel(xAxisLabel);

            double middle = getMiddleValue();
            double range = getRange();

            final NumberAxis yAxis = new NumberAxis(middle - range * 2 > 0 ? middle - range * 2 : 0, middle + range   * 4 , range / 2);
            yAxis.setLabel(yAxisLabel);

            final LineChart<Number, Number> sc = new LineChart<>(xAxis, yAxis);

            for(int i = 0 ; i < dataY.length; i++) {
                XYChart.Series<Number, Number> currentSeries = new XYChart.Series<>();
                currentSeries.setName(DecompositionEnumAll.values()[i].toString());
                for (int j = 0; j < amountOfRuns; j += amountOfRuns / 20) {
                    currentSeries.getData().add(new XYChart.Data<Number, Number>(j , (float)dataY[i][j]));
                }
                sc.getData().addAll(currentSeries);
            }

            sc.setAnimated(false);
            sc.setCreateSymbols(true);

            Scene scene = new Scene(sc, 500, 400);
            stage.setScene(scene);
            stage.show();
        }

    private double getRange() {
            if (dataY.length == 1)
                return dataY[0][amountOfRuns];

        double biggestValues = 0;
        double smallestValue = 10000000;
        for(int i = 0; i < dataY.length; i++) {
            double currentValue = dataY[i][amountOfRuns];
            if (biggestValues < currentValue)
                biggestValues = currentValue;
            if(smallestValue > currentValue)
                smallestValue = currentValue;
        }
        return biggestValues - smallestValue;
    }

    private double getMiddleValue() {
            double allValues = 0;
            for(int i = 0; i < dataY.length; i++) {
                allValues += dataY[i][amountOfRuns];
            }
        return allValues / dataY.length;
    }
}

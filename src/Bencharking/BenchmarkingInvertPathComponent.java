package Bencharking;

import AdditionalCode.Input.JSONDeEncoder;
import General.Bitmap;
import General.Path;
import org.openjdk.jmh.annotations.*;
import original.Decompose;
import refactored.FindPath;
import refactored.PathInverter;
import refactored.TurnPolicyEnum;

import java.awt.*;
import java.io.IOException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by andreydelany on 10.07.17.
 */
public class BenchmarkingInvertPathComponent {
    @State(Scope.Thread)
    public static class MySate {

        Bitmap bitmap;
        Path path;

        @Setup
        public void setBitmap(){
            try {
                bitmap = JSONDeEncoder.readBitmapFromJSon("01.json","testPictures");
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Setup
        public void setUpPath(){
            Point firstPoint = new Point(0,99);
            FindPath pathFinder = new FindPath(bitmap,firstPoint,43, TurnPolicyEnum.MINORITY);
            this.path = pathFinder.getPath();
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureRefactored(MySate state) throws InterruptedException {
        PathInverter pathInverter = new PathInverter(state.bitmap);
        pathInverter.invertPathOnBitmap(state.path);
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureOriginal(MySate state) throws InterruptedException {
        Decompose.xor_path(state.bitmap,state.path);
    }
}

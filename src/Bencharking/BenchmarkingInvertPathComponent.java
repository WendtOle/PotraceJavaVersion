package Bencharking;

import AdditionalCode.Input.JSONDeEncoder;
import Potrace.General.Bitmap;
import Potrace.General.Path;
import org.openjdk.jmh.annotations.*;
import Potrace.original.Decompose;
import Potrace.refactored.FindPath;
import Potrace.refactored.PathInverter;
import Potrace.refactored.TurnPolicyEnum;

import java.awt.*;
import java.io.IOException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

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
    @OutputTimeUnit(NANOSECONDS)
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
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureOriginal(MySate state) throws InterruptedException {
        Decompose.xor_path(state.bitmap,state.path);
    }
}

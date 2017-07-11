package Bencharking;

import AdditionalCode.Input.JSONDeEncoder;
import Potrace.General.Bitmap;
import Potrace.General.Param;
import org.openjdk.jmh.annotations.*;
import Potrace.original.Decompose;
import Potrace.refactored.FindPath;
import Potrace.refactored.TurnPolicyEnum;

import java.awt.*;
import java.io.IOException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Created by andreydelany on 10.07.17.
 */
public class BenchmarkingFindPathComponent {

    @State(Scope.Thread)
    public static class MySate {

        Bitmap bitmap;
        Potrace.General.Param params;
        TurnPolicyEnum turnPolicy = TurnPolicyEnum.MINORITY;
        int turnPolicyAsInt = turnPolicy.ordinal();
        int sign = 43;
        int x0 = 0;
        int y0 = 100;
        Point firstPoint = new Point(0,99);

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
        public void setUpParams() {
            params = new Param();
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
        FindPath findPath = new FindPath(state.bitmap,state.firstPoint,state.sign,state.turnPolicy);
        findPath.getPath();
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureOriginal(MySate state) throws InterruptedException {
        Decompose.findpath(state.bitmap,state.x0,state.y0,state.sign,state.turnPolicyAsInt);
    }
}

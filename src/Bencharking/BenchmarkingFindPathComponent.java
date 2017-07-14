package Bencharking;

import AdditionalCode.FileInputOutput.JsonEncoder;
import Potrace.General.Bitmap;
import Potrace.General.Param;
import Potrace.original.Decompose;
import Potrace.refactored.PathFindingCharacteristics;
import Potrace.refactored.FindPath;
import Potrace.refactored.PathKindEnum;
import Potrace.refactored.TurnPolicyEnum;
import org.openjdk.jmh.annotations.*;

import java.awt.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class BenchmarkingFindPathComponent {

    @State(Scope.Thread)
    public static class MySate {

        Bitmap bitmap;
        Potrace.General.Param params;
        TurnPolicyEnum turnPolicy = TurnPolicyEnum.MINORITY;
        int turnPolicyAsInt = turnPolicy.ordinal();
        PathKindEnum kindOfPath = PathKindEnum.POSITIV;
        int sign = kindOfPath.getIntRepresentation();
        int x0 = 0;
        int y0 = 100;
        Point firstPoint = new Point(0,99);

        @Setup
        public void setBitmap(){
            JsonEncoder encoder = new JsonEncoder("01.json","testPictures");
            bitmap = encoder.getBitmap();
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
    @Fork(3)
    @Threads(1)
    public void mesureRefactored(MySate state) throws InterruptedException {
        FindPath findPath = new FindPath(state.bitmap,state.firstPoint,new PathFindingCharacteristics(state.turnPolicy,state.kindOfPath));
        findPath.getPath();
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(3)
    @Threads(1)
    public void mesureOriginal(MySate state) throws InterruptedException {
        Decompose.findpath(state.bitmap,state.x0,state.y0,state.sign,state.turnPolicyAsInt);
    }
}

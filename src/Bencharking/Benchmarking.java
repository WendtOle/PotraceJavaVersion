package Bencharking;

import AdditionalCode.FileInputOutput.JsonEncoder;
import Potrace.General.Bitmap;
import Potrace.General.DecompositionInterface;
import Potrace.General.Param;
import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;


public class Benchmarking{

    @State(Scope.Thread)
    public static class MySate {

        Bitmap bitmap;
        Potrace.General.Param params;

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
    @Measurement(iterations = 5, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(10)
    @Threads(1)
    public void mesureRefactored(MySate state) throws InterruptedException {
        DecompositionInterface decomposer = new Potrace.refactored.Decompose();
        decomposer.getPathList(state.bitmap,state.params);
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 5, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(10)
    @Threads(1)
    public void mesureOriginal(MySate state) throws InterruptedException {
        DecompositionInterface decomposer = new Potrace.original.Decompose();
        decomposer.getPathList(state.bitmap,state.params);
    }
}
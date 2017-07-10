package Bencharking;

import AdditionalCode.Input.JSONDeEncoder;
import DecompositionKindsForTesting.*;
import General.Bitmap;
import General.DecompositionInterface;
import General.Param;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by andreydelany on 10.07.17.
 */

public class Benchmarking{

    @State(Scope.Thread)
    public static class MySate {

        Bitmap bitmap;
        General.Param params;

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
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureRefactored(MySate state) throws InterruptedException {
        DecompositionInterface decomposer = new refactored.Decompose();
        decomposer.getPathList(state.bitmap,state.params);
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureOriginal(MySate state) throws InterruptedException {
        DecompositionInterface decomposer = new original.Decompose();
        decomposer.getPathList(state.bitmap,state.params);
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureRefactored_PathListSwopped(MySate state) throws InterruptedException {
        DecompositionInterface decomposer = new DecomposeWithOriginalPathListToTree();
        decomposer.getPathList(state.bitmap,state.params);
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureRefactored_PathInverterSwopped(MySate state) throws InterruptedException {
        DecompositionInterface decomposer = new DecomposeWithOriginalPathInverterOnlyInDecompose();
        decomposer.getPathList(state.bitmap,state.params);
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureRefactored_FindFilledPixelAndFindPath(MySate state) throws InterruptedException {
        DecompositionInterface decomposer = new OriginFindFilledPixelAndFindPath();
        decomposer.getPathList(state.bitmap,state.params);
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureRefactored_FindPath(MySate state) throws InterruptedException {
        DecompositionInterface decomposer = new OriginalFindPath();
        decomposer.getPathList(state.bitmap,state.params);
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureRefactored_FindNextFilledPixel(MySate state) throws InterruptedException {
        DecompositionInterface decomposer = new OrigFindNextFilledPixel();
        decomposer.getPathList(state.bitmap,state.params);
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureRefactored_PathListToTreeAndPathInverter(MySate state) throws InterruptedException {
        DecompositionInterface decomposer = new OrigPathListToTreeOrigPathInverterInDecomp();
        decomposer.getPathList(state.bitmap,state.params);
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureRefactored_fakeOriginal(MySate state) throws InterruptedException {
        DecompositionInterface decomposer = new FakeOriginal();
        decomposer.getPathList(state.bitmap,state.params);
    }
}
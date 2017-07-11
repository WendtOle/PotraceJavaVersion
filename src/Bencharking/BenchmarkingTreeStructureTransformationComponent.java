package Bencharking;

import AdditionalCode.FileInputOutput.JsonEncoder;
import Potrace.General.Bitmap;
import Potrace.General.DecompositionInterface;
import Potrace.General.Param;
import Potrace.General.Path;
import Potrace.original.Decompose;
import Potrace.refactored.TreeStructurTransformation;
import Potrace.refactored.TreeStructurTransformationInterface;
import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Created by andreydelany on 10.07.17.
 */
public class BenchmarkingTreeStructureTransformationComponent {
    @State(Scope.Thread)
    public static class MySate {


        Path path;
        Bitmap workCopy;

        public Bitmap getBitmap(){
            JsonEncoder encoder = new JsonEncoder("01.json","testPictures");
            return encoder.getBitmap();
        }

        @Setup
        public void setBitmap(){
            DecompositionInterface decomposer = new DecomposeHelperForTreeStructureTransformation();
            path = decomposer.getPathList(getBitmap(),new Param());
            workCopy = decomposer.getWorkCopy();

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
        TreeStructurTransformationInterface transformator = new TreeStructurTransformation(state.path,state.workCopy);
        transformator.getTreeStructure();
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(5)
    @Threads(2)
    public void mesureOriginal(MySate state) throws InterruptedException {
        Decompose.pathlist_to_tree(state.path,state.workCopy);
    }
}

package Bencharking;

import AdditionalCode.FileInputOutput.JsonEncoder;
import Potrace.General.Bitmap;
import Potrace.General.Param;
import Potrace.General.Path;
import Potrace.original.Decompose;
import Potrace.refactored.FindPathsOnBitmap;
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
        Bitmap bitmap,emptyBitmap;

        public Bitmap getBitmap(){
            JsonEncoder encoder = new JsonEncoder("01.json","testPictures");
            return encoder.getBitmap();
        }

        @Setup
        public void setBitmap(){
            this.bitmap = getBitmap();
            this.emptyBitmap = new Bitmap(bitmap.w,bitmap.h);
            FindPathsOnBitmap findPathsOnBitmap = new FindPathsOnBitmap(bitmap,new Param());
            path = findPathsOnBitmap.getPathList();
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(10)
    @Threads(1)
    public void mesureRefactored(MySate state) throws InterruptedException {
        TreeStructurTransformationInterface transformator = new TreeStructurTransformation(state.path,state.emptyBitmap);
        transformator.getTreeStructure();
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(10)
    @Threads(1)
    public void mesureOriginal(MySate state) throws InterruptedException {
        Decompose.pathlist_to_tree(state.path,state.emptyBitmap);
    }
}

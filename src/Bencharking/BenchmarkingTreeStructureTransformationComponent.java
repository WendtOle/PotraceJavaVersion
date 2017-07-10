package Bencharking;

import AdditionalCode.Input.JSONDeEncoder;
import General.Bitmap;
import General.DecompositionInterface;
import General.Param;
import General.Path;
import org.openjdk.jmh.annotations.*;
import original.Decompose;
import refactored.TreeStructurTransformation;
import refactored.TreeStructurTransformationInterface;

import java.io.IOException;

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
            Bitmap bitmap = new Bitmap();
            try {
                bitmap = JSONDeEncoder.readBitmapFromJSon("01.json","testPictures");
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
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

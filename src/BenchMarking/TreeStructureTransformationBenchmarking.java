package BenchMarking;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.General.Param;
import Potrace.General.Path;
import Potrace.original.Decompose;
import Potrace.refactored.FindAllPathsOnBitmap;
import Potrace.refactored.ListToTreeTransformation;
import Potrace.refactored.ListToTreeTransformationInterface;
import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TreeStructureTransformationBenchmarking {
    @State(Scope.Thread)
    public static class TestData {


        Path[] paths;
        Bitmap[] bitmaps;

        @Setup
        public void setUp(){
            BitmapImporter importer = new BitmapImporter("benchmarkingPictures");
            bitmaps = importer.getAllBitmaps();

            setPaths();
        }

        public void setPaths(){
            paths = new Path[bitmaps.length];
            for (int bitmapIndex = 0; bitmapIndex < bitmaps.length; bitmapIndex++) {
                FindAllPathsOnBitmap findAllPathsOnBitmap = new FindAllPathsOnBitmap(bitmaps[bitmapIndex],new Param());
                paths[bitmapIndex] = findAllPathsOnBitmap.getPathList();
            }
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(2)
    @Threads(1)
    public void mesureRefactored(TestData data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            ListToTreeTransformationInterface transformator = new ListToTreeTransformation(data.paths[bitmapIndex],data.bitmaps[bitmapIndex]);
            transformator.getTreeStructure();
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(2)
    @Threads(1)
    public void mesureOriginal(TestData data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            Decompose.pathlist_to_tree(data.paths[bitmapIndex],data.bitmaps[bitmapIndex]);
        }
    }
}

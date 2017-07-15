package Bencharking;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.General.Param;
import Potrace.General.Path;
import Potrace.original.Decompose;
import Potrace.refactored.PathInverter;
import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class InvertPath {

    @State(Scope.Thread)
    public static class TestData {

        Bitmap[] bitmaps;
        Path[] paths;

        @Setup
        public void setUp(){
            BitmapImporter importer = new BitmapImporter("benchmarkingPictures");
            bitmaps = importer.getAllBitmaps();

            setPaths();
        }

        public void setPaths(){
            paths = new Path[bitmaps.length];
            for (int bitmapIndex = 0; bitmapIndex < bitmaps.length; bitmapIndex++) {
                Potrace.refactored.Decompose decomposer = new Potrace.refactored.Decompose();
                paths[bitmapIndex] = decomposer.getPathList(bitmaps[bitmapIndex],new Param());
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
    public void refactored(TestData data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            PathInverter pathInverter = new PathInverter(data.bitmaps[bitmapIndex]);
            pathInverter.invertPathOnBitmap(data.paths[bitmapIndex]);
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(2)
    @Threads(1)
    public void original(TestData data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            Decompose.xor_path(data.bitmaps[bitmapIndex],data.paths[bitmapIndex]);
        }
    }
}

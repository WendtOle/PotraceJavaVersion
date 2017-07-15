package BenchMarking;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.General.DecompositionInterface;
import Potrace.General.Param;
import Potrace.General.Path;
import Potrace.original.Decompose;
import Potrace.refactored.*;
import org.openjdk.jmh.annotations.*;

import java.awt.*;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class CompletePotraceAlgorithm {

    final static int amountOfWarmUpRounds = 10;
    final static int amountOfMesuringRounds = 10;
    final static int amountOfForks = 2;
    final static int amountOfThreads = 2;
    final static int msPerRound = 500;
    final static String testBitmapFolder = "benchMarkingPictures02";

    @State(Scope.Thread)
    public static class BasicTestData {

        Bitmap[] bitmaps;
        Param params = new Param();

        @Setup
        public void setBitmap(){
            BitmapImporter importer = new BitmapImporter(testBitmapFolder);
            bitmaps = importer.getAllBitmaps();
        }
    }

    @State(Scope.Thread)
    public static class TestDataForInvertingPathBenchmark {

        Bitmap[] bitmaps;
        Path[] paths;

        @Setup
        public void setBitmap(){
            BitmapImporter importer = new BitmapImporter(testBitmapFolder);
            bitmaps = importer.getAllBitmaps();
            paths = new Path[bitmaps.length];
            for (int bitmapIndex = 0; bitmapIndex < bitmaps.length; bitmapIndex++) {
                Potrace.refactored.Decompose decomposer = new Potrace.refactored.Decompose();
                paths[bitmapIndex] = decomposer.getPathList(bitmaps[bitmapIndex],new Param());
            }
        }
    }

    @State(Scope.Thread)
    public static class TestDataForFindingPathBenchmark {

        Bitmap[] bitmaps;

        TurnPolicyEnum turnPolicy = TurnPolicyEnum.MINORITY;
        int turnPolicyAsInt = turnPolicy.ordinal();
        PathKindEnum kindOfPath = PathKindEnum.POSITIV;
        int sign = kindOfPath.getIntRepresentation();

        Point[] firstFilledPixelsAsPoint;
        int[][] firstFilledPixelsAsInt;

        @Setup
        public void setBitmap(){
            BitmapImporter importer = new BitmapImporter(testBitmapFolder);
            bitmaps = importer.getAllBitmaps();
            firstFilledPixelsAsPoint = new Point[bitmaps.length];
            firstFilledPixelsAsInt = new int[bitmaps.length][2];
            for (int bitmapIndex = 0; bitmapIndex < bitmaps.length; bitmapIndex++) {
                NextFilledPixelFinder filledPixelFinder = new NextFilledPixelFinder(bitmaps[bitmapIndex]);
                Point firstFilledPixel = filledPixelFinder.getPositionOfNextFilledPixel();
                firstFilledPixelsAsPoint[bitmapIndex] = firstFilledPixel;
                firstFilledPixelsAsInt[bitmapIndex][0] = firstFilledPixel.x;
                firstFilledPixelsAsInt[bitmapIndex][1] = firstFilledPixel.y + 1;
            }
        }
    }

    @State(Scope.Thread)
    public static class TestDataForTreeStructureBenchmark {

        Bitmap[] bitmaps;
        Path[] unstructuredPaths;

        @Setup
        public void setBitmap(){
            BitmapImporter importer = new BitmapImporter(testBitmapFolder);
            bitmaps = importer.getAllBitmaps();
            unstructuredPaths = new Path[bitmaps.length];
            for (int bitmapIndex = 0; bitmapIndex < bitmaps.length; bitmapIndex++) {
                FindAllPathsOnBitmap findAllPathsOnBitmap = new FindAllPathsOnBitmap(bitmaps[bitmapIndex],new Param());
                unstructuredPaths[bitmapIndex] = findAllPathsOnBitmap.getPathList();
            }
        }

    }


    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void completeRefactored(BasicTestData data) throws InterruptedException {
        for(Bitmap currentBitmap : data.bitmaps) {
            DecompositionInterface decomposer = new Potrace.refactored.Decompose();
            decomposer.getPathList(currentBitmap,data.params);
        }
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void completeOriginal(BasicTestData data) throws InterruptedException {
        for(Bitmap currentBitmap : data.bitmaps) {
            DecompositionInterface decomposer = new Potrace.original.Decompose();
            decomposer.getPathList(currentBitmap,data.params);
        }
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void findPathRefactored(TestDataForFindingPathBenchmark data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            Bitmap bitmap = data.bitmaps[bitmapIndex];
            Point point = data.firstFilledPixelsAsPoint[bitmapIndex];
            Potrace.refactored.FindPath findPath = new Potrace.refactored.FindPath(bitmap, point, new PathFindingCharacteristics(data.turnPolicy, data.kindOfPath));
            findPath.getPath();
        }
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void findPathOriginal(TestDataForFindingPathBenchmark data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            Bitmap bitmap = data.bitmaps[bitmapIndex];
            int x0 = data.firstFilledPixelsAsInt[bitmapIndex][0];
            int y0 = data.firstFilledPixelsAsInt[bitmapIndex][1];
            Decompose.findpath(bitmap,x0,y0,data.sign,data.turnPolicyAsInt);
        }
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void invertPathRefactored(TestDataForInvertingPathBenchmark data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            PathInverter pathInverter = new PathInverter(data.bitmaps[bitmapIndex]);
            pathInverter.invertPathOnBitmap(data.paths[bitmapIndex]);
        }
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void invertPathOriginal(TestDataForInvertingPathBenchmark data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            Decompose.xor_path(data.bitmaps[bitmapIndex],data.paths[bitmapIndex]);
        }
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MICROSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void findFilledPixelRefactored(BasicTestData data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            NextFilledPixelFinder nextFilledPixelFinder = new NextFilledPixelFinder(data.bitmaps[bitmapIndex]);
            nextFilledPixelFinder.getPositionOfNextFilledPixel();
        }
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MICROSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void findFilledPixelOriginal(BasicTestData data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            Bitmap bitmap = data.bitmaps[bitmapIndex];
            Decompose.findnext(bitmap,new Point(0,bitmap.h-1));
        }
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void treeStructureTransformationRefactored(TestDataForTreeStructureBenchmark data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            ListToTreeTransformationInterface transformator = new ListToTreeTransformation(data.unstructuredPaths[bitmapIndex],data.bitmaps[bitmapIndex]);
            transformator.getTreeStructure();
        }
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void treeStructureTransformationOriginal(TestDataForTreeStructureBenchmark data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            Decompose.pathlist_to_tree(data.unstructuredPaths[bitmapIndex],data.bitmaps[bitmapIndex]);
        }
    }
}
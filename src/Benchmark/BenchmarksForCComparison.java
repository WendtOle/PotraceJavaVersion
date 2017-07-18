package Benchmark;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.General.DecompositionInterface;
import Potrace.General.Path;
import Potrace.General.Trace;
import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class BenchmarksForCComparison {

    final static int amountOfWarmUpRounds = 20;
    final static int amountOfMesuringRounds = 20;
    final static int amountOfForks = 5;
    final static int amountOfThreads = 1;
    final static int msPerRound = 500;
    final static String testBitmapFolder = "src/Benchmark/benchmarkingPictures";

    @State(Scope.Thread)
    public static class TestData01Sketch {

        Bitmap bitmap;
        Potrace.General.Param param = new Potrace.General.Param();

        @Setup
        public void setBitmap(){
            BitmapImporter importer = new BitmapImporter(testBitmapFolder);
            bitmap = importer.getBitmap("01_sketch.jpg");
        }
    }

    @State(Scope.Thread)
    public static class TestData02Picture {

        Bitmap bitmap;
        Potrace.General.Param param = new Potrace.General.Param();

        @Setup
        public void setBitmap(){
            BitmapImporter importer = new BitmapImporter(testBitmapFolder);
            bitmap = importer.getBitmap("02_picture.jpg");
        }
    }

    @State(Scope.Thread)
    public static class TestData03Logo {

        Bitmap bitmap;
        Potrace.General.Param param = new Potrace.General.Param();

        @Setup
        public void setBitmap(){
            BitmapImporter importer = new BitmapImporter(testBitmapFolder);
            bitmap = importer.getBitmap("03_logo.jpg");
        }
    }

    @State(Scope.Thread)
    public static class TestData04Writing {

        Bitmap bitmap;
        Potrace.General.Param param = new Potrace.General.Param();

        @Setup
        public void setBitmap(){
            BitmapImporter importer = new BitmapImporter(testBitmapFolder);
            bitmap = importer.getBitmap("04_writing.jpg");
        }
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void sketch01(TestData01Sketch data) throws InterruptedException {
        DecompositionInterface decomposer = new Potrace.original.Decompose();
        Path plist = decomposer.getPathList(data.bitmap, data.param);
        plist = Trace.process_path(plist, data.param);
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void picture02(TestData02Picture data) throws InterruptedException {
        DecompositionInterface decomposer = new Potrace.original.Decompose();
        Path plist = decomposer.getPathList(data.bitmap, data.param);
        plist = Trace.process_path(plist, data.param);
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void logo03(TestData03Logo data) throws InterruptedException {
        DecompositionInterface decomposer = new Potrace.original.Decompose();
        Path plist = decomposer.getPathList(data.bitmap, data.param);
        plist = Trace.process_path(plist, data.param);
    }

    @Benchmark
    @Warmup(iterations = amountOfWarmUpRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @Measurement(iterations = amountOfMesuringRounds, time = msPerRound, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(amountOfForks)
    @Threads(amountOfThreads)
    public void writing04(TestData04Writing data) throws InterruptedException {
        DecompositionInterface decomposer = new Potrace.original.Decompose();
        Path plist = decomposer.getPathList(data.bitmap, data.param);
        plist = Trace.process_path(plist, data.param);
    }

}
package Bencharking;

import Potrace.General.Bitmap;
import org.openjdk.jmh.annotations.*;
import Potrace.original.Decompose;
import Potrace.refactored.NextFilledPixelFinder;

import java.awt.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Created by andreydelany on 10.07.17.
 */
public class BenchmarkingNextFilledPixelFinderComponent {
    @State(Scope.Thread)
    public static class MySate {

        Bitmap bitmap;

        @Setup
        public void setBitmap(){
            this.bitmap = new Bitmap(128,128);
            this.bitmap.map[245] = 0x1;
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
        NextFilledPixelFinder nextFilledPixelFinder = new NextFilledPixelFinder(state.bitmap);
        nextFilledPixelFinder.getPositionOfNextFilledPixel();
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(10)
    @Threads(1)
    public void mesureOriginal(MySate state) throws InterruptedException {
        Decompose.findnext(state.bitmap,new Point(0,state.bitmap.h-1));
    }
}

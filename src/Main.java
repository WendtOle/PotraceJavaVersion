import java.awt.*;

/**
 * Created by andreydelany on 06/03/2017.
 */
public class Main {
    public static void main(String [] args)
    {
        potrace_param param = new potrace_param();
        potrace_bitmap bm = new potrace_bitmap(8,8);
        bm.default_bitmap_normalSecond();
        potrace_path result = PotraceLib.potrace_trace(param,bm);
    }
}

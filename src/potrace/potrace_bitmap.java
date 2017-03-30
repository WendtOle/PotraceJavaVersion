package potrace;

/**
 * Created by andreydelany on 04/03/2017.
 */

    //Ole:
    /* Im Originalen bitmap File wurde der Typ Bitmap nicht definiert sondern dieser wurde in PotraceLib definiert,
    was nicht wirklich viel Sinn macht. Das Bitmap file wurde dagegen für Funktionen die die Bitmap etwas angehen verwendet.
    Ich habe mich dafür entschieden, die Typdefinition des Bitmap bildes, mit in dieses File zu legen,
    da es einerseits notwendig ist. Und andererseits grundlegender Java Logik folgt.
    Außerdem habe ich die Methoden die den State der Bitmap veränden noch Static gelassen,
    die dann eine bitmap wieder zurückgeben. Sinnvoll wäre allerdings, diese zu KlassenMethoden zu machen,
    die direkt die aktuelle Bitmap verändern. Ich denke, auch das diess vertretbar ist,
    da es ein Prinzip von Java ist und zum anderen, ich es nur gemacht, habe, da in C eingabe gleich ausgabe parameter sind,
    und dies in Java nicht möglich ist.*/

    //TODO: Static Methoden in Klassen Methoden verwandeln

    /* Im Originalen wurden die Bits oder Pixel in sogenannten Potrace_words abgespeichert.
    Ein Potrace_Word war im wesentlichen ein "unsigned long" -> 64 Bit.
    Ich habe aus bequemlichkeit diese Implementierung auf 32 Bits heruntergeschraubt und konnte so einfacher mit Integer arbeiten.
    Theoretisch ist mit Long genauso leicht zu arbeiten wie mit Integer, braucht nur etwas einarbeitungszeit.
    Zum Beispiel:
    long test = 0x9999999999999999L;
     */

    //TODO: Change Implementation of PotraceWord = Integer to PotraceWord = Long

    /* Ich halte es für sinnvoll die gesamten Methoden die sich mit der Bitmanipulation beschäftigen, in eine extra Klasse zu packen.
    Da dies aber wirklich der Cleanness Grad beeinflusst, gehört diese Aufgabe erst an einen späteren Zeitpunkt */

    //TODO: Bitmanipulations Methoden in eine eigene Klasse

    /* Im Originalen Code wird oftmals mit BM_WordSize und BM_WordBits herrumhantiert.
    Oftmals kommt es, wenn man genauer in den Code schaut nur darauf an wieviel Bits in einem Potrace_Word stecken,
    deshalb habe ich in der Variable PIXELINWORD diese Anzahl abgespeichert.
    Und der gesamte Algorthmus bezieht sich auf diese Variable */

public class potrace_bitmap {

    public static int PIXELINWORD = 64;
    static long BM_ALLBITS = (~0);
    static long BM_HIBIT = 1 << PIXELINWORD -1;

    public int w, h;              /* width and height, in pixels */
    public int dy;                /* words per scanline (not bytes) */
    public long[] map;             /* raw data, dy*h words */ //TODO changed representation of potrace words


    //Ole:
    /* Ich habe nur die Macros kopiert und in normale Methoden verwandelt die wirklich gebraucht wurden.
    Es gibt ungefähr die doppelte Anzahl an Macros. */

    //Entwickler:
    /* macros for accessing pixel at index (x,y). U* macros omit the bounds check. */

    private boolean bm_range(int x, int a) {
        return (x) >= 0 && (x) < (a);
    }

    private boolean bm_range(double x, double a) {
        return bm_range((int)x,(int)a);
    }

    private boolean bm_safe(int x, int y) {
        return bm_range(x, this.w) && bm_range(y, this.h);
    }

    private long bm_mask(int x) {
        return ((1L) << (PIXELINWORD-1-(x)));
    }

    private boolean BM_UGET(int x, int y) {
        return(bm_index(x, y) & bm_mask(x)) != 0;
    }

    public boolean BM_GET(int x, int y) {
        return bm_safe(x, y) ? BM_UGET(x, y) : false;
    }

    private long[] bm_scanline(int y) {
        long[] scanLine = new long[this.dy];
        for (int i = 0; i < this.dy ; i ++) {
            scanLine[i] = this.map[(y * this.dy) + i];
        }
        return scanLine;
    }

    public long bm_index(int x, int y) {
        return bm_scanline(y)[x/PIXELINWORD];
    }

    private void BM_UCLR(int x, int y){
        this.map[y * this.dy + (x / PIXELINWORD)] = this.map[y * this.dy + (x / PIXELINWORD)] & ~bm_mask(x);
    }

    private void BM_USET(int x, int y) {
        this.map[y * this.dy + (x / PIXELINWORD)] = this.map[y * this.dy + (x / PIXELINWORD)] | bm_mask(x);
    }

    private void BM_UPUT(int x, int y, boolean b) {
        if (b)
            BM_USET(x, y);
        else
            BM_UCLR(x, y);
    }

    public void BM_PUT(int x, int y, boolean b) {
        if (bm_safe( x, y))
            BM_UPUT(x, y, b);
    }

    private int getsize(int dy, int h) {
        int size;

        if (dy < 0) {
            dy = -dy;
        }

        size = dy * h * potrace_bitmap.PIXELINWORD;

        /* check for overflow error */
        if (size < 0 || (h != 0 && dy != 0 && size / h / dy != potrace_bitmap.PIXELINWORD)) {
            return -1;
        }

        return size;
    }

    public int bm_size() {
        return getsize(this.dy, this.h);
    }

    // Ole:
    /* hab ich neu geschrieben, einfach wegen des unterschieds zwischen Java und C
    Aber im wesentlichen handelt es sich hierbei um einen Constructor */

    public potrace_bitmap(int w, int h) {
        this.w = w;
        this.h = h;
        this.dy = (w - 1) / PIXELINWORD + 1;
        this.map = new long[this.dy * this.h];
    }

    //Entwickler:
    /* clear the given bitmap. Set all bits to c. Assumes a well-formed
    bitmap. */

    //Ole:
    /* Hier musste ich einiges verändern. In der Originalversion sucht er sich die Addresse der Bitmap herraus,
    mit der Methode bm_base und überschreibt dann einfach den gesamten Speicher mit 1en oder nullen.
    Ich gehe einfach die komplette Bitmap und damit jedes einzelne Potrace_Word durch und überschreibe alles mit einsen oder nullen. */

    static potrace_bitmap bm_clear(potrace_bitmap bm, int c) {
        for (int y = 0; y < bm.h; y ++) {
            for (int dy = 0; dy < bm.dy; dy ++) {
                int clearedValue = (c == 1 ? -1 : 0);
                if (dy == bm.dy -1) {
                    clearedValue =  clearedValue << (PIXELINWORD - (bm.w % PIXELINWORD));
                }
                bm.map[bm.dy * y + dy] = clearedValue;
            }
        }
        return bm;
    }

    //Ole:
    /* Hier musste ich die implementierung ändern, da C sich noch um neuen Speicher kümmern muss.
    Zum Schluss funktioniert es aber gleich.
     */

    //Entwickler:
    /* duplicate the given bitmap. Return NULL on error with errno set. Assumes a well-formed bitmap. */

    public potrace_bitmap bm_dup() {
        potrace_bitmap bm1 = new potrace_bitmap(this.w, this.h);

        if (this == null)
            return null;

        for (int y=0; y < this.h; y++) {
            for (int dy = 0; dy < this.dy; dy ++) {
                bm1.map[y * this.dy + dy] = this.map[y * this.dy + dy];
            }
        }
        return bm1;
    }


    public void bm_clearexcess() {
        long mask;
        int y;

        if (this.w % potrace_bitmap.PIXELINWORD != 0) {
            mask = potrace_bitmap.BM_ALLBITS << (potrace_bitmap.PIXELINWORD - (this.w % potrace_bitmap.PIXELINWORD));
            for (y=0; y<this.h; y++) {
                this.map[y * this.dy + this.dy - 1] = this.bm_index(this.w, y) & mask;
            }
        }
    }
}

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

    public static int PIXELINWORD = 32;
    static int BM_ALLBITS = (~0);
    static int BM_HIBIT = 1 << PIXELINWORD -1;

    public int w, h;              /* width and height, in pixels */
    public int dy;                /* words per scanline (not bytes) */
    public int[] map;             /* raw data, dy*h words */ //TODO changed representation of potrace words


    //Ole:
    /* Ich habe nur die Macros kopiert und in normale Methoden verwandelt die wirklich gebraucht wurden.
    Es gibt ungefähr die doppelte Anzahl an Macros. */

    //Entwickler:
    /* macros for accessing pixel at index (x,y). U* macros omit the bounds check. */

    static boolean bm_range(int x, int a) {
        return (x) >= 0 && (x) < (a);
    }

    static boolean bm_range(double x, double a) {
        return bm_range((int)x,(int)a);
    }

    static boolean bm_safe(potrace_bitmap bm, int x, int y) {
        return bm_range(x, bm.w) && bm_range(y, bm.h);
    }

    static int bm_mask(int x) {
        return ((1) << (PIXELINWORD-1-(x)));
    }

    static boolean BM_UGET(potrace_bitmap bm, int x, int y) {
        return(bm_index(bm, x, y) & bm_mask(x)) != 0;
    }

    static boolean BM_GET(potrace_bitmap bm, int x, int y) {
        return bm_safe(bm, x, y) ? BM_UGET(bm, x, y) : false;
    }

    static int[] bm_scanline(potrace_bitmap bm, int y) {
        int[] scanLine = new int[bm.dy];
        for (int i = 0; i < bm.dy ; i ++) {
            scanLine[i] = bm.map[(y * bm.dy) + i];
        }
        return scanLine;
    }

    static int bm_index(potrace_bitmap bm, int x, int y) {
        return bm_scanline(bm,y)[x/PIXELINWORD];
    }

    static int getsize(int dy, int h) {
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

    static int bm_size(potrace_bitmap bm) {
        return getsize(bm.dy, bm.h);
    }

    // Ole:
    /* hab ich neu geschrieben, einfach wegen des unterschieds zwischen Java und C
    Aber im wesentlichen handelt es sich hierbei um einen Constructor */

    public potrace_bitmap(int w, int h) {
        this.w = w;
        this.h = h;
        this.dy = (w - 1) / PIXELINWORD + 1;
        this.map = new int[this.dy * this.h];
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

    static potrace_bitmap bm_dup(potrace_bitmap bm) {
        potrace_bitmap bm1 = new potrace_bitmap(bm.w, bm.h);

        if (bm == null)
            return null;

        for (int y=0; y < bm.h; y++) {
            for (int dy = 0; dy < bm.dy; dy ++) {
                bm1.map[y * bm.dy + dy] = bm.map[y * bm.dy + dy];
            }
        }
        return bm1;
    }
}

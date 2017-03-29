package potrace;

/**
 * Created by andreydelany on 07/03/2017.
 */

    //Ole:
    /* Ursprünglich wurde die bbox Typdefinition in der Klasse decompose vorgenommen,
    ich habe es allerdings als eigene Klasse angesehen, und sie daher ausgelagert.
    Ein Folgeproblem was demzufolge auftritt, ist das die zugehörigen Funktionen,
    die den State der Klasse / Object verändern,
    eigentlich in dieser Klasse seien sollte. Aber da ich so wenige wie möglichst am originalen Code verändern wollte,
    habe ich sie bis jetzt noch dort belassen. Ich glaube aber, dass es auf jeden Fall notwendig ist, sie in diese Klasse mit zunehmen.*/

    //TODO: Methoden, die den State bbox verändern, in diese Klasse ziehen

public class bbox {

    int x0, x1, y0, y1;

}

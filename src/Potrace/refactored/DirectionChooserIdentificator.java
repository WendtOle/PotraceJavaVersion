package Potrace.refactored;

/**
 * Created by andreydelany on 13.07.17.
 */
public class DirectionChooserIdentificator {
    TurnPolicyEnum turnPolicy;
    PathKindEnum kindOfPath;

    public DirectionChooserIdentificator(TurnPolicyEnum turnPolicyEnum, PathKindEnum kindOfPath){
        this.turnPolicy = turnPolicyEnum;
        this.kindOfPath = kindOfPath;
    }
}

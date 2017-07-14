package Potrace.refactored;

/**
 * Created by andreydelany on 13.07.17.
 */
public class PathFindingCharacteristics {
    TurnPolicyEnum turnPolicy;
    PathKindEnum kindOfPath;

    public PathFindingCharacteristics(TurnPolicyEnum turnPolicyEnum, PathKindEnum kindOfPath){
        this.turnPolicy = turnPolicyEnum;
        this.kindOfPath = kindOfPath;
    }
}

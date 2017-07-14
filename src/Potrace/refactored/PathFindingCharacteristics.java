package Potrace.refactored;

public class PathFindingCharacteristics {
    TurnPolicyEnum turnPolicy;
    PathKindEnum kindOfPath;

    public PathFindingCharacteristics(TurnPolicyEnum turnPolicyEnum, PathKindEnum kindOfPath){
        this.turnPolicy = turnPolicyEnum;
        this.kindOfPath = kindOfPath;
    }
}

package Potrace.refactored;

public enum PathKindEnum {
    POSITIV (43), NEGATIV (45);

    int intRepresentation;

    PathKindEnum(int intRepresentation){
        this.intRepresentation = intRepresentation;
    }

    public int getIntRepresentation(){
        return intRepresentation;
    }
}

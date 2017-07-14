package AdditionalCode.FileInputOutput;

import Potrace.General.Path;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;

/**
 * Created by andreydelany on 14.07.17.
 */
public class PathsListFromJsonReader {

    JSONObject currentPath;
    Path path;
    int amountOfPath;

    PathsListFromJsonReader(int amountOfPaths){
        this.amountOfPath = amountOfPaths;
    }

    Path getPathList(JSONArray pathsAsJsonArray){
        return recoverPath(pathsAsJsonArray,0);
    }

    private Path recoverPath(JSONArray testDataObject, int currentPathIndex) {
        currentPath = (JSONObject) testDataObject.get(currentPathIndex);
        Path path = setPathWithBasicInformation();
        addChildrenAndSiblingDummies(path);
        addLinkToNextPath(testDataObject, currentPathIndex, path);
        return path;
    }

    private Path setPathWithBasicInformation() {
        int area = (int)(long)currentPath.get("area");
        int sign = (int)(long)currentPath.get("sign");
        int length = (int)(long)currentPath.get("length");
        Point[] points = getPoints(currentPath);
        return new Path(area,sign,length,points);
    }

    private Point[] getPoints(JSONObject currentPath) {
        JSONArray pointsJSon = (JSONArray) currentPath.get("pt");
        return recoverPointArray(pointsJSon);
    }

    private Point[] recoverPointArray(JSONArray pointsJSon) {
        Point[] points = new Point[pointsJSon.size()];
        for (int currentPointIndex = 0; currentPointIndex < pointsJSon.size(); currentPointIndex++)
            points[currentPointIndex] = recoverCurrentPoint(pointsJSon, currentPointIndex);
        return points;
    }

    private Point recoverCurrentPoint(JSONArray pointsJSon, int currentPointIndex) {
        JSONObject currentPoint = (JSONObject) pointsJSon.get(currentPointIndex);
        return new Point((int)(long)currentPoint.get("x"),(int)(long)currentPoint.get("y"));
    }

    private void addChildrenAndSiblingDummies(Path path) {
        if (hasCurrentPathAChild())
            path.childlist = new Path();
        if (hasCurrentPathASibling())
            path.sibling = new Path();
    }

    private boolean hasCurrentPathAChild(){
        return (boolean)currentPath.get("hasChild");
    }

    private boolean hasCurrentPathASibling(){
        return (boolean)currentPath.get("hasSibling");
    }

    private void addLinkToNextPath(JSONArray testDataObject, int currentPathIndex, Path path) {
        if(areThereMorePathesInList(currentPathIndex))
            path.next = recoverPath(testDataObject,currentPathIndex + 1);
    }

    private boolean areThereMorePathesInList(int currentPathIndex) {
        return currentPathIndex + 1 < amountOfPath;
    }
}
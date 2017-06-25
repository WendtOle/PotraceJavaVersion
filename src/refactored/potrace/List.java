package refactored.potrace;

public class List {

    public static Path insertElementAtTheEndOfList(Path element, Path list) {
        if (element != null) {
            if (list != null) {
                Path currentElement = list;
                while (currentElement.next != null) {
                    currentElement = currentElement.next;
                }
                element.next = null;
                currentElement.next = element;
                return list;
            } else {
                element.next = null;
                return element;
            }
        }
        return list;
    }

    public static Path insertListAtTheEndOfList(Path list, Path unmodifiedList) {
        if (list != null) {
            if (unmodifiedList != null) {
                Path currentElement = unmodifiedList;
                while (currentElement.next != null) {
                    currentElement = currentElement.next;
                }
                currentElement.next = list;
                return unmodifiedList;
            } else {
                return list;
            }
        }
        return unmodifiedList;
    }
}

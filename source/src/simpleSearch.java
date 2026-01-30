import java.util.ArrayList;

public class simpleSearch {
    public ArrayList<LibraryItem> searchForLibraryItem(String search, ArrayList<LibraryItem> comparisonList){
        ArrayList<LibraryItem> results = new ArrayList<>();
        for (LibraryItem item : comparisonList){
            if (item.matchesSearch(search)){
                results.add(item);
            }
        }
        return results;
    }
}

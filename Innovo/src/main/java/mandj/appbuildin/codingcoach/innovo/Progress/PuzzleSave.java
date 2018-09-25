package mandj.appbuildin.codingcoach.innovo.Progress;

/**
 * Created by joey on 3/19/15.
 */

public class PuzzleSave implements Comparable{

    String name, data, completeness;

    public PuzzleSave(String name, String completeness, String data) {
        this.name = name;
        this.completeness = completeness;
        this.data = data;
    }

    public String toString() {
        return name + "\n" + completeness + "\n" + data;
    }

    public String getName() {

        return name;
    }

    public String getCompleteness() {
        return completeness;
    }

    public String getData() {
        return data;
    }

    @Override
    public int compareTo(Object another) {
        return getName().compareTo(((PuzzleSave)another).getName());
    }
}

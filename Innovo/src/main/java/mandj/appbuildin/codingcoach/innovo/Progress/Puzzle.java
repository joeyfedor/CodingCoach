package mandj.appbuildin.codingcoach.innovo.Progress;

/**
 * Created by joey on 3/16/15.
 */
public abstract class Puzzle implements Comparable{

    String puzzletype = "";
    String puzzlename = "";

    // 0 = , 1 = , 2 =
    int isComplete = 0;

    public Puzzle(String name, String type, int complete){
        this.puzzlename = name;
        this.puzzletype = type;
        this.isComplete = complete;
    }

    public String getPuzzleName(){
        return puzzlename;
    }

    public String getPuzzletype(){
        return puzzletype;
    }

    public int getCompletness(){
        return isComplete;
    }

    public String toString() {
        return puzzlename + ": " + puzzletype + ", " + isComplete;
    }

    public String getHtmlName() {
        if (getCompletness() == 0)
            return "<font color=#dcd603>" + getPuzzleName() + "</font><br><br>";
        else if (getCompletness() == 1)
            return "<font color=#5bdc00>" + getPuzzleName() + "</font><br><br>";
        else
            return "<font color=#dc4229>" + getPuzzleName() + "</font><br><br>";
    }

    @Override
    public int compareTo(Object another) {
        return getPuzzleName().compareTo(((Puzzle)another).getPuzzleName());
    }
}

package mandj.appbuildin.codingcoach.innovo.Study.TabbedListView;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mandj.appbuildin.codingcoach.innovo.MainActivity;
import mandj.appbuildin.codingcoach.innovo.R;

public class ListContent {

    //This is what lets listViews have two lines of text in them. It's sort of like a
    //2D array, but way more cray.

    private final static Pattern WB_PATTERN = Pattern.compile("(?<=\\w)\\b");
    //For puzzle tabs:
    public static List<Map<String, String>> StringList = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> LoopsList = new ArrayList<Map<String, String>>();
    //For learn spinner (change variable names soon):
    public static List<Map<String, String>> SectionOneList = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> SectionTwoList = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> SectionThreeList = new ArrayList<Map<String, String>>();
    public static int[] NavIcons = new int[7];
    public static String[] NavTitles = new String[7];
    public static String[] NavDescripts = new String[7];
    //Number of items in puzzle tabs lists - increment as needed
    static int numLogic = 10;
    //For puzzle tabs:
    public static int[] LogicDifficulty = new int[numLogic];
    public static String[] LogicTitles = new String[numLogic];
    public static String[] LogicDescripts = new String[numLogic];
    public static int[] LogicDone = new int[numLogic];
    static int numLoops = 10;
    public static int[] LoopsDifficulty = new int[numLoops];
    public static String[] LoopsTitles = new String[numLoops];
    public static String[] LoopsDescripts = new String[numLoops];
    public static int[] LoopsDone = new int[numLoops];
    static int numStrings = 10;
    public static int[] StringsDifficulty = new int[numStrings];
    public static String[] StringsTitles = new String[numStrings];
    public static String[] StringsDescripts = new String[numStrings];
    public static int[] StringsDone = new int[numStrings];
    static MainActivity mainActivity = new MainActivity();
    static ArrayList<String> correctpuzzles;

    //constructor
    public ListContent() {
    }

    //Used by lists with 2 textviews (used by Learn lists)
    private static Map<String, String> addWords(String one, String two) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("First Line", one);
        map.put("Second Line", two);
        return map;
    }

    private static void addLogic(int position, String title, String description, int difficulty, int done, Context context) {
        LogicTitles[position] = title;
        LogicDescripts[position] = description;
        LogicDifficulty[position] = difficulty;
        LogicDone[position] = decideDone(context, done, description);
    }

    //add puzzles to Logic listview here!
    public static void setupLogic(Context context) {
        String[] prompt = context.getResources().getStringArray(R.array.question_logic);
        String[] names = context.getResources().getStringArray(R.array.method_header_logic);
        for (int i = 0; i < names.length; i++){
            addLogic(i, names[i].substring(nthsearch(names[i],' ', 2),names[i].lastIndexOf("(")),
                    "Logic #" + (i+1) + ": " + truncateAfterWords(15, prompt[i]) + "...", R.drawable.levelone,0,context);
        }
        for (int i = names.length; i < numLogic; i++)
            addLogic(i, "Locked", "Logic #" + Integer.toString(i + 1), R.drawable.leveltwo, 2, context);
    }

    private static void addStrings(int position, String title, String description, int difficulty, int done, Context context) {
        StringsTitles[position] = title;
        StringsDescripts[position] = description;
        StringsDifficulty[position] = difficulty;
        StringsDone[position] = decideDone(context, done, description);
    }

    public static void setupStrings(Context context) {
        String[] prompt = context.getResources().getStringArray(R.array.question_strings);
        String[] names = context.getResources().getStringArray(R.array.method_header_strings);
        for (int i = 0; i < names.length; i++){
            addStrings(i, names[i].substring(nthsearch(names[i], ' ', 2), names[i].lastIndexOf("(")),
                    "Strings #" + (i + 1) + ": " + truncateAfterWords(15, prompt[i]) + "...", R.drawable.levelone, 0, context);
        }
        for (int i = names.length; i < numStrings; i++)
            addStrings(i, "Locked", "Strings #" + Integer.toString(i + 1), R.drawable.leveltwo, 2, context);
    }

    public static void addLoops(int position, String title, String description, int difficulty, int done, Context context) {
        LoopsTitles[position] = title;
        LoopsDescripts[position] = description;
        LoopsDifficulty[position] = difficulty;
        LoopsDone[position] = decideDone(context, done, description);
    }

    public static void setupLoops(Context context) {
        String[] prompt = context.getResources().getStringArray(R.array.question_loops);
        String[] names = context.getResources().getStringArray(R.array.method_header_loops);
        for (int i = 0; i < names.length; i++){
            addLoops(i, names[i].substring(nthsearch(names[i],' ', 2),names[i].lastIndexOf("(")),
                    "Loops #" + (i+1) + ": " + truncateAfterWords(15, prompt[i]) + "...", R.drawable.levelone,0,context);
        }
        for (int i = names.length; i < numLoops; i++)
            addLoops(i, "Locked", "Loops #" + Integer.toString(i + 1), R.drawable.leveltwo, 2, context);
    }

    public static void addNav(int position, String title, String description, int icon) {
        NavTitles[position] = title;
        NavDescripts[position] = description;
        NavIcons[position] = icon;
    }

    public static void setupNavList() {
        addNav(0, "Home", "nottitle", R.drawable.home);
        addNav(1, "Reference", "nottitle", R.drawable.ref);
        addNav(2, "Save Game", "nottitle", R.drawable.save);
        addNav(3, "Help", "nottitle", R.drawable.help);
        addNav(4, "About", "nottitle", R.drawable.about);
        addNav(5, "Feedback", "nottitle", R.drawable.feedback);
        addNav(6, "Settings", "nottitle", R.drawable.settings);
    }

    //add puzzles to Logic listview here!
    public static void setupLearn() {
        addLearn(0, "addTwo", "Logic #1");
        addLearn(1, "lessThanTen", "Logic #2");
        addLearn(2, "twoOrThree", "Logic #3");
        addLearn(3, "divideByThree", "Logic #4");
        for (int i = 4; i < numLogic; i++)
            addLearn(i, "Locked", "Logic #" + Integer.toString(i + 1));
    }

    private static void addLearn(int position, String title, String description) {
        LogicTitles[position] = title;
        LogicDescripts[position] = description;
    }

    private static String truncateAfterWords(int n, String s) {
        if (s == null) return null;
        if (n <= 0) return "";
        Matcher m = WB_PATTERN.matcher(s);
        for (int i = 0; i < n && m.find(); i++) ;
        if (m.hitEnd())
            return s;
        else
            return s.substring(0, m.end());
    }

    private static int decideDone(Context context, int done, String description) {
        Log.d("All puzzles", mainActivity.getNamesCorrect(context).toString());
        correctpuzzles = mainActivity.getNamesCorrect(context);


        for (String puzzle : correctpuzzles) {
            if (description.contains(puzzle + ":")) {
                done = 1;
                break;
            }
        }
        return done;
    }

    public static int nthsearch(String str, char ch, int n) {
        int pos = 0;
        if (n != 0) {
            for (int i = 1; i <= n; i++) {
                pos = str.indexOf(ch, pos) + 1;
            }
            return pos;
        } else {
            return 0;
        }
    }

    public List<Map<String, String>> getStringList() {
        if (StringList.isEmpty()) {
            StringList.add(addWords("timesTwo", "Strings #1"));
            StringList.add(addWords("flipFlop", "Strings #2"));
            StringList.add(addWords("addSpaces", "Strings #3"));
            StringList.add(addWords("getLast", "Strings #4"));
            StringList.add(addWords("getFirst", "Strings #5"));
            StringList.add(addWords("toPalindrome", "Strings #6"));
            for (int i = 7; i <= 10; i++)
                StringList.add(addWords("Locked", "Strings #" + Integer.toString(i)));
        }
        return StringList;

    }

    public List<Map<String, String>> getLoopsList() {
        if (LoopsList.isEmpty()) {
            LoopsList.add(addWords("numberOne", "Loops #1"));
            LoopsList.add(addWords("allZeros", "Loops #2"));
            LoopsList.add(addWords("findLargest", "Loops #3"));
            LoopsList.add(addWords("halfSum", "Loops #4"));
            LoopsList.add(addWords("findSmallest", "Loops #5"));
            LoopsList.add(addWords("inReverse", "Loops #6"));
            LoopsList.add(addWords("moreEvens", "Loops #7"));
            for (int i = 8; i <= 10; i++)
                LoopsList.add(addWords("Locked", "Loops #" + Integer.toString(i)));
        }
        return LoopsList;
    }

    //FOR LEARN LISTS - USES MAPS
    public List<Map<String, String>> getSectionOneList(Context context) {
        if (SectionOneList.isEmpty()) {

            String[] topics = context.getResources().getStringArray(R.array.topics);
            String[] descpors = context.getResources().getStringArray(R.array.Descriptors);

            for (int i = 0; i < topics.length; i++) {
                //adds a custom box to the list
                SectionOneList.add(addWords(topics[i], descpors[i] + "\n"));
            }

            for (int i = 1 + SectionOneList.size(); i <= 10; i++) {
                String temp = "Locked";
                SectionOneList.add(addWords(Integer.toString(i) + ". " + temp, "(Section One)"));
            }
        }
        return SectionOneList;
    }

    public List<Map<String, String>> getSectionTwoList() {
        if (SectionTwoList.isEmpty()) {
            for (int i = 1; i <= 10; i++) {
                String temp = "Locked";
                SectionTwoList.add(addWords(Integer.toString(i) + ". " + temp, "(Section Two)"));
            }
        }
        return SectionTwoList;
    }

    public List<Map<String, String>> getSectionThreeList() {
        if (SectionThreeList.isEmpty()) {
            for (int i = 1; i <= 10; i++) {
                String temp = "Locked";
                SectionThreeList.add(addWords(Integer.toString(i) + ". " + temp, "(Section Three)"));
            }
        }
        return SectionThreeList;
    }

    public String[] getLogicTitles() {
        return LogicTitles;
    }

    public String[] getLogicDescripts() {
        return LogicDescripts;
    }

    public int[] getLogicDifficulty() {
        return LogicDifficulty;
    }

    public int[] getLogicDone() {
        return LogicDone;
    }

    public String[] getStringsTitles() {
        return StringsTitles;
    }

    public String[] getStringsDescripts() {
        return StringsDescripts;
    }

    public int[] getStringsDifficulty() {
        return StringsDifficulty;
    }

    public int[] getStringsDone() {
        return StringsDone;
    }

    public String[] getNavTitles() {
        return NavTitles;
    }

    public String[] getNavDescripts() {
        return NavDescripts;
    }

    public int[] getNavIcons() {
        return NavIcons;
    }

    public String[] getLoopsTitles() {
        return LoopsTitles;
    }

    public String[] getLoopsDescripts() {
        return LoopsDescripts;
    }

    public int[] getLoopsDifficulty() {
        return LoopsDifficulty;
    }

    public int[] getLoopsDone() {
        return LoopsDone;
    }
}




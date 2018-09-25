package mandj.appbuildin.codingcoach.innovo.Study.Runner;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import bsh.EvalError;
import bsh.Interpreter;
import mandj.appbuildin.codingcoach.innovo.R;


/**
 * Created by joey on 2/18/15.
 */

public class SetPuzzle extends PuzzleRunner {

    Context context;
    String question, method, answer;
    int puzzle, puzzleType;
    boolean newPuzzle;
    List<Integer> integers;
    List<String> strings;
    List<Character> chars;
    List<Double> doubles;
    ArrayList<String[]> stringArrs = new ArrayList<>();
    ArrayList<int[]> intArrs = new ArrayList<>();


    public SetPuzzle(Resources resources, Intent intent) {

        this.newPuzzle = intent.getBooleanExtra("newPuzzle?", false);

        if (intent.getIntArrayExtra("array") != null) {
            this.puzzle = intent.getIntArrayExtra("array")[0];
            this.puzzleType = intent.getIntArrayExtra("array")[1];
        }


        if (puzzleType == 1) {
            this.question = resources.getStringArray(R.array.question_logic)[puzzle];
            this.method = resources.getStringArray(R.array.method_header_logic)[puzzle];
            this.answer = resources.getStringArray(R.array.correct_answer_logic)[puzzle];
        } else if (puzzleType == 2) {
            this.question = resources.getStringArray(R.array.question_strings)[puzzle];
            this.method = resources.getStringArray(R.array.method_header_strings)[puzzle];
            this.answer = resources.getStringArray(R.array.correct_answer_strings)[puzzle];
        } else if (puzzleType == 3) {
            this.question = resources.getStringArray(R.array.question_loops)[puzzle];
            this.method = resources.getStringArray(R.array.method_header_loops)[puzzle];
            this.answer = resources.getStringArray(R.array.correct_answer_loops)[puzzle];
        }
    }

    public SetPuzzle(int puz, int puzType, Resources resources) {

        this.puzzle = puz;
        this.puzzleType = puzType;

        if (puzzleType == 1) {
            setTitle("Logic #" + Integer.toString(puzzle + 1));
            this.question = resources.getStringArray(R.array.question_logic)[puzzle];
            this.method = resources.getStringArray(R.array.method_header_logic)[puzzle];
            this.answer = resources.getStringArray(R.array.correct_answer_logic)[puzzle];
        } else if (puzzleType == 2) {
            setTitle("Strings #" + Integer.toString(puzzle + 1));
            this.question = resources.getStringArray(R.array.question_strings)[puzzle];
            this.method = resources.getStringArray(R.array.method_header_strings)[puzzle];
            this.answer = resources.getStringArray(R.array.correct_answer_strings)[puzzle];
        } else if (puzzleType == 3) {
            setTitle("Loops #" + Integer.toString(puzzle + 1));
            this.question = resources.getStringArray(R.array.question_loops)[puzzle];
            this.method = resources.getStringArray(R.array.method_header_loops)[puzzle];
            this.answer = resources.getStringArray(R.array.correct_answer_loops)[puzzle];
        }
    }

    public static List<Integer> getRandomNumbers(int range, int amount) {
        List<Integer> randomNums = new ArrayList<>();
        for (double i = 0; i <= range; i++) {
            randomNums.add((int) i);
        }
        Collections.shuffle(randomNums);
        return randomNums.subList(0, amount);
    }

    public static String[] getRandomWords(String[] list, int amount) {
        List<String> arrayList = Arrays.asList(list);
        Collections.shuffle(arrayList);
        return arrayList.toArray(new String[amount]);
    }

    public static ArrayList<Character> getRandomLetters() {
        ArrayList<Character> abc = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 26; i++) {
            if (random.nextDouble() > .5)
                abc.add((char) ('A' + i));
            else
                abc.add((char) ('a' + i));
        }
        Collections.shuffle(abc);
        return abc;
    }

    //Do not use with large ranges.
    public static List<Double> getRandomDoubles(int range, int amount) {
        List<Double> randomNums = new ArrayList<>();
        Random randomGenerator = new Random();
        for (int i = 0; i <= range; i++) {
            randomNums.add((double) i + (double) randomGenerator.nextInt(9) / 10);
        }
        Collections.shuffle(randomNums);
        return randomNums.subList(0, amount);
    }

    public String getMethod() {
        return method;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getPuzzle() {
        return puzzle;
    }

    public int getPuzzleType() {
        return puzzleType;
    }

    public boolean getnewPuzzle() {
        return newPuzzle;
    }

    //returns the parameters in a data-type, variable name,... form
    public String[] findParameters(String methodHeader) {
        int firstBracket = methodHeader.indexOf("(") + 1;
        int lastBracket = methodHeader.lastIndexOf(")");

        methodHeader = methodHeader.substring(firstBracket, lastBracket);
        methodHeader = methodHeader.replace(",", "");

        return methodHeader.split(" ");
    }

    public String setParameters(Interpreter i, String[] input) throws EvalError {
        ArrayList<Object> out = new ArrayList<>();

        for (int count = 0; count < input.length; count = count + 2) {
            switch (input[count]) {
                case "int":
                    i.set(input[count + 1], integers.get(count).intValue());
                    out.add(String.valueOf(integers.get(count)));
                    Log.d("Setting", String.valueOf(input[count + 1]) + " as " + integers.get(count));
                    break;
                case "String":
                    i.set(input[count + 1], strings.get(count));
                    out.add(String.valueOf(strings.get(count)));
                    Log.d("Setting", String.valueOf(input[count + 1]) + " as " + strings.get(count));
                    break;
                case "char":
                    i.set(input[count + 1], chars.get(count).charValue());
                    out.add(String.valueOf(chars.get(count)));
                    Log.d("Setting", String.valueOf(input[count + 1]) + " as " + chars.get(count));
                    break;
                case "double":
                    i.set(input[count + 1], doubles.get(count).doubleValue());
                    out.add(String.valueOf(doubles.get(count)));
                    Log.d("Setting", String.valueOf(input[count + 1]) + " as " + doubles.get(count));
                    break;
                case "String[]":
                    i.set(input[count + 1], (stringArrs.get(count)));
                    out.add(Arrays.toString(stringArrs.get(count)));
                    Log.d("Setting", String.valueOf(input[count + 1]) + " as " + Arrays.toString(stringArrs.get(count)));
                    break;
                case "int[]":
                    i.set(input[count + 1], (intArrs.get(count)));
                    out.add(Arrays.toString(intArrs.get(count)));
                    Log.d("Setting", String.valueOf(input[count + 1]) + " as " + Arrays.toString(intArrs.get(count)));
                    break;
            }
        }
        return Arrays.toString(out.toArray());
    }

    public void setPreviousParameters(Interpreter i, String[] input) throws EvalError {
        setParameters(i, input);
        try {
            for (int count = 0; count < input.length; count = count + 2) {
                switch (input[count]) {
                    case "int":
                        integers.remove(count);
                        break;
                    case "String":
                        strings.remove(count);
                        break;
                    case "char":
                        chars.remove(count);
                        break;
                    case "double":
                        doubles.remove(count);
                        break;
                    case "String[]":
                        stringArrs.remove(count);
                        break;
                    case "int[]":
                        intArrs.remove(count);
                        break;
                }
            }
        } catch (IndexOutOfBoundsException IOB) {
            generateArrays(context);
        }
    }

    public void generateArrays(Context context) {
        this.context = context;
        integers = getRandomNumbers(25, 25);
        strings = new LinkedList<>(Arrays.asList((getRandomWords(context.getResources().getStringArray(R.array.testString), 25))));
        chars = getRandomLetters();
        doubles = getRandomDoubles(25, 25);
        for (int i = 0; i < 50; i++)
            stringArrs.add(Arrays.copyOf(getRandomWords(context.getResources().getStringArray(R.array.testString), 1), 4));
        for (int i = 0; i < 25; i++) {
            int[] ints = new int[]{(int) (Math.random() * 25), (int) (Math.random() * 25), (int) (Math.random() * 25), (int) (Math.random() * 25)};
            intArrs.add(ints);
        }
    }

    public int getSmallestNum() {
        return Math.min(integers.size(), Math.min(strings.size(),
                Math.min(chars.size(), Math.min(doubles.size(),
                        Math.min(stringArrs.size(), intArrs.size())))));
    }

}

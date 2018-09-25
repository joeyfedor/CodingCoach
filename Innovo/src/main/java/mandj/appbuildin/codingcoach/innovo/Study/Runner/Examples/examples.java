package mandj.appbuildin.codingcoach.innovo.Study.Runner.Examples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import bsh.EvalError;
import bsh.Interpreter;
import mandj.appbuildin.codingcoach.innovo.R;

public class examples extends AppCompatActivity {

    Toolbar mToolbar;
    String method, answer;
    Random generator = new Random();
    Object correctAnswer;
    String[] testStrings = new String[]{"coding", "coach", "example", "hello"};
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static String[] getRandomWords(String[] list, int amount) {
        List<String> arrayList = Arrays.asList(list);
        Collections.shuffle(arrayList);
        return arrayList.toArray(new String[amount]);
    }

    //Do not use with large ranges.
    public static List<Integer> getRandomNumbers(int range, int amount) {
        List<Integer> randomNums = new ArrayList<>();
        for (int i = 0; i <= range; i++) {
            randomNums.add(i);
        }
        Collections.shuffle(randomNums);
        return randomNums.subList(0, amount);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examples);

        mToolbar = (Toolbar) (findViewById(R.id.toolbar));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        ArrayList<ItemData> data = new ArrayList<ItemData>();

        //This turns into the input for the array list

        method = getIntent().getStringExtra("Method");
        answer = getIntent().getStringExtra("Answer");

        int spaceFinder = method.indexOf(" ", 9);
        String tempMethod = method.substring(spaceFinder);

        getSupportActionBar().setTitle(tempMethod);

        Log.d("Method = ", method);
        Log.d("Awnser = ", answer);

        Interpreter i = new Interpreter();
        if (method.contains("int x")) {
            for (int b = 0; b < 25; b++) {
                try {
                    i.set("x", b);
                    correctAnswer = i.eval(answer);
                } catch (EvalError evalError) {
                    evalError.printStackTrace();
                }
                if (correctAnswer instanceof int[]) {
                    int[] arra = (int[]) correctAnswer;
                    correctAnswer = Arrays.toString(arra);
                }

                String puzzlename = tempMethod.substring(0,tempMethod.indexOf("(")+1);

                data.add(new ItemData("", "",
                        puzzlename + b + ") would return " + correctAnswer
                ));


            }

        } else if (method.contains("String str")) {
            for (int b = 0; b < 4; b++) {
                try {
                    i.set("str", testStrings[b]);
                    correctAnswer = i.eval(answer);

                    data.add(new ItemData(
                            "Input: " + testStrings[b],
                            "Output: " + correctAnswer,
                            "For example, puzzlename(" + testStrings[b] + ") would return " + correctAnswer
                    ));

                } catch (EvalError evalError) {
                    evalError.printStackTrace();
                }
            }


        } else if (method.contains("int[] nums")) {
            int numInArray = 4; //changeable
            for (int b = 0; b < 4; b++) {
                int[] randomArray = new int[numInArray];
                for (int c = 0; c < numInArray; c++)
                    randomArray[c] = generator.nextInt(25);
                try {
                    i.set("nums", randomArray);
                    correctAnswer = i.eval(answer);
                } catch (EvalError evalError) {
                    evalError.printStackTrace();
                }

                if (correctAnswer instanceof int[]) {
                    int[] arra = (int[]) correctAnswer;
                    correctAnswer = Arrays.toString(arra);
                }

                String arrayVals = randomArray[0] + "," +
                        randomArray[1] +
                        "," + randomArray[2] + "," + randomArray
                        [3];
                data.add(new ItemData(
                        "",
                        "",
                        "For example, puzzlename(" + arrayVals +
                                ") would return " + correctAnswer
                ));
            }
        } else if (method.contains("String[] strs")) {
            int stringsInExample = 10; //changeable
            for (int c = 0; c < stringsInExample; c++) {
                int randomSize = getRandomNumbers(10, 1).get(0);
                String[] randomArray = Arrays.copyOfRange(getRandomWords(getResources().getStringArray(R.array.testString), stringsInExample), 0, randomSize);
                try {
                    i.set("c", randomArray);
                    correctAnswer = i.eval(answer);
                } catch (EvalError evalError) {
                    evalError.printStackTrace();
                }
                data.add(new ItemData(
                        "",
                        "",
                        "For example, puzzlename(" + Arrays.toString(randomArray) +
                                ") would return " + correctAnswer
                ));
            }
        } else if (method.contains("double x")) {
            List<Double> doubles = getRandomDoubles(50, 25);
            for (int b = 0; b < 25; b++) {
                try {
                    i.set("x", doubles.get(b));
                    correctAnswer = i.eval(answer);
                } catch (EvalError evalError) {
                    evalError.printStackTrace();
                }
                if (correctAnswer instanceof int[]) {
                    int[] arra = (int[]) correctAnswer;
                    correctAnswer = Arrays.toString(arra);
                }

                String puzzlename = tempMethod.substring(0, tempMethod.indexOf("(") + 1);

                data.add(new ItemData("", "",
                        puzzlename + doubles.get(b) + ") would return " + correctAnswer
                ));


            }
        } else if (method.contains("char c")) {
            List<Character> characters = getRandomLetters();
            for (int b = 0; b < 25; b++) {
                try {
                    i.set("c", characters.get(b));
                    correctAnswer = i.eval(answer);

                    data.add(new ItemData(
                            "",
                            "",
                            "For example, puzzlename(" + characters.get(b) + ") would return " + correctAnswer
                    ));

                } catch (EvalError evalError) {
                    evalError.printStackTrace();
                }
            }

        } else
            Log.d("tag ", "Does not exist");

        mAdapter = new MyAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_examples, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package mandj.appbuildin.codingcoach.innovo.Study.Runner;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

import bsh.EvalError;
import bsh.Interpreter;
import mandj.appbuildin.codingcoach.innovo.Feedback;
import mandj.appbuildin.codingcoach.innovo.MainActivity;
import mandj.appbuildin.codingcoach.innovo.R;
import mandj.appbuildin.codingcoach.innovo.Settings;
import mandj.appbuildin.codingcoach.innovo.Study.Runner.Examples.examples;
import mandj.appbuildin.codingcoach.innovo.Study.Runner.Help.HelpSwipe;
import mandj.appbuildin.codingcoach.innovo.Study.TabbedListView.PuzzleTabs;
import mandj.appbuildin.codingcoach.innovo.Vibrate;


public class PuzzleRunner extends AppCompatActivity implements View.OnClickListener{

    String input, constructorType, result;
    boolean open, hasBundle = false;
    Button button, shareButton;
    Boolean useServer;
    Interpreter i;
    ImageView difficulty;
    Random generator = new Random();
    boolean[] bolArr;
    String[] outputs = new String[25];
    Object[] userAnswers = new Object[25], correctAnswers = new Object[25];
    CirclePageIndicator mIndicator;

    // Variables for the DoubleTapMethod
    int tapCount = 0;
    long waitTimeTap = 0;
    SetPuzzle setPuzzle;
    LayoutInflater inflater;
    View dialogue;
    Dialog popUp;

    //Used for the pager
    private WrapContentHeightViewPager pager = null;
    private DemoCollectionPagerAdapter pagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_puzzle_runner);

        //Initialize the Visual Elements
        EditText tempEdit = (EditText) findViewById(R.id.editText);
        View submitButton = findViewById(R.id.button);
        TextView methodView = (TextView) findViewById(R.id.methodView);
        TextView questionView = (TextView) findViewById(R.id.questionView);
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        final Context context = this;
        SharedPreferences preferences;

        //setup actionbar
        Toolbar mToolbar = (Toolbar) (findViewById(R.id.toolbar));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //sets up the pager
        ////////////////////////////////////////////////////////////////////////////////////////////

        pagerAdapter = new DemoCollectionPagerAdapter();

        pager = (WrapContentHeightViewPager) findViewById(R.id.swipeResults);
        pager.setAdapter(pagerAdapter);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);

        pager.addOnPageChangeListener(new PageIndicator() {
            @Override
            public void setViewPager(ViewPager view) {

            }

            @Override
            public void setViewPager(ViewPager view, int initialPosition) {

            }

            @Override
            public void setCurrentItem(int item) {

            }

            @Override
            public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {

            }

            @Override
            public void notifyDataSetChanged() {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setOnTouchListener(new View.OnTouchListener() {

            int dragthreshold = 30;
            int downX;
            int downY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getRawX();
                        downY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int distanceX = Math.abs((int) event.getRawX() - downX);
                        int distanceY = Math.abs((int) event.getRawY() - downY);

                        if (distanceY > distanceX && distanceY > dragthreshold) {
                            pager.getParent().requestDisallowInterceptTouchEvent(false);
                            scrollView.getParent().requestDisallowInterceptTouchEvent(true);
                        } else if (distanceX > distanceY && distanceX > dragthreshold) {
                            pager.getParent().requestDisallowInterceptTouchEvent(true);
                            scrollView.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        scrollView.getParent().requestDisallowInterceptTouchEvent(false);
                        pager.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //sets up the puzzle data Including the question,method and Answer.
        ////////////////////////////////////////////////////////////////////////////////////////////

        setPuzzle = new SetPuzzle(getResources(), getIntent());
        if (setPuzzle.getnewPuzzle())
            savePuzzle(setPuzzle.getPuzzle(), setPuzzle.getPuzzleType());
        else
            setPuzzle = new SetPuzzle(lastPuzzlePlayed()[0], lastPuzzlePlayed()[1], getResources());

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Sets the Actionbar Title after reading the puzzle.
        ////////////////////////////////////////////////////////////////////////////////////////////

        if (setPuzzle.getPuzzleType() == 1)
            setTitle("Logic #" + Integer.toString(setPuzzle.getPuzzle() + 1));
        else if (setPuzzle.getPuzzleType() == 2)
            setTitle("Strings #" + Integer.toString(setPuzzle.getPuzzle() + 1));
        else if (setPuzzle.getPuzzleType() == 3)
            setTitle("Loops #" + Integer.toString(setPuzzle.getPuzzle() + 1));

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Looks into the Preferences and changes the layout accordingly
        ////////////////////////////////////////////////////////////////////////////////////////////

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.getBoolean("lined_checkbox", true))
            tempEdit.setBackgroundResource(0);

        useServer = preferences.getBoolean("useServer", true);

        if (preferences.getBoolean("debugging", false)) {
            TextView txtView = (TextView) findViewById(R.id.displayPuzzles);
            txtView.setVisibility(View.VISIBLE);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Waits for a long press of the submit button
        ////////////////////////////////////////////////////////////////////////////////////////////

        submitButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CharSequence testToastWords = "Puzzle Code Saved!";
                Toast testToast = Toast.makeText(context, testToastWords, Toast.LENGTH_SHORT);
                testToast.show();
                try {
                    saveCodeOnly();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //sets the first word to the selected color and the other ui elements to their values
        ////////////////////////////////////////////////////////////////////////////////////////////

        questionView.setText(Html.fromHtml(StringManipulation.firstWord(setPuzzle.getQuestion() +
                "<font color=#0099cc> Examples </font>", "fcb03c")));
        methodView.setText(Html.fromHtml(StringManipulation.firstWord(setPuzzle.getMethod(), "6fb07f")));

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Looks in the Save and pulls out sets the edit text to the last attempts code.
        ////////////////////////////////////////////////////////////////////////////////////////////

        readPrevInput();

        if (savedInstanceState != null) {

            backgroundTask task = new backgroundTask();

            // Restore value of members from saved state
            userAnswers = (Object[]) savedInstanceState.getStringArray("userAnswers");
            Log.d("userAnswers", Arrays.toString(userAnswers));

            correctAnswers = (Object[]) savedInstanceState.getStringArray("correctAnswers");
            Log.d("correctAnswers", Arrays.toString(correctAnswers));

            outputs = savedInstanceState.getStringArray("outputs");
            Log.d("outputs", Arrays.toString(outputs));

            hasBundle = true;

            task.onPostExecute(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.puzzle_runner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent upIntent = new Intent(this, PuzzleTabs.class);
            upIntent.putExtra("tabInfo", setPuzzle.getPuzzleType() - 1);
            NavUtils.navigateUpTo(this, upIntent);
            return true;
        }
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            intent.putExtra("callingActivity", "PuzzleRunner");
            intent.putExtra("array", new int[]{setPuzzle.getPuzzle(), setPuzzle.getPuzzleType()});
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_help) {
            if (!open) {
                try {
                    open = true;
                    Intent intent = new Intent(this, HelpSwipe.class);
                    CardView cardView = (CardView) findViewById(R.id.card_view);

                    intent.putExtra("prompt", new int[]{cardView.getLeft(), cardView.getTop(),
                            cardView.getRight(), cardView.getBottom()});

                    startActivity(intent);
                    Thread.sleep(200);
                    open = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        if (id == R.id.action_feedback) {
            Intent intent = new Intent(this, Feedback.class);
            intent.putExtra("callingActivity", "PuzzleRunner");
            intent.putExtra("array", new int[]{setPuzzle.getPuzzle(), setPuzzle.getPuzzleType()});
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickRun(View v) throws Exception {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            new backgroundTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
        else
            new backgroundTask().execute((Void[]) null);
        Vibrate.VibrateOne(20, this);

    }

    public boolean[] testCorrect(Object[] userAnswers, Object[] correctAnswers) {

        boolean[] booleans = new boolean[25];

        for (int i = 0; i < 25; i++) {
            booleans[i] = (userAnswers[i].equals(correctAnswers[i]));
        }

        if (!hasBundle) {
            if (Arrays.equals(userAnswers, correctAnswers)) {
                successPopUp();
                try {
                    saveData(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(50);
                    Vibrate.VibrateOne(new int[]{100, 150, 100}, this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Vibrate.VibrateOne(500, this);
                String text = "";

                switch (generator.nextInt(3)) {
                    case 0:
                        text = "That was close, try again!";
                        break;
                    case 1:
                        text = "Good try, try again!";
                        break;
                    case 2:
                        text = "Try again";
                        break;
                    //case 3:
                    //    text = "That was horrible, keep trying :(";
                    //    break;
                }
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        }
        return booleans;
    }


    public void readPrevInput() {
        String fileName = getTitle().toString();
        // 1 = win, 2 = lose, 3 = not done / no info
        int ret = 3;
        String userInput = "";
        String result = "";
        try {
            //tests to see if the file already exists if so tests for a win
            FileInputStream fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader buffreader = new BufferedReader(isr);
            result = buffreader.readLine();
            String readLine = buffreader.readLine();
            while (readLine != null) {
                userInput += readLine + "\n";
                readLine = buffreader.readLine();
            }
            if (userInput.length() != 0)
                userInput = userInput.substring(0, userInput.length() - 1);
            isr.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            FileOutputStream fos;
            try {
                fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                fos.write("3\n".getBytes());
                fos.close();
            } catch (IOException e2) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.equals("1") || result.equals("2")) {
            ret = Integer.parseInt(result);
        }

        Object[] objects = new Object[2];
        objects[0] = ret;
        objects[1] = userInput;

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(objects[1].toString());
    }

    public void saveData(boolean correct) throws IOException {
        String fileName = getTitle().toString();
        // 1 = win, 2 = lose, 3 = not done / no info
        String reader;
        try {
            EditText editText = (EditText) findViewById(R.id.editText);
            //tests to see if the file already exists if so tests for a win
            FileInputStream fis = openFileInput(fileName);
            String input = editText.getText().toString();
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            reader = bufferedReader.readLine();
            Log.d("Reader", reader);

            if (Integer.parseInt(reader) == 1) {
                FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                fos.write(("1\n" + input).getBytes());
                fos.close();
            } else {
                if (correct) {
                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                    fos.write(("1\n" + input).getBytes());
                    fos.close();
                } else {
                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                    fos.write(("2\n" + input).getBytes());
                    fos.close();
                }
            }

            isr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCodeOnly() throws IOException {
        String fileName = getTitle().toString();
        // 1 = win, 2 = lose, 3 = not done / no info
        result = "";
        try {
            //tests to see if the file already exists if so tests for a win
            FileInputStream fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader buffReader = new BufferedReader(isr);
            result = buffReader.readLine();
            isr.close();

            EditText editText = (EditText) findViewById(R.id.editText);
            String input = editText.getText().toString();
            if (Integer.parseInt(result) == 1) {
                FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                fos.write(("1\n" + input).getBytes());
                fos.close();
            } else if (Integer.parseInt(result) == 2) {
                FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                fos.write(("2\n" + input).getBytes());
                fos.close();
            } else {
                FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                fos.write(("3\n" + input).getBytes());
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    public void successPopUp() {

        //gets the name of the user
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("example_text", "");
        MainActivity progress = new MainActivity();


        popUp = new Dialog(this);
        popUp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popUp.setContentView(R.layout.layout_success);

        //Creates the dialog box
        inflater = getLayoutInflater();
        dialogue = inflater.inflate(R.layout.layout_success, null);

        //initializes the dialog boxes variables and sets text views to correct text
        TextView textView = (TextView) dialogue.findViewById(R.id.textView);
        if (name != null)
            textView.setText("Congratulations, \n" + name + "!");
        else
            textView.setText("Congratulations!");

        button = (Button) dialogue.findViewById(R.id.button);
        shareButton = (Button) dialogue.findViewById(R.id.shareButton);
        difficulty = (ImageView) dialogue.findViewById(R.id.imageView);
        popUp.setContentView(dialogue);
        popUp.getWindow().setBackgroundDrawable(new ColorDrawable(80000000));
        popUp.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ((ViewGroup) popUp.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                getApplicationContext(), android.R.anim.fade_in));
        popUp.show();

        //listens for the close button to be pressed
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popUp.cancel();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, setPuzzle.getMethod() + "\n" + input + "}");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share your puzzle code!"));
            }
        });
    }

    public void exampleDoubleClick(View b) {
        if (tapCount >= 1 && waitTimeTap > System.currentTimeMillis()) {
            Vibrate.VibrateOne(20, this);
            Intent intent = new Intent(this, examples.class);
            intent.putExtra("Method", setPuzzle.getMethod());
            intent.putExtra("Answer", setPuzzle.getAnswer());
            startActivity(intent);
        } else {
            tapCount++;
            waitTimeTap = System.currentTimeMillis() + 2500;
            String text = "tap one more time for examples";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }
    }

    public int[] lastPuzzlePlayed() {
        int[] intArr = {1, 1};
        try {
            String read;
            //tests to see if the file already exists if so tests for a win
            FileInputStream fis = openFileInput("LAST_PUZZLE");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedreader = new BufferedReader(isr);
            read = bufferedreader.readLine();
            int intOne = Integer.parseInt(read.substring(0, read.indexOf(" ")));
            int intTwo = Integer.parseInt(read.substring(read.indexOf(" ") + 1));
            intArr = new int[]{intOne, intTwo};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return intArr;
    }

    //todo find good name for the consturctors
    public void savePuzzle(int one, int two) {
        try {

            FileOutputStream fos = openFileOutput("LAST_PUZZLE", Context.MODE_PRIVATE);
            fos.write((String.valueOf(one) + " " + String.valueOf(two)).getBytes());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putStringArray("userAnswers", Arrays.toString(userAnswers)
                .substring(1, Arrays.toString(userAnswers).length() - 1).split(","));

        savedInstanceState.putStringArray("correctAnswers", Arrays.toString(correctAnswers)
                .substring(1, Arrays.toString(correctAnswers).length() - 1).split(","));

        savedInstanceState.putStringArray("outputs", outputs);

        super.onSaveInstanceState(savedInstanceState);

    }


    public class backgroundTask extends AsyncTask<Object, Object, Boolean> {

        TextView smallMessage;
        ProgressBar loading;
        String resultTextOne, resultTextTwo, resultTextThree;
        boolean isCorrect = false;
        Boolean done = false;
        Object userAnswer = "", correctAnswer;
        String[] methods = new String[25];

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading = (ProgressBar) findViewById(R.id.progressBar);

            loading.setVisibility(View.VISIBLE);

            result = null;
            resultTextOne = "";
            resultTextTwo = "";
            resultTextThree = "";

            //initialized visual elements
            EditText userIn = (EditText) findViewById(R.id.editText);
            smallMessage = (TextView) findViewById(R.id.displayPuzzles);


            ////////////////////////////////////////////////////////////////////////////////////////////
            //Sends the input to the server and waits for a response
            ////////////////////////////////////////////////////////////////////////////////////////////

            input = userIn.getText().toString();

            //Fixes issue's that arose when the users used comments ex.(//test) and the if else issue.
            input = StringManipulation.repair(input);

        }

        @Override
        protected Boolean doInBackground(Object... params) {


            userAnswers = new Object[25];
            correctAnswers = new Object[25];

            long waitTime = System.currentTimeMillis() + 3500;
            int action = 0;
            if (!useServer) {
                action = 20;
                result = "Compilation Successful.";
            }

            while (true)
                switch (action) {

                    //sends code to the server and if it connects and compiles correctly it moves on,
                    // otherwise activates the error case

                    case (0):

                        result = Server.send(input, setPuzzle.getMethod(), "192.168.1.7", getApplicationContext());
                        //waits for the servers information
                        while (result == null) {
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (waitTime < System.currentTimeMillis()) {
                                result = "Server timed out, please check your internet connection.";
                                action = 100;
                                break;
                            }
                        }

                        //tests for error
                        if (result.contains("Error"))
                            return true;
                        else
                            action = 20;
                        break;

                    //end case 10

                    case (20):

                        //Decides What Form of Puzzle that it is and sets it up for the SwitchCase.
                        String paramArea = setPuzzle.getMethod().substring(setPuzzle.getMethod().indexOf("("));
                        Log.d("Test", paramArea);


                        if (paramArea.contains("int[] nums") || paramArea.contains("String[]")
                                || paramArea.contains("int") || paramArea.contains("double")
                                || paramArea.contains("char") || paramArea.contains("String"))
                            constructorType = "int";
                        else {
                            return true;
                        }

                        done = false;

                        i = new Interpreter();
                        correctAnswer = 0;
                        setPuzzle.generateArrays(getApplication());
                        String inputs = null;
                        switch (constructorType) {

                            ////////////////////////////////////////////////////////////////////////////
                            //int//
                            ////////////////////////////////////////////////////////////////////////////
                            case "int":
                                for (int b = 0; b < 25; b++) {
                                    Log.d("Smallest num", String.valueOf((setPuzzle.getSmallestNum())));
                                    try {
                                        inputs = setPuzzle.setParameters(i, setPuzzle.findParameters(setPuzzle.getMethod()));
                                        if (inputs != null)
                                            outputs[b] = (inputs);
                                        userAnswer = i.eval(input);
                                        if (userAnswer instanceof int[]) {
                                            Log.d("HIT", "HIT");
                                            int[] arra = (int[]) userAnswer;
                                            userAnswer = Arrays.toString(arra);
                                        }
                                        setPuzzle.setPreviousParameters(i, setPuzzle.findParameters(setPuzzle.getMethod()));
                                        correctAnswer = i.eval(setPuzzle.getAnswer());
                                    } catch (EvalError evalError) {
                                        evalError.printStackTrace();
                                    }

                                    if (correctAnswer instanceof int[]) {
                                        Log.d("HIT", "HIT");
                                        int[] arra = (int[]) correctAnswer;
                                        correctAnswer = Arrays.toString(arra);
                                    }
                                    userAnswers[b] = (userAnswer);
                                    correctAnswers[b] = (correctAnswer);
                                }
                                done = true;
                                break;
                        }

                        ////////////////////////////////////////////////////////////////////////////////////////////
                        //Waits for the code to stop Processing and Sets it to Correctness
                        // to false if the code enters an endless loop
                        ////////////////////////////////////////////////////////////////////////////////////////////

                        waitTime = System.currentTimeMillis() + 2500;
                        while (!done) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (waitTime < System.currentTimeMillis()) {
                                return true;
                            }
                        }

                        try {
                            saveData(isCorrect);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return Boolean.FALSE;
                    //end case 20

                    case (100):
                        return Boolean.TRUE;
                }
        }

        @Override
        protected void onPostExecute(Boolean hasError) {
            super.onPostExecute(hasError);

            loading = (ProgressBar) findViewById(R.id.progressBar);
            loading.setVisibility(View.INVISIBLE);

            if (hasError) {
                Log.d("result", result);
                String resultERR = result.substring(result.indexOf("Error"));
                result = result.substring(0, result.indexOf("Error"));

                if (pagerAdapter.hasView()) {
                    int i = 0;
                    while (i < pagerAdapter.getCount()) {
                        pagerAdapter.removeView(pager, i);
                        pagerAdapter.notifyDataSetChanged();
                    }
                    setOut(resultERR.trim(), "", "There was an error");
                } else {
                    setOut(resultERR.trim(), "", "There was an error");
                }

            } else {

                String method = setPuzzle.getMethod();
                method = method.substring(method.indexOf(" ", 7));
                method = method.substring(0, method.indexOf("(") + 1);


                String startHere = method;
                for (int i = 0; i < 25; i++) {
                    method = startHere + outputs[i]
                            .substring(1, outputs[i].length() - 1) + ")";
                    methods[i] = method;
                }

                bolArr = testCorrect(userAnswers, correctAnswers);
                int f = 0;
                for (int i = 0; i < bolArr.length; i++) {
                    if (!bolArr[i] && (i > 4 || i == 0)) {
                        f = i;
                        break;
                    }
                }

                Log.d("Boolean f", String.valueOf(bolArr[f]));
                Log.d("Boolean f", String.valueOf(f));

                mIndicator.setBoolArr(new boolean[]{bolArr[4], bolArr[3], bolArr[2], bolArr[1], bolArr[f]});

                if (!pagerAdapter.hasView()) {

                    for (int i = 0; i < 5; i++) {
                        if (i == 0) {
                            setOut(userAnswers[f], correctAnswers[f], methods[f]);
                        } else {
                            setOut(userAnswers[i], correctAnswers[i], methods[i]);
                        }
                    }

                } else {

                    int i = 0;
                    for (; i < pagerAdapter.getCount(); i++) {
                        pagerAdapter.removeView(pager, i);
                        pagerAdapter.notifyDataSetChanged();
                        if (i == 0) {
                            setOut(userAnswers[f], correctAnswers[f], methods[f]);
                        } else {
                            setOut(userAnswers[i], correctAnswers[i], methods[i]);
                        }
                    }
                    for (; i < 5; i++) {
                        if (i == 0) {
                            setOut(userAnswers[f], correctAnswers[f], methods[f]);
                        } else {
                            setOut(userAnswers[i], correctAnswers[i], methods[i]);
                        }
                    }
                }
            }
            pagerAdapter.notifyDataSetChanged();

            ////////////////////////////////////////////////////////////////////////////////////////////
            //Makes the Results Visible
            ////////////////////////////////////////////////////////////////////////////////////////////

            smallMessage = (TextView) findViewById(R.id.displayPuzzles);
            smallMessage.setText(result);

            ////////////////////////////////////////////////////////////////////////////////////////////
            //Tests all possible errors and sets the resultView accordingly
            ////////////////////////////////////////////////////////////////////////////////////////////

            Log.d("Has error?", String.valueOf(!(!smallMessage.getText().equals("Timed out") &&
                    !smallMessage.getText().toString().contains("Server timed out")
                    && !hasError && !smallMessage.getText().toString().contains("Compilation Successful."))));
            Log.d("Server time out", String.valueOf(smallMessage.getText().toString().contains("Server timed out")));
            Log.d("Comp Successful", String.valueOf(!smallMessage.getText().toString().contains("Compilation Successful.")));

            ////////////////////////////////////////////////////////////////////////////////////////////
            //close keyboard
            ////////////////////////////////////////////////////////////////////////////////////////////

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        public void setOut(Object userAnswer, Object correctAnswer, String method) {

            LayoutInflater inflater = getLayoutInflater();
            LinearLayout v0 = (LinearLayout) inflater.inflate(R.layout.puzzle_runner_out, null);
            TextView methodPager = (TextView) v0.findViewById(R.id.outMethod);
            TextView answerOut = (TextView) v0.findViewById(R.id.answer_out);
            TextView outUser = (TextView) v0.findViewById(R.id.userOut);

            methodPager.setText(method);

            if (method.contains("error")) {
                outUser.setText(userAnswer.toString());
                answerOut.setText("");
                answerOut.setVisibility(View.GONE);
            } else if (!userAnswer.equals(correctAnswer)) {
                outUser.setText("Your answer:\n" + userAnswer.toString());
                answerOut.setText("Correct answer:\n" + correctAnswer.toString());
            } else {
                outUser.setText("Your answer is:\nCorrect!");
                answerOut.setText("Output:\n" + correctAnswer.toString());
            }
            pagerAdapter.addView(v0, 0);
        }
    }
}
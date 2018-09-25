package mandj.appbuildin.codingcoach.innovo.Learn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import bsh.EvalError;
import bsh.Interpreter;
import mandj.appbuildin.codingcoach.innovo.R;
import mandj.appbuildin.codingcoach.innovo.Vibrate;

public class LearnRunner extends AppCompatActivity {

    static TextView txt;

    static Context con;
    static TextBoxListener ts;
    static LayoutInflater inflater;

    static String printer = "";

    final buildHandler buildhandler = new buildHandler();
    final popUpHandler popuphandler = new popUpHandler();
    final printHandler printhandler = new printHandler();

    Toolbar mToolbar;
    Interpreter interpreter;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    TextView SysIn;
    InputMethodManager inputManager;
    EditText strIn;
    View input;
    View output;
    TextView prompter;
    boolean start = true;
    String prompt = "";
    printTask print = new printTask();

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_runner);

        int lessonNum = getIntent().getIntExtra("position", 0);
        Resources res = getResources();
        TypedArray ta, ta1;
        String[] subtopics, lessons;
        int id;
        String topic;

        topic = res.getStringArray(R.array.topics)[lessonNum];
        Log.d("TOPIC", topic);

        prompt = res.getStringArray(R.array.prompts)[lessonNum];

        ta = res.obtainTypedArray(R.array.subTopics);
        id = ta.getResourceId(lessonNum, 0);
        subtopics = res.getStringArray(id);
        ta.recycle();

        ta1 = res.obtainTypedArray(R.array.lessons);
        id = ta1.getResourceId(lessonNum, 0);
        lessons = res.getStringArray(id);
        ta1.recycle();


        LinearLayout lin = (LinearLayout) findViewById(R.id.checkHolder);
        LinearLayout lin1 = (LinearLayout) findViewById(R.id.holder);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //Padding for subtopics cardViews (L,T,R,B)
        params.setMargins(8, 2, 8, 5);

        for (int c = 0; c < subtopics.length; c++) {


            //Start making the header and addes it to the LinLay
            final TextView textVi = new TextView(this);
            textVi.setText(subtopics[c]);
            textVi.setTextSize(18);
            lin1.addView(textVi);

            //creating card and adding it to the view
            final CardView cardView = new CardView(this);
            cardView.setRadius(dpToPx(3));
            cardView.setContentPadding(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
            cardView.setLayoutParams(params);
            lin1.addView(cardView);

            //makes the textview and adds it all to the card
            final TextView textView = new TextView(this);
            textView.setText(lessons[c]);
            cardView.addView(textView);
            //end making cardviews

            //This area adds the Checkbox
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(subtopics[c]);
            checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    CheckBox checkbox = (CheckBox) v;

                    if (checkbox.isChecked()) {
                        cardView.setVisibility(View.GONE);
                        textVi.setVisibility(View.GONE);
                    } else {
                        cardView.setVisibility(View.VISIBLE);
                        textVi.setVisibility(View.VISIBLE);
                    }
                }
            });
            lin.addView(checkBox);

            //end checkbox
        }

        con = this;

        strIn = (EditText) findViewById(R.id.editText);
        Button go = (Button) findViewById(R.id.runner);
        input = findViewById(R.id.containerIn);
        output = findViewById(R.id.containerOut);
        txt = (TextView) findViewById(R.id.interOutput);
        txt.setMovementMethod(new ScrollingMovementMethod());
        prompter = (TextView) findViewById(R.id.promptIn);

        if (prompt.equals("")) {
            go.setVisibility(View.GONE);
            output.setVisibility(View.GONE);
            input.setVisibility(View.GONE);
        } else {
            prompter.setText(prompt);
        }

        mToolbar = (Toolbar) (findViewById(R.id.toolbar));
        setSupportActionBar(mToolbar);
        setTitle(topic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        ts = new TextBoxListener(SysIn);
        //maybe this next line should be done in the TextFieldStreamer ctor
        //but that would cause a "leak a this from the ctor" warning

        System.setIn(ts);

        System.setOut(new PrintStream(new OutputStream() {

            @Override
            public void write(int oneByte) throws IOException {

                try {
                    Thread.sleep(0, 500000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                outputStream.write(oneByte);
                Message msg = buildhandler.obtainMessage(0, new String(outputStream.toByteArray()));
                buildhandler.sendMessage(msg);
                outputStream.reset();
            }
        }));

        new Thread(new printTask()).start();


        inflater = getLayoutInflater();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_u1__lesson1, menu);
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

    public void onRun(View v) throws IOException, InterruptedException {
        Vibrate.VibrateOne(20, this);

        if (start) {
            print.go();
            txt.setText("");
            prompter.setText(prompt);
            interpreter = new Interpreter();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                new backgroundTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
            else
                new backgroundTask().execute((Void[]) null);

        } else {

            print.stop();
            interpreter.stop();
            txt.setText("");
            output.setVisibility(View.INVISIBLE);
            output.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));

            input.setVisibility(View.VISIBLE);
            input.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.input_fade));
            txt.setText("");
        }
        start = !start;
    }

    public void openText() {
        Message msg = popuphandler.obtainMessage(0, "test");
        popuphandler.sendMessage(msg);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void stopTask() {
        try {
            Thread.sleep(100);
            interpreter.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class popUpHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            final EditText input = new EditText(con);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);

            new AlertDialog.Builder(con)
                    .setTitle("Your Code is Requesting Input!")
                    .setMessage("Please enter your desired input below")
                    .setView(input)
                    .setNeutralButton("input", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ts.InputField(input);
                        }
                    })
                            //.setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            super.handleMessage(msg);
        }
    }

    static class buildHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            printer += msg.obj.toString();
            super.handleMessage(msg);
        }
    }

    static class printHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            txt.append(msg.obj.toString());
            //int y = (txt.getLineCount() - 2) * txt.getLineHeight(); // the " - 1" should send it to the TOP of the last line, instead of the bottom of the last line
            //txt.scrollTo(0, y);

            super.handleMessage(msg);
        }
    }

    public class printTask implements Runnable {
        boolean stopped = true, interrupt = false;

        public void run() {
            while (true) {

                Message msg = printhandler.obtainMessage(0, printer);
                printhandler.sendMessage(msg);
                printer = "";

                if (interrupt) {
                    return;
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void go() {
            stopped = false;
        }

        public void stop() {
            stopped = true;
        }

    }

    public class backgroundTask extends AsyncTask<Object, Object, Boolean> {


        String err;
        String in;

        @Override
        protected void onPreExecute() {

            in = strIn.getText().toString();

            input.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.input_fade));
            input.setVisibility(View.INVISIBLE);

            output.setVisibility(View.VISIBLE);
            output.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));

            inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(strIn.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }

        @Override
        protected Boolean doInBackground(Object... params) {
            err = null;
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }

            try {
                final File dir = new File(String.valueOf(getApplicationContext().getFilesDir()));
                dir.mkdirs(); //create folders where write files
                final File file = new File(dir, "BlockForTest.txt");
                PrintStream print = new PrintStream(file.getAbsoluteFile());
                print.print(in);

                Thread.sleep(100);

                Log.d("Stop?", String.valueOf(interpreter.getStop()));
                interpreter.source(file.getAbsolutePath());

            } catch (InterruptedException | IOException e) {
                err = e.getMessage();
                e.printStackTrace();
            } catch (RuntimeException runEx) {
                runEx.printStackTrace();
                Log.d("END OF BSH", "END OF BSH");
            } catch (EvalError evalError) {
                String str = evalError.getMessage();
                err = "This command gave you an error: ";
                if (evalError.getMessage() != null) {
                    err += evalError.getMessage();
                    err += "\n\n" + ((str.contains("exception")) ? str.substring(str.lastIndexOf("exception"), str.length()) : "");
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Log.d("PostExecute", "Ending Process");

            if (err != null)
                txt.setText(err);

            err = null;
            super.onPostExecute(aBoolean);
        }
    }


}





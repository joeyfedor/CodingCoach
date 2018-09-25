package mandj.appbuildin.codingcoach.innovo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import mandj.appbuildin.codingcoach.innovo.Learn.LearnSpinner;
import mandj.appbuildin.codingcoach.innovo.Study.Runner.Help.HelpSwipe;
import mandj.appbuildin.codingcoach.innovo.Study.Runner.PuzzleRunner;
import mandj.appbuildin.codingcoach.innovo.Study.TabbedListView.PuzzleTabs;

public class Feedback extends AppCompatActivity {

    Toolbar mToolbar;
    String button = "Comment";
    Spinner spinner;
    String Username = "unknown";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mToolbar = (Toolbar) (findViewById(R.id.toolbar));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        populateSpinner();

        intent = getIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home && intent != null) {
            Class<?> callingActivity = intent.getExtras().getClass();
            Intent upIntent = new Intent(this, callingActivity);
            NavUtils.navigateUpTo(this, upIntent);
            return true;
        }


        /*int id = item.getItemId();
        if (id == android.R.id.home) {
            Class<?> callingClass;
            String callingActivity = "";
            Intent intent = getIntent();
            if (intent != null)
                callingActivity = getIntent().getStringExtra("callingActivity");

            if (callingActivity.equals("MainActivity"))
                callingClass = MainActivity.class;
            else if (callingActivity.equals("Help"))
                callingClass = HelpSwipe.class;
            else if (callingActivity.equals("PuzzleTabs"))
                callingClass = PuzzleTabs.class;
            else if (callingActivity.equals("PuzzleRunner"))
                callingClass = PuzzleRunner.class;
            else if (callingActivity.equals("Learn"))
                callingClass = LearnSpinner.class;
            else callingClass = MainActivity.class;

            Intent upIntent = new Intent(this, callingClass);
            if (callingClass.equals(PuzzleRunner.class))
                upIntent.putExtra("array", getIntent().getIntArrayExtra("array"));
            if (callingClass.equals(PuzzleTabs.class))
                upIntent.putExtra("tabInfo", getIntent().getExtras().getInt("tabInfo"));
            if (callingClass.equals("MainActivity"))
                upIntent.putExtra("open", true);
            NavUtils.navigateUpTo(this, upIntent);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        RadioButton radioButton, radioButton1, radioButton2, radioButton3;
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton4);

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButton:
                if (checked)
                    radioButton1.setChecked(false);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                button = "Comment";
                break;
            case R.id.radioButton1:
                if (checked)
                    radioButton.setChecked(false);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                button = "Bug";
                break;
            case R.id.radioButton2:
                if (checked)
                    radioButton.setChecked(false);
                radioButton1.setChecked(false);
                radioButton3.setChecked(false);
                button = "Misspelling";
                break;
            case R.id.radioButton4:
                if (checked)
                    radioButton.setChecked(false);
                radioButton1.setChecked(false);
                radioButton2.setChecked(false);
                button = "Other";
                break;
        }
    }

    public void populateSpinner() {
        //gets the name of the user
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("example_text", "");

        spinner = (Spinner) findViewById(R.id.editText);
        List<String> list = new ArrayList<String>();
        list.add(name + " - Change name in settings");
        list.add("Anonymous");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void populateSpinner(View v, Context context) {
        //gets the name of the user
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = preferences.getString("example_text", "");

        spinner = (Spinner) v.findViewById(R.id.editText);
        List<String> list = new ArrayList<String>();
        list.add(name + " - Change name in settings");
        list.add("Anonymous");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void sendFeedBack(View v) {
        sendFeedBack(this);
    }

    public void sendFeedBack(Activity context) {
        String name;

        spinner = (Spinner) context.findViewById(R.id.editText);
        Intent Email = new Intent(Intent.ACTION_SEND);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (spinner.getSelectedItem().toString().equals("Anonymous")) {
            name = "Anonymous";
        } else {
            name = preferences.getString("example_text", "");
        }

        String one = ("OS Name: " + System.getProperty("os.name"));
        String two = ("OS Version: " + System.getProperty("os.version"));
        String three = ("Api Level: " + String.valueOf(Build.VERSION.SDK_INT));
        String four = ("Device: " + Build.DEVICE);
        String five = ("Device Model: " + Build.MODEL);
        String six = ("Manufacturer" + Build.MANUFACTURER);
        String seven = ("Product: " + Build.PRODUCT);

        Email.setType("text/email");

        Email.putExtra(Intent.EXTRA_CC, new String[]{"joeyfedor@gmail.com", "monica.anuforo@gmail.com"});
        Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"codingcoachapp@gmail.com"});
        Email.putExtra(Intent.EXTRA_SUBJECT, "FeedBack type: " + button);
        EditText editText = (EditText) context.findViewById(R.id.editText2);
        Email.putExtra(Intent.EXTRA_TEXT, name + "\n" + one + "\n" + two + "\n" +
                three + "\n" + four + "\n" + five + "\n" +
                six + "\n" + seven + "\n" + editText.getText());
        context.startActivity(Intent.createChooser(Email, "Choose your Email"));
    }

    public class SelectingItem implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Username = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}

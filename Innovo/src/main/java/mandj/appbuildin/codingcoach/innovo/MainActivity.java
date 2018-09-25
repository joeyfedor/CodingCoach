package mandj.appbuildin.codingcoach.innovo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import mandj.appbuildin.codingcoach.innovo.Learn.LearnSpinner;
import mandj.appbuildin.codingcoach.innovo.Progress.BooleanPuz;
import mandj.appbuildin.codingcoach.innovo.Progress.LoopsPuz;
import mandj.appbuildin.codingcoach.innovo.Progress.Puzzle;
import mandj.appbuildin.codingcoach.innovo.Progress.PuzzleSave;
import mandj.appbuildin.codingcoach.innovo.Progress.StringPuz;
import mandj.appbuildin.codingcoach.innovo.Study.Runner.PuzzleRunner;
import mandj.appbuildin.codingcoach.innovo.Study.TabbedListView.ListContent;
import mandj.appbuildin.codingcoach.innovo.Study.TabbedListView.PuzzleTabs;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        MainFragment.OnFragmentInteractionListener, View.OnClickListener,
        ReferenceFragment.OnFragmentInteractionListener {

    private static int RC_SIGN_IN = 9001;
    String ExternalSave = "1";
    Context context;
    ArrayList<PuzzleSave> puzzleSaves = new ArrayList<>();
    ArrayList<PuzzleSave> puzzles1 = new ArrayList<>();
    ArrayList<PuzzleSave> addLocal = new ArrayList<>();
    ArrayList<PuzzleSave> addExternal = new ArrayList<>();
    ArrayList<PuzzleSave> compare = new ArrayList<>();
    ArrayList<Puzzle> puzzles = new ArrayList<>();
    int position = 1;
    int count = 0;
    String button = "Comment";
    Spinner spinner;
    String Username = "unknown";
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;
    String email;
    String bannerUrl = "http://hdwallpapers.cat/wallpaper/adorable_puppies_pets_labs_animals_dogs_hd-wallpaper-1176670.jpg";
    String coverPhotoUrl = "https://lh3.googleusercontent.com/-hOk9cXuL0RE/U5_kN" +
            "RdyYzI/AAAAAAAAAHo/4G1krM161Cw/s630-fcrop64=1,01600000ff14ff2e/google%2Bplus%2Bdefault%2Bcover%2Bphoto%2B1.jpg";
    String userInput = "";
    Fragment currentFrag;
    final private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private String mCurrentSaveName = "snapshotTemp";
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;
    private FragmentManager.OnBackStackChangedListener
            mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            syncActionBarArrowState();
        }
    };

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main_navdrawer);


        context = getApplicationContext();


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            int refPosition = intent.getExtras().getInt("toReference");
            position = refPosition;
        }


        if (savedInstanceState == null) {
            //sets the position of the navdrawer
            openFragment(position);
        } else {
            //currentFrag = getFragmentManager().getFragment(savedInstanceState, "mContent");
            //openFragment(currentFrag);
        }


        //gets nav drawer
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        //sets up toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        ViewCompat.setElevation(mToolbar, 1000);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //populates elements on the nav drawer
        setUpNav("Guest", "Email", coverPhotoUrl, bannerUrl);


        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        //adds a touch listener to the navdrawer
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int position = recyclerView.getChildPosition(child);

                    if (position == 7) {
                        Intent intent = new Intent(context, Settings.class);
                        intent.putExtra("callingActivity", "MainActivity");
                        startActivity(intent);
                        return false;
                    }

                    /*if(position==2){
                        Drawer.closeDrawers();
                        openFragment(position);

                        //new backgroundTask().execute(2);
                    }*/

                    if (position != 0/* && position !=2*/) {
                        Drawer.closeDrawers();
                        openFragment(position);

                        return true;
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        FragmentManager fm = getFragmentManager();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (fm.getBackStackEntryCount() > 0) {
                Log.i("MainActivity", "popping backstack");
                fm.popBackStack();
            } else {
                Log.i("MainActivity", "nothing on backstack, calling super");
                super.onBackPressed();
            }
            Log.d("test", "test");
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {

            if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }

            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;

        }
        return super.onKeyDown(keyCode, e);
    }

    private void openFragment(int pos) {
        if (pos == 0)
            pos = 1;
        //clearBackStack();
        // update the main content by replacing fragments
        position = pos;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        currentFrag = PlaceholderFragment.newInstance(pos + 1, this);
        fragmentTransaction
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, currentFrag)
                .commit();
    }

    private void openFragment(Fragment fragment) {
        // update the main content by replacing fragments
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, fragment)
                .commit();
    }

    // CLEAR BACK STACK.
    private void clearBackStack() {
        FragmentManager fragmentManager = getFragmentManager();
        while (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    public void onSectionAttached(int number) {
        ListContent listContent = new ListContent();
        listContent.setupNavList();
        String[] titles = listContent.getNavTitles();
        switch (number) {
            case 1:
                mTitle = "Coding Coach";
                break;
            case 2:
                mTitle = titles[1];
                break;
            case 3:
                mTitle = titles[2];
                break;
            case 4:
                mTitle = titles[3];
                break;
            case 5:
                mTitle = titles[4];
                break;
            case 6:
                mTitle = titles[5];
        }
        restoreActionBar();
    }

    public void updateTitle(String[] names, int number) { //For ReferenceFragment
        mTitle = names[number];
        restoreActionBar();
    }

    public void updateTitle(String name) { //For ReferenceFragment
        mTitle = name;
        restoreActionBar();
    }

    public void restoreActionBar() {
        //actionBar.setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("TAG", String.valueOf(id));
        if (mDrawerToggle.isDrawerIndicatorEnabled() &&
                mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == android.R.id.home /*&&
                /*getSupportFragmentManager().popBackStackImmediate()*/) {
            Toast.makeText(this, "Tasdfasd", Toast.LENGTH_LONG);
            return true;
        }

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            intent.putExtra("callingActivity", "MainActivity");
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_feedback) {
            Intent intent = new Intent(this, Feedback.class);
            intent.putExtra("callingActivity", "MainActivity");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public void onLearnClick(View b) {
        Vibrate.VibrateOne(20, this);
        Intent intent = new Intent(this, LearnSpinner.class);
        startActivity(intent);
    }

    public void onStudyClick(View b) {
        Vibrate.VibrateOne(20, this);
        Intent intent = new Intent(this, PuzzleTabs.class);
        startActivity(intent);/*setContentView(R.layout.)*/
    }

    public void onPreviousPuzzle(View b) {
        Vibrate.VibrateOne(20, this);
        Intent intent = new Intent(this, PuzzleRunner.class);
        startActivity(intent);
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
        spinner = (Spinner) findViewById(R.id.editText);
        List<String> list = new ArrayList<String>();
        list.add("Username - Inputed in settings");
        list.add("Anonymous");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void sendFeedBack(View v) {
        spinner = (Spinner) findViewById(R.id.editText);
        Intent Email = new Intent(Intent.ACTION_SEND);
        String name = ("User Name: " + Username);
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
        EditText editText = (EditText) findViewById(R.id.editText2);
        Email.putExtra(Intent.EXTRA_TEXT, name + "\n" + one + "\n" + two + "\n" +
                three + "\n" + four + "\n" + five + "\n" +
                six + "\n" + seven + "\n" + editText.getText());
        startActivity(Intent.createChooser(Email, "Choose your Email"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncActionBarArrowState();
    }

    @Override
    public void onClick(View v) {

    }

    private void setUpNav(String Name, String Email, String Profile, String Banner) {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        //mTitle = getTitle();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ListContent navstuff = new ListContent();
        ListContent.setupNavList(); //sets up the stuff in the list


        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true); // Letting the system know that the list objects are of fixed size
        mAdapter = new NavDrawerAdapter(navstuff.getNavTitles(), navstuff.getNavIcons(), Name, Email, Profile, Banner);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager

        Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        getFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);

        //mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
        final FragmentManager fm = getFragmentManager();

        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                    onBackPressed();
                }
            }

        });
    }

    private void updateNav(String Name, String Email, String Profile, String Banner) {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        //mTitle = getTitle();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ListContent navstuff = new ListContent();
        ListContent.setupNavList(); //sets up the stuff in the list


        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true); // Letting the system know that the list objects are of fixed size
        mAdapter = new NavDrawerAdapter(navstuff.getNavTitles(), navstuff.getNavIcons(), Name, Email, Profile, Banner);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager
    }

    @Override
    protected void onDestroy() {
        getFragmentManager().removeOnBackStackChangedListener(mOnBackStackChangedListener);
        super.onDestroy();
    }

    private void syncActionBarArrowState() {
        int backStackEntryCount =
                getFragmentManager().getBackStackEntryCount();
        mDrawerToggle.setDrawerIndicatorEnabled(backStackEntryCount == 0);
        Log.d("backStackEntryCount", String.valueOf(backStackEntryCount));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //getFragmentManager().putFragment(outState, "mContent", currentFrag);
        //outState.putInt("position", position);
        Log.d("Save MainActivity", "saved");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //openFragment(savedInstanceState.getInt("position"));
        //currentFrag = getFragmentManager().getFragment(savedInstanceState, "mContent");
        //openFragment(currentFrag);
        Log.d("open MainActivity", "saved");

        //openFragment(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onSkipSignin(View v) {
        setContentView(R.layout.activity_main_navdrawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        ViewCompat.setElevation(mToolbar, 10);
        setSupportActionBar(mToolbar);
        setUpNav("Anyonomous", email, coverPhotoUrl, bannerUrl);
        openFragment(position);
    }

    public void getPuzzleArray(Context context) {
        if (puzzles.size() == 0) {
            ArrayList<Puzzle> methPuzzles = new ArrayList<>();
            String correctness = "";

            for (File file : context.getFilesDir().listFiles()) {
                int number = 0;
                String name = file.getName();
                //tests to see if the file already exists if so tests for a win
                try {
                    FileInputStream fis = context.openFileInput(name);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader buffreader = new BufferedReader(isr);
                    correctness = buffreader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (correctness != null) {
                    if (correctness.contains("0")) {
                        number = 0;
                    } else if (correctness.contains("1")) {
                        number = 1;
                    } else if (correctness.contains("2")) {
                        number = 2;
                    }
                }

                Puzzle puz = new BooleanPuz("failure", number);

                if (name.contains("Logic")) {
                    puz = new BooleanPuz(name, number);
                } else if (name.contains("Loops")) {
                    puz = new LoopsPuz(name, number);
                } else if (name.contains("Strings")) {
                    puz = new StringPuz(name, number);
                }

                if (!puz.getPuzzleName().equals("failure"))
                    methPuzzles.add(puz);
            }


            ArrayList<String> logicNum = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.question_logic)));
            ArrayList<String> stringsNum = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.question_strings)));
            ArrayList<String> loopsNum = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.question_loops)));
            Puzzle puz;

            ArrayList<String> strings = new ArrayList<>();
            for (Puzzle puzzle : methPuzzles)
                strings.add(puzzle.getPuzzleName());


            count = 1;
            for (String str : logicNum) {
                str = "Logic #" + count;
                puz = new BooleanPuz(str, 0);
                if (!strings.contains(str)) {
                    methPuzzles.add(puz);
                }
                count++;
            }

            count = 1;
            for (String str : stringsNum) {
                str = "Strings #" + count;
                puz = new StringPuz(str, 0);
                if (!strings.contains(str)) {
                    methPuzzles.add(puz);
                }
                count++;
            }

            count = 1;
            for (String str : loopsNum) {
                str = "Loops #" + count;
                puz = new LoopsPuz(str, 0);
                if (!strings.contains(str)) {
                    methPuzzles.add(puz);
                }
                count++;
            }

            this.puzzles = methPuzzles;
        }
    }

    private void computeChanges() throws InterruptedException {

        Scanner scan = new Scanner(ExternalSave);
        String title = "...", completeness = " ", data = " ", line;

        while (scan.hasNextLine() && !(title.length() < 3)) {
            boolean innercont = true;
            title = scan.nextLine();

            if (scan.hasNextLine())
                completeness = scan.nextLine();

            while (innercont && scan.hasNextLine()) {
                line = scan.nextLine();
                if (line.contains("**/NEXT/**")) {
                    innercont = false;
                } else {
                    data += (line + "\n");
                }
            }


            PuzzleSave save = new PuzzleSave(title, completeness, data);
            puzzleSaves.add(save);
            data = "";
            completeness = "";

            //Takes out all the repeats

            List<String> al = new ArrayList<>();
            // add elements to al, including duplicates
            Set<String> hs = new HashSet<>();
            hs.addAll(al);
            al.clear();
            al.addAll(hs);

            //

        }


        scan = new Scanner(userInput);
        data = "";
        title = "...";

        while (scan.hasNextLine() && !(title.length() < 3)) {
            boolean innercont = true;

            title = scan.nextLine();

            completeness = scan.nextLine();

            while (innercont && scan.hasNextLine()) {
                line = scan.nextLine();
                if (line.contains("**/NEXT/**")) {
                    innercont = false;
                } else {
                    data += (line + "\n");
                }
            }


            PuzzleSave save = new PuzzleSave(title, completeness, data);
            puzzles1.add(save);
            data = "";
            completeness = "";
        }


        boolean savepuzzle;


        Collections.sort(puzzles1);
        Collections.sort(puzzleSaves);

        for (PuzzleSave puzzleSave : puzzles1) {
            savepuzzle = true;
            for (PuzzleSave puzzleSave1 : puzzleSaves) {
                if (puzzleSave.getName().equals(puzzleSave1.getName())) {
                    savepuzzle = false;
                    compare.add(puzzleSave);
                }
            }
            if (savepuzzle) {
                addExternal.add(puzzleSave);
            }
        }


        // gets the puzzles from the external and internal save and
        // for any that are missing adds them to the Internal list to be saved.
        for (PuzzleSave puzzleSave : puzzleSaves) {
            savepuzzle = true;
            for (PuzzleSave puzzleSave1 : puzzles1) {

                if (puzzleSave.getName().equals(puzzleSave1.getName())) {
                    savepuzzle = false;
                }
            }
            if (savepuzzle) {
                addLocal.add(puzzleSave);
                addExternal.add(puzzleSave);
            }
        }

        //iterates through the compare array and saves data according to completeness.
        for (PuzzleSave puzzleSave : compare) {

            if (puzzleSave.getName().equals("LAST_PUZZLE")) {
                addExternal.add(puzzleSave);
            } else {
                for (PuzzleSave puzzleSave1 : puzzleSaves) {
                    if (puzzleSave.getName().equals(puzzleSave1.getName())) {
                        if (puzzleSave.getCompleteness().equals(puzzleSave1.getCompleteness())) {
                            if (puzzleSave.getData().equals(puzzleSave1.getData())) {
                                addExternal.add(puzzleSave);
                            } else {
                                addExternal.add(puzzleSave);
                            }

                        } else {
                            try {
                                if (Integer.parseInt(puzzleSave.getCompleteness()) < Integer.parseInt(puzzleSave1.getCompleteness())) {
                                    addLocal.add(puzzleSave);
                                    addExternal.add(puzzleSave);
                                } else if (Integer.parseInt(puzzleSave.getCompleteness()) > Integer.parseInt(puzzleSave1.getCompleteness())) {
                                    addLocal.add(puzzleSave1);
                                    addExternal.add(puzzleSave1);
                                } else {
                                    addLocal.add(puzzleSave);
                                    addExternal.add(puzzleSave);
                                }
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                }
            }
        }

        Collections.sort(addExternal);
        Collections.sort(addLocal);

    }

    public int getNumPuzzles(Context context) {
        getPuzzleArray(context);

        int amount = 0;
        for (Puzzle puz : puzzles) {
            amount++;
        }
        return amount;
    }

    public int getNumCorrect(Context context) {
        getPuzzleArray(context);

        int amount = 0;
        for (Puzzle puz : puzzles) {
            if (puz.getCompletness() == 1)
                amount++;
        }
        return amount;
    }

    public int getNumIncorrect(Context context) {
        getPuzzleArray(context);

        int amount = 0;
        for (Puzzle puz : puzzles) {
            if (puz.getCompletness() == 2)
                amount++;
        }
        return amount;
    }

    public int getNumUncompleted(Context context) {
        getPuzzleArray(context);

        int amount = 0;
        for (Puzzle puz : puzzles) {
            if (puz.getCompletness() == 0)
                amount++;
        }
        return amount;
    }

    public ArrayList<String> getNamesCorrect(Context context) {
        getPuzzleArray(context);

        ArrayList<String> arrayList = new ArrayList<>();
        for (Puzzle puz : puzzles) {
            if (puz.getCompletness() == 1)
                arrayList.add(puz.getPuzzleName());
        }
        return arrayList;
    }

    public ArrayList<String> getNamesInCorrect(Context context) {
        getPuzzleArray(context);

        ArrayList<String> arrayList = new ArrayList<>();
        for (Puzzle puz : puzzles) {
            if (puz.getCompletness() == 2)
                arrayList.add(puz.getHtmlName());
        }
        return arrayList;
    }

    public ArrayList<String> getNamesUncompleted() {
        getPuzzleArray(this);

        ArrayList<String> arrayList = new ArrayList<>();
        for (Puzzle puz : puzzles) {
            if (puz.getCompletness() == 0)
                arrayList.add(puz.getHtmlName());
        }
        return arrayList;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public static Fragment newInstance(int sectionNumber, Context context) {
            if (sectionNumber == 3) {
                Class<?> clss = ReferenceFragment.class;
                Bundle args = new Bundle();
                args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                args.putString("callingActivity", "MainActivity");
                Fragment fragment = Fragment.instantiate(context, clss.getName(), args);
                fragment.setArguments(args);
                return fragment;
            }
            Class<?> clss = MainFragment.class;
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            Fragment fragment = Fragment.instantiate(context, clss.getName(), args);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_new_main, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }
}

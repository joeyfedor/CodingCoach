package mandj.appbuildin.codingcoach.innovo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import mandj.appbuildin.codingcoach.innovo.Study.TabbedListView.PuzzleTabs;

/**
 * Created by Monica on 8/29/2015.
 */
public class Reference extends AppCompatActivity implements ReferenceFragment.OnFragmentInteractionListener {
    Toolbar mToolbar;
    Fragment currentFrag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);

        mToolbar = (Toolbar) (findViewById(R.id.toolbar));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        openFragment(0);

    }

    private void openFragment(int pos) {
        // update the main content by replacing fragments
        //position = pos;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        currentFrag = PlaceholderFragment.newInstance(pos + 1, this);
        fragmentTransaction
                .addToBackStack("Reference")
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, currentFrag)
                .commit();
    }

    private void openFragment(Fragment fragment) {
        // update the main content by replacing fragments
        //position = pos;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction
                .addToBackStack("Reference")
                        //.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, fragment)
                .commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "mContent", currentFrag);
        Log.d("Reference", "saved");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //openFragment(savedInstanceState.getInt("position"));
        //currentFrag = getFragmentManager().getFragment(savedInstanceState, "mContent");
        //currentFrag = getFragmentManager().getFragment(savedInstanceState, "mContent");
        //openFragment(currentFrag);
        Log.d("open Reference", "saved");
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("test", "test");
            return true;
        }
        return false;
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
        if (id == android.R.id.home) {
            Intent upIntent = new Intent(this, PuzzleTabs.class);
            upIntent.putExtra("toReference", 2);
            NavUtils.navigateUpTo(this, upIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }


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
            Class<?> clss = ReferenceFragment.class;
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
}

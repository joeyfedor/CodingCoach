package mandj.appbuildin.codingcoach.innovo.Study.Runner.Help;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Locale;

import mandj.appbuildin.codingcoach.innovo.Feedback;
import mandj.appbuildin.codingcoach.innovo.R;
import mandj.appbuildin.codingcoach.innovo.Settings;

//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;

public class HelpSwipe extends Activity implements HelpOne.OnFragmentInteractionListener, DialogInterface {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_swipe);
        CharSequence helpWords = "Swipe to navigate.";
        Toast helpToast = Toast.makeText(this, helpWords, Toast.LENGTH_SHORT);
        helpToast.show();

        //Adjusts window size.
        WindowManager.LayoutParams params = getWindow().getAttributes();
        //params.x = -20;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        params.height = (int) (size.y * .9);
        params.width = (int) (size.x * .9);
        params.gravity = Gravity.CENTER;
        //params.y = -10;
        //params.horizontalMargin = 20;
        //params.verticalMargin = 20;
        this.getWindow().setAttributes(params);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //Swipe pageAdapter = new Swipe(this, mViewPager, getFragmentManager());

        /*final TextView closeText = (TextView)findViewById(R.id.textViewClose);
        closeText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                closeText.setTextColor(getResources().getColor(R.color.clicked)); //not working
                return true;
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.help_swipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            intent.putExtra("callingActivity", "Help");
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_feedback) {
            Intent intent = new Intent(this, Feedback.class);
            intent.putExtra("callingActivity", "Help");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }

    public void onCloseClick(View v) {

        finish();
    }

    public int getCurrentPage() {
        return mViewPager.getCurrentItem() + 1;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
        //public static int sectionNum;
        //private static final Context mContext;

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

        public static Fragment newIInstance(int sectionNumber, Context context) {
            Class<?> clss = HelpOne.class; //default
            /*if(sectionNumber == 1)
                //clss = HelpOne.class;
                sectionNum = 1;
            else if(sectionNumber == 2)
                //clss = HelpOne.class;
                sectionNum = 2;
            else if(sectionNumber == 3)
                //clss = HelpOne.class;
                sectionNum = 3;*/
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            Fragment fragment = Fragment.instantiate(context, clss.getName(), args);
            //fragment.setArguments(args);
            return fragment;//Fragment.instantiate(context, clss.getName(), null);
        }

        public Context setAndGetContext(Activity activity) {
            return activity;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_help_swipe, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        final Context mContext;

        public SectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            return PlaceholderFragment.newIInstance(position + 1, mContext);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }

    }

}

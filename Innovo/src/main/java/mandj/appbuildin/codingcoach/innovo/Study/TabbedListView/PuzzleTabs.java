package mandj.appbuildin.codingcoach.innovo.Study.TabbedListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import mandj.appbuildin.codingcoach.innovo.Feedback;
import mandj.appbuildin.codingcoach.innovo.R;
import mandj.appbuildin.codingcoach.innovo.Settings;
import mandj.appbuildin.codingcoach.innovo.SlidingTabLayout;
import mandj.appbuildin.codingcoach.innovo.Study.Runner.PuzzleRunner;

public class PuzzleTabs extends AppCompatActivity implements LogicFragment.OnFragmentInteractionListener,
        StringsFragment.OnFragmentInteractionListener, LoopsFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    // SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    TabsAdapter mTabsAdapter;
    Toolbar mToolbar;
    SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_tabs);

        // Set up the action bar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        //mViewPager = new ViewPager(this);
        //mViewPager.setId(R.id.pager);
        //setContentView(mViewPager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
        // TODO Set Elevation of the ActionBar Tabs with default elevation
        //mSlidingTabLayout.setElevation(getSupportActionBar().getElevation());
        mTabsAdapter = new TabsAdapter(this, mViewPager, mSlidingTabLayout);
        mTabsAdapter.addTab(/*actionBar.newTab().setIcon(R.drawable.appbar_type_boolean_black),*/ LogicFragment.class, null);
        mTabsAdapter.addTab(/*actionBar.newTab().setIcon(R.drawable.appbar_grade_a_black),*/ StringsFragment.class, null);
        mTabsAdapter.addTab(/*actionBar.newTab().setIcon(R.drawable.appbar_loop_black),*/ LoopsFragment.class, null);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                switch (position) {
                    case (0):
                        return getResources().getColor(R.color.toolbar_accent);
                    case (1):
                        return getResources().getColor(R.color.toolbar_accent1);
                    case (2):
                        return getResources().getColor(R.color.toolbar_accent2);
                    default:
                        return 0;
                }
            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            int tabNumber = intent.getExtras().getInt("tabInfo");
            mTabsAdapter.setCurrentTab(tabNumber);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.puzzle_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            intent.putExtra("callingActivity", "PuzzleTabs");
            intent.putExtra("tabInfo", mTabsAdapter.getCurrentTab());
            startActivity(intent);
            return true;
        } else if (id == R.id.action_feedback) {
            Intent intent = new Intent(this, Feedback.class);
            intent.putExtra("callingActivity", "PuzzleTabs");
            intent.putExtra("tabInfo", mTabsAdapter.getCurrentTab());
            startActivity(intent);
            return true;
        } else if (id == R.id.action_previous_puzzle) {
            Intent intent = new Intent(this, PuzzleRunner.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}

package mandj.appbuildin.codingcoach.innovo.Study.TabbedListView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import java.util.ArrayList;

import mandj.appbuildin.codingcoach.innovo.SlidingTabLayout;


public class TabsAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    private final Context mContext;
    private final ActionBar mActionBar;
    private final ViewPager mViewPager;
    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
    private final String TAG = "21st Polling:";

    public TabsAdapter(PuzzleTabs fa, ViewPager pager, SlidingTabLayout slidingTabLayout) {
        super(fa.getSupportFragmentManager());
        mContext = fa;
        mActionBar = fa.getSupportActionBar();
        mViewPager = pager;
        mViewPager.setAdapter(this);
        mViewPager.setOnPageChangeListener(this);
        //slidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name;
        switch (position) {
            case (0):
                name = "      Boolean Logic      "; //added spaces because they were too small :P
                break;
            case (1):
                name = "      Strings      ";
                break;
            case (2):
                name = "      Loops      ";
                break;
            case (3):
                name = "      Recursion      ";
                break;
            default:
                name = "      Boolean Logic      ";
                break;
        }
        return name;
    }

    public void addTab(/*ActionBar.Tab tab,*/ Class<?> clss, Bundle args) {
        TabInfo info = new TabInfo(clss, args);
        //tab.setTag(info);
        //tab.setTabListener(this);
        mTabs.add(info);
        //mActionBar.addTab(tab);
        notifyDataSetChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public Fragment getItem(int position) {
        TabInfo info = mTabs.get(position);
        return Fragment.instantiate(mContext, info.clss.getName(), info.args);
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    public int getCurrentTab() {
        return mViewPager.getCurrentItem();
    }

    public void setCurrentTab(int i) {
        mViewPager.setCurrentItem(i);
    }

    static final class TabInfo {
        private final Class<?> clss;
        private final Bundle args;

        TabInfo(Class<?> _class, Bundle _args) {
            clss = _class;
            args = _args;
        }
    }

}

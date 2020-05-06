package com.sstechcanada.resumeapp.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.google.android.material.tabs.TabLayout;
import com.sstechcanada.resumeapp.R;
import com.sstechcanada.resumeapp.fragments.jobs.GoogleJobs;
import com.sstechcanada.resumeapp.fragments.jobs.Indeed;
import com.sstechcanada.resumeapp.fragments.jobs.LinkedIn;

public class JobSearchResultActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_search_results);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
         mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    GoogleJobs tab1 = new GoogleJobs();
                    return tab1;

                case 1:
                    Indeed tab2 = new Indeed();
                    return tab2;

                case 2:
                    LinkedIn tab3 = new LinkedIn();
                    return tab3;



                default:
                    return null;
            }
        }


        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "GoogleJobs";

                case 1:
                    return "Indeed";

                case 2:
                    return "LinkedIn";

                default:
                    return null;

            }


        }

    }

}
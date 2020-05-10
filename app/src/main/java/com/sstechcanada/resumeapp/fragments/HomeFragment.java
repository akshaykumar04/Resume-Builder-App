package com.sstechcanada.resumeapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eftimoff.viewpagertransformers.DrawFromBackTransformer;
import com.google.android.material.tabs.TabLayout;
import com.sstechcanada.resumeapp.R;

import com.sstechcanada.resumeapp.fragments.home.JefHome;
import com.sstechcanada.resumeapp.fragments.home.JobSearchHome;
import com.sstechcanada.resumeapp.fragments.home.ResumeHome;


public class HomeFragment extends Fragment {
    private CardView jobs, resume, jef;
    private ViewPager homeViewPager;
    private SectionsPagerAdapter HomeAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, viewGroup, false);

      HomeAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        homeViewPager = (ViewPager) view.findViewById(R.id.container);
        homeViewPager.setAdapter(HomeAdapter);
        homeViewPager.setPageTransformer(true, new DrawFromBackTransformer());

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        homeViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(homeViewPager));

//
//        jobs = view.findViewById(R.id.JobsCard);
//        jef = view.findViewById(R.id.cardView2);
//        resume = view.findViewById(R.id.cardView);
//
//        resume.setOnClickListener(view2 ->{
//            startActivity(new Intent(getActivity(), ResumeBuilderActivity.class));
//        });
//
//        jobs.setOnClickListener(view1 -> {
//            startActivity(new Intent(getActivity(), JobSearchActivity.class));
//        });
//
//        jef.setOnClickListener(view2 -> {
//            startActivity(new Intent(getActivity(), JefBookingActivity.class));
//        });





        return view;


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    ResumeHome tab1 = new ResumeHome();
                    return tab1;

                case 1:
                    JefHome tab2 = new JefHome();
                    return tab2;

                case 2:
                    JobSearchHome tab3 = new JobSearchHome();
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
                    return "Resume";

                case 1:
                    return "JEF";

                case 2:
                    return "Job Search";

                default:
                    return null;

            }


        }

    }





}

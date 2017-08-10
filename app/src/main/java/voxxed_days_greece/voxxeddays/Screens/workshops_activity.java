package voxxed_days_greece.voxxeddays.Screens;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.adapters.mWorkshopAdapter;
import voxxed_days_greece.voxxeddays.api.get_workshops;
import voxxed_days_greece.voxxeddays.models.workshops;

public class workshops_activity extends AppCompatActivity {

    private get_workshops mWorkshopObject =null;
    private int daysCount=0;
    private SharedPreferences sharedPreferences=null;
    private static ListView mListView =null;
    private static ArrayList<workshops> AllWorkshops=null,WorkshopsListByDay=null;
    private static Context mApplicationContext = null;
    private static SharedPreferences.Editor editor = null;





    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workshops_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        sharedPreferences = getSharedPreferences(First_Screen.STATE_SELECTION,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mApplicationContext = getApplicationContext();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //start the background staff
        BackGroundWork mBackgroundWork = new BackGroundWork();
        mBackgroundWork.execute();




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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_workshops_activity, container, false);
            mListView = (ListView) rootView.findViewById(R.id.mWorkshop_ListView);
            mListView.setAdapter(new mWorkshopAdapter(mApplicationContext,fillWorksDayArray(getArguments().getInt(ARG_SECTION_NUMBER)),editor));






            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show how many workshop days we have.
            return sharedPreferences.getInt("WorkshopDays",5);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Day " + String.valueOf(position+1);
        }
    }



    public class BackGroundWork extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            //get the workshop object
            AllWorkshops =new ArrayList<>();
            WorkshopsListByDay= new ArrayList<>();
            mWorkshopObject = get_workshops.getWorkShopObject();
            AllWorkshops = mWorkshopObject.returnWorkShopsList();
            return null;
        }
    }

    public static ArrayList<workshops> fillWorksDayArray(int chooseDay){
        Log.e("Data","The Number is : " + String.valueOf(chooseDay));
        ArrayList<workshops>  mListArray = new ArrayList<>();



        for (workshops mworkshops : AllWorkshops){
            if(mworkshops.day==chooseDay){
                Log.e("Data","The Name is : " + mworkshops.getWorkshop_name());
                mListArray.add(mworkshops);
            }
        }
        return mListArray;
    }





}

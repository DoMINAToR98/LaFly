package sodevan.lafly;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class Home extends AppCompatActivity {

    ViewPagerAdapter adapter ;
    ViewPager pager;
    SlidingTabLayout tabs;
    int n=3  ; // No of sliding Tab

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CharSequence Titles[] = {"Events" , "EnLearn","Forum"};

        pager = (ViewPager)findViewById(R.id.pager);

        Context c = getApplicationContext();



        adapter = new ViewPagerAdapter(getSupportFragmentManager() , Titles ,n, c ) ;

        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);

        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        tabs.setViewPager(pager);

        pager.getAdapter().notifyDataSetChanged();

    }
}

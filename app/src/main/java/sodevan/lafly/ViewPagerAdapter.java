package sodevan.lafly;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ravipiyush on 10/10/16.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[] ;
    int n ;
    Context c ;




    public ViewPagerAdapter(FragmentManager fm  ,CharSequence Titles[] , int n , Context c ) {
        super(fm);

        this.Titles = Titles ;
        this.n = n ;
        this.c=c;
    }

    @Override
    public Fragment getItem(int position) {


//        if(position==0) {
//            Info info = new Info() ;
//            info.setC(c);
//            return  info ;
//        }


         if(position==0) {
            EnLearn enLearn = new EnLearn() ;
            enLearn.setC(c);
            return  enLearn  ;
        }

      else   if(position==1) {
            Forum forum = new Forum() ;
            forum.setC(c);
            return  forum  ;
        }

        else  {
            Events events = new Events() ;
            events.setC(c);

            return  events  ;
        }



    }

    @Override
    public int getCount() {
        return n ;
    }


    @Override
    public Parcelable saveState() {
        return null ;
    }


}

package sodevan.lafly;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnLearn  extends Fragment {
    private ListView lv;
    FirebaseDatabase database;
    DatabaseReference ref;
    private YouTubePlayer YPlayer;
    Context c ;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.activity_en_learn ,container  , false) ;

        database = FirebaseDatabase.getInstance() ;
        ref = database.getReference("Enlearn Videos")  ;
        lv = (ListView) v.findViewById(R.id.enlearnlistview);
     final YouTubePlayerSupportFragment youTubePlayerFragment = (YouTubePlayerSupportFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.youtube_fragment);
        //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
     //   transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();



        FirebaseListAdapter<VideoChild> fb = new FirebaseListAdapter<VideoChild>(getActivity(), VideoChild.class, R.layout.videochild, ref) {

            @Override
            protected void populateView(View v, VideoChild model, int position) {



                final String url=model.getURL();
                String date=model.getDate();
                String name=model.getName();
                TextView Date=(TextView)v.findViewById(R.id.date);
                TextView Name=(TextView)v.findViewById(R.id.heading);
                Name.setText(name);
                Date.setText(date);

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();


                youTubePlayerFragment.initialize("AIzaSyCzEKeIrR_Cn-lORRuu0z-1PiD0kCYOI4I", new YouTubePlayer.OnInitializedListener(){


                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
                    {
                        if (!b) {
                            YPlayer =youTubePlayer;
                            YPlayer.setFullscreen(true);
                            YPlayer.loadVideo(url);
                            YPlayer.play();
                        }




                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
                    {
                        Log.d("Error","Error");



                    }



                });





            }


        };
        lv.setAdapter(fb);
        return v ;

    }

    public void setC(Context c) {
        this.c = c;
    }

}

package sodevan.lafly;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static android.content.Context.LOCATION_SERVICE;


public class Info extends Fragment {

    Context c = getContext();
    HashMap<String , Float > cart  ;
    ArrayList<StoreItem> storeItems ;
    CustomStoreAdapter adapter ;
    ListView lv ;
    String order="" ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View V= inflater.inflate(R.layout.activity_info, container, false);

        setHasOptionsMenu(true);

        storeItems = new ArrayList<>() ;
        cart = new HashMap<String, Float>()  ;

        lv = (ListView)V.findViewById(R.id.lvstore) ;

        storeItems.add(new StoreItem("Friends Maternity Pads" , 169 , R.drawable.a5  ,false) ) ;
        storeItems.add(new StoreItem("Stayfree Dry Max Ultra Dry" , 155 ,R.drawable.a5,false));
        storeItems.add(new StoreItem("Carefree Sanitary Pads" , 135 ,R.drawable.a5,false));
        storeItems.add(new StoreItem("Tresemme Spa Rejuvenation Conditioner" , 204 ,R.drawable.a5,false));
        storeItems.add(new StoreItem("Whisper Choice Regular wings" , 570 ,R.drawable.a5,false));
     //   storeItems.add(new StoreItem("Nike Up or Down Deodorant(women)" , 184 ,R.drawable.a6));
      //  storeItems.add(new StoreItem("Revital H Woman " , 269 ,R.drawable.a7));
       // storeItems.add(new StoreItem(".Trophic Wellness Nuticharge" , 302 ,R.drawable.a8));
        //storeItems.add(new StoreItem("Nivea Whitening Skin Roll" , 131 ,R.drawable.a9));
        //storeItems.add(new StoreItem("Gillette Venus Gift Pack" , 549 ,R.drawable.a10));


        adapter = new CustomStoreAdapter(c , 0 ,storeItems) ;


        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

              RelativeLayout rv  = (RelativeLayout) v.findViewById(R.id.maina) ;
                StoreItem item = (StoreItem) parent.getItemAtPosition(position) ;

                 if(!item.isStatus()){

                     item.setStatus(true);
                     rv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                     cart.put(item.getIname() , item.getPrice()) ;
                 }

                else {
                     rv.setBackgroundColor(getResources().getColor(R.color.tria));
                     item.setStatus(false);
                     cart.remove(item.getIname()) ;

                     Log.e("pos", position + "");



                   }
                }
        });



        return V ;
    }




    public void setC(Context c) {
        this.c = c;
    }


    public class CustomStoreAdapter extends ArrayAdapter<StoreItem> {



        public CustomStoreAdapter(Context context, int resource , ArrayList<StoreItem> items) {
            super(context,resource,items);

        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            if (v==null){
                v=getActivity().getLayoutInflater().inflate(R.layout.store_child,parent,false);
            }
          final  StoreItem child=storeItems.get(position);





                TextView tv1 = (TextView) v.findViewById(R.id.nameitem);
                TextView tv2 = (TextView) v.findViewById(R.id.price);
                ImageView im = (ImageView) v.findViewById(R.id.imageView2) ;
                tv1.setText(child.getIname());
                tv2.setText("₹ "+child.getPrice()+"");




            return v;


        }






    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu3 , menu);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext()) ;
        boolean b = sp.getBoolean("LaLifestatus" , false) ;

        MenuItem item =  menu.findItem(R.id.lalife_check_box2) ;
        item.setChecked(b)  ;

        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());


        switch (item.getItemId()) {

            case R.id.checkout:
                for (String key : cart.keySet()){
                    order = order + key + " : Rs"+cart.get(key) + "\n" ;
                }
                    if(!order.equals("")){
                   Intent Ord = new Intent(getContext(), OrderScreen.class);
                Ord.putExtra("orders" , order) ;
                startActivity(Ord);}

                else {
                        Toast.makeText(c, "Cart Empty", Toast.LENGTH_SHORT).show();
                    }
                break ;

            case R.id.lalife_check_box2:
                if (item.isChecked()) {
                    item.setChecked(false);
                    sp.edit().putBoolean("LaLifestatus", false).commit();
                    Toast.makeText(getContext(), " LaLife Deactivated ", Toast.LENGTH_SHORT).show();
                    getActivity().stopService(new Intent(getActivity(),HUD.class));
                    // stop the widget services

                } else {
                    item.setChecked(true);
                    sp.edit().putBoolean("LaLifestatus", true).commit() ;
                    Toast.makeText(getContext(), "LaLife Activated", Toast.LENGTH_SHORT).show();
                    getActivity().startService(new Intent(getActivity(),HUD.class));



                }

                break ;

            case R.id.Configlalife1:
                Intent config  = new Intent(getContext() , ConfigureLalife.class) ;
                startActivity(config);

        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onResume() {
        order="" ;
        super.onResume();
    }
}

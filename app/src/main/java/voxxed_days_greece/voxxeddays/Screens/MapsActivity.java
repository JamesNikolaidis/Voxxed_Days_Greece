package voxxed_days_greece.voxxeddays.Screens;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

import voxxed_days_greece.voxxeddays.R;
import voxxed_days_greece.voxxeddays.api.getVenueDetails;
import voxxed_days_greece.voxxeddays.models.venue;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Spinner select_thessloniki_state=null;
    private venue mVenueObject = null;
    private TextView mPlace=null,mTime=null,mDate=null;
    private double lot,lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mVenueObject = getVenueDetails.getVenuObject().returnVenueObject();
        //*******************************
        Geocoder coder = new Geocoder(this);
        try {

            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(mVenueObject.address,1);
            lat = adresses.get(0).getLatitude();
            lot = adresses.get(0).getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //*********************




        mPlace =(TextView)findViewById(R.id.venue_place);
        mTime = (TextView)findViewById(R.id.venu_time);
        mDate = (TextView)findViewById(R.id.venue_date);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mPlace.setText(mVenueObject.getPlace());
        mTime.setText(mVenueObject.getTime());
        mDate.setText(mVenueObject.getDate());
        select_thessloniki_state = (Spinner) findViewById(R.id.select_thessaloniki_state);
        select_thessloniki_state.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.town_state_adapter,R.id.ggggg,this.getResources().getStringArray(R.array.thessaloniki_states)));



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //make 3d building
        mMap.setBuildingsEnabled(true);



        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat,lot);
        mMap.addMarker(new MarkerOptions().position(sydney).title(mVenueObject.getPlace()));

       //    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,14));
        //CameraUpdateFactory.zoomTo(5.0f);
        CameraPosition mCameraPosition = new CameraPosition.Builder().target(sydney).zoom(17).bearing(180).tilt(90).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));



    }
}

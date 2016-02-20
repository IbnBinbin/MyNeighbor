package ibn.myneighbor;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.graphics.Color;

public class NeighborhoodActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int drawOption;
    private LatLng firstClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighborhood);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        drawOption = -1;

        ImageButton circle = (ImageButton) findViewById(R.id.circle);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Ibn", "draw circle");
                drawOption = 0;
            }
        });
        ImageButton point = (ImageButton) findViewById(R.id.point);
        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Ibn", "draw point");
                drawOption = 1;
            }
        });

        ImageButton line = (ImageButton) findViewById(R.id.line);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Ibn", "draw line");
                drawOption = 2;
            }
        });

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

        // Add a marker in Sydney and move the camera
//        Log.d("Ibn",mMap.getMyLocation().getLatitude()+" "+mMap.getMyLocation().getLongitude());
        LatLng glasgow = new LatLng(55.8580, -4.2590);
//        mMap.addMarker(new MarkerOptions().position(glasgow).title("Glasgow"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(glasgow));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(glasgow, 12.0f));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (drawOption == 0) {
                    firstClick = latLng;
                } else if (drawOption == 1) {
//                    mMap.addMarker(new MarkerOptions().position(latLng));

                } else if (drawOption == 2) {
                    firstClick = latLng;
                }
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d("Ibn", "draw option: " + drawOption);
                double center = 0;
                int radius = getDistanceFromLatLonInKm(firstClick,latLng);
                if (drawOption == 0) {
                    mMap.addCircle(new CircleOptions()
                            .center(new LatLng(latLng.latitude, latLng.longitude))
                            .radius(radius)
                            .strokeColor(Color.RED));
//                .fillColor(Color.BLUE));
                } else if (drawOption == 1) {
                    mMap.addMarker(new MarkerOptions().position(latLng));

                } else if (drawOption == 2) {
                    mMap.addPolyline(new PolylineOptions()
                            .add(latLng, firstClick)
                            .width(5)
                            .color(Color.GREEN));
                }
            }
        });

    }

    public int getDistanceFromLatLonInKm(LatLng ll1,LatLng ll2) {
        int R = 6371; // Radius of the earth in m
        double dLat = deg2rad(ll2.latitude-ll1.latitude);  // deg2rad below
        double dLon = deg2rad(ll2.longitude-ll1.longitude);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(ll1.latitude)) * Math.cos(deg2rad(ll2.latitude)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c *1000; // Distance in m
        return (int)d;
    }

    public double deg2rad(Double deg) {
        return deg * (Math.PI/180);
    }
}

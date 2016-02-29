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
import android.widget.Button;
import android.graphics.Color;
import android.widget.Toast;

import java.util.ArrayList;

import ibn.myneighbor.Model.Neighborhood;

public class NeighborhoodActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int drawOption;
    private int clickedinitial;
    private LatLng firstClick;
    private LatLng finalP;
    private ArrayList<String[]> allLatLng;
    private String[] aLatLng; //0:init/1:final/2:type
    private ArrayList<Neighborhood> nb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApp.initOnBroadCastReceiver(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighborhood);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LocalStorageAdapter db = new LocalStorageAdapter();
        nb = new ArrayList<Neighborhood>();
        nb.addAll(db.getNeighborhood(MyApp.getUsername()));
//        Log.d("Ibn", "nb size: "+nb.size());
        allLatLng = new ArrayList<String[]>();

        db.closeDB();

        drawOption = -1;
        ImageButton circle = (ImageButton) findViewById(R.id.circle);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Ibn", "draw circle");
                drawOption = 0;
            }
        });
        ImageButton point = (ImageButton) findViewById(R.id.point);
        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Ibn", "draw point");
                drawOption = 1;
            }
        });

        ImageButton line = (ImageButton) findViewById(R.id.line);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Ibn", "draw line");
                drawOption = 2;
            }
        });

        Button cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent createNewActivity = new Intent(view.getContext(), MainActivity.class);
//                startActivity(createNewActivity);
                finish();
            }
        });

        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalStorageAdapter db = new LocalStorageAdapter();
                String username = MyApp.getUsername();

                for (int i = 0; i < allLatLng.size(); i++) {
                    db.createNeighborhood(new Neighborhood(allLatLng.get(i)[0], allLatLng.get(i)[1], Integer.parseInt(allLatLng.get(i)[2]), username),true);
                }
                db.closeDB();
//                Intent createNewActivity = new Intent(view.getContext(), MainActivity.class);
//                startActivity(createNewActivity);
                finish();

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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(glasgow, 15.0f));

//        float zoom = mMap.getCameraPosition().zoom;

        for (int i = 0; i < nb.size(); i++) {
            if (nb.get(i).getDrawType() == 0) {
                int radius = getDistanceFromLatLonInKm(new LatLng(Double.parseDouble(nb.get(i).getInitialPoint().split(",")[0]), Double.parseDouble(nb.get(i).getInitialPoint().split(",")[1])), new LatLng(Double.parseDouble(nb.get(i).getFinalPoint().split(",")[0]), Double.parseDouble(nb.get(i).getFinalPoint().split(",")[1])));
                mMap.addCircle(new CircleOptions()
                        .center(midPoint(new LatLng(Double.parseDouble(nb.get(i).getInitialPoint().split(",")[0]), Double.parseDouble(nb.get(i).getInitialPoint().split(",")[1])), new LatLng(Double.parseDouble(nb.get(i).getFinalPoint().split(",")[0]), Double.parseDouble(nb.get(i).getFinalPoint().split(",")[1]))))
                        .radius(radius * 0.5)
                        .strokeColor(Color.RED));
            } else if (nb.get(i).getDrawType() == 1) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(nb.get(i).getFinalPoint().split(",")[0]), Double.parseDouble(nb.get(i).getFinalPoint().split(",")[1]))));

            } else if (nb.get(i).getDrawType() == 2) {
                mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(Double.parseDouble(nb.get(i).getFinalPoint().split(",")[0]), Double.parseDouble(nb.get(i).getFinalPoint().split(",")[1])), new LatLng(Double.parseDouble(nb.get(i).getInitialPoint().split(",")[0]), Double.parseDouble(nb.get(i).getInitialPoint().split(",")[1])))
                        .width(5)
                        .color(Color.RED));
            }
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                aLatLng = new String[3];
                aLatLng[0] = latLng.latitude + "," + latLng.longitude;
                if (mMap.getCameraPosition().zoom >= 15.0) {
                    clickedinitial = 1;
                    if (drawOption == 0) {
                        firstClick = latLng;
                    } else if (drawOption == 1) {
//                    mMap.addMarker(new MarkerOptions().position(latLng));

                    } else if (drawOption == 2) {
                        firstClick = latLng;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "zoom more", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                finalP = latLng;
                if (mMap.getCameraPosition().zoom >= 15.0) {
                    if (firstClick == null && drawOption != 1 && drawOption != -1) {
                        Toast.makeText(getApplicationContext(), "Please click the initial point first", Toast.LENGTH_SHORT).show();
                    } else if (drawOption == 0) {
                        aLatLng[1] = latLng.latitude + "," + latLng.longitude;

                        int radius = getDistanceFromLatLonInKm(firstClick, latLng);
                        mMap.addCircle(new CircleOptions()
                                .center(midPoint(firstClick, latLng))
                                .radius(radius * 0.5)
                                .strokeColor(Color.RED));
                        clickedinitial = 0;
                        aLatLng[2] = Integer.toString(drawOption);
                        allLatLng.add(aLatLng);
                        firstClick = null;
                    } else if (drawOption == 1) {
                        aLatLng = new String[3];
                        aLatLng[1] = latLng.latitude + "," + latLng.longitude;
                        aLatLng[0] = "0,0";
                        aLatLng[1] = latLng.latitude + "," + latLng.longitude;
                        mMap.addMarker(new MarkerOptions().position(latLng));
                        aLatLng[2] = Integer.toString(drawOption);
                        allLatLng.add(aLatLng);
                    } else if (drawOption == 2) {
                        aLatLng[1] = latLng.latitude + "," + latLng.longitude;

                        mMap.addPolyline(new PolylineOptions()
                                .add(latLng, firstClick)
                                .width(5)
                                .color(Color.RED));
                        clickedinitial = 0;
                        firstClick = null;
                        aLatLng[2] = Integer.toString(drawOption);
                        allLatLng.add(aLatLng);
                    } else if (clickedinitial == 0) {
                        Toast.makeText(getApplicationContext(), "Please click the initial point first", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please select tool on the right first", Toast.LENGTH_SHORT).show();

                    }
//                    Log.d("Ibn", allLatLng.size() + "");
                } else {
                    Toast.makeText(getApplicationContext(), "zoom more", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public int getDistanceFromLatLonInKm(LatLng ll1, LatLng ll2) {
        int R = 6371; // Radius of the earth in m
        double dLat = deg2rad(ll2.latitude - ll1.latitude);  // deg2rad below
        double dLon = deg2rad(ll2.longitude - ll1.longitude);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(ll1.latitude)) * Math.cos(deg2rad(ll2.latitude)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c * 1000; // Distance in m
        return (int) d;
    }

    public double deg2rad(Double deg) {
        return deg * (Math.PI / 180);
    }

    public LatLng midPoint(LatLng ll1, LatLng ll2) {
        double lat1 = ll1.latitude;
        double lon1 = ll1.longitude;
        double lat2 = ll2.latitude;
        double lon2 = ll2.longitude;

        double dLon = Math.toRadians(lon2 - lon1);

        //convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

        //print out in degrees
//        System.out.println(Math.toDegrees(lat3) + " " + Math.toDegrees(lon3));
        return new LatLng(Math.toDegrees(lat3), Math.toDegrees(lon3));
    }
}

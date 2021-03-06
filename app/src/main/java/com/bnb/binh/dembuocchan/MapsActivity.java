package com.bnb.binh.dembuocchan;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private TextView tvTime, tvKcal, tvBuoc;
    private TextView tvTest;
    private TextView timeCountTv;
    private TextView kcalCount;
    private TextView kmCount;
    private Timer timer;
    private Timer timer2;
    private TimerTask timerTask;
    private TimerTask timerTask2;
    private long timeCout = 0;
    private LocationManager mLocationManager;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationRequest mLocationRequest;
    private LocationListener mLocationListener;
    private long UPDATE_INTERVAL = 2000;
    private long FASTEST_INTERVAL = 2000;
    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;
    private ImageButton btnLocation;
    private ImageButton btnChange;
    private ImageButton btnDoi;
    private ImageButton btnCamera;
    private RelativeLayout layoutMap;
    private LinearLayout layoutRun;
    private boolean isChange = false;
    private Polyline polyline = null;
    private List<LatLng> latLngList = new ArrayList<>();
    private List<LatLng> latLngList2 = new ArrayList<>();
    private List<Marker> markerList = new ArrayList<>();
    private double coutCalculate = 0;
    private ImageButton btnPause;
    private Integer stepCount = -1;
    private double Magni = 0;
    private SensorManager sensorManager;
    private Sensor sensor;
    private long timeCheck = 0;
    private long timeCheck2 = 0;
    public static int checkLayout = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        if (requesrSinglePermission()) {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            assert mapFragment != null;
            mapFragment.getMapAsync(this);


            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            checkLocation();
        }

        init();
        initEvent();
        test();

        if (checkLayout == 0) {
            layoutRun.setVisibility(View.VISIBLE);
            layoutMap.setVisibility(View.GONE);
        } else {
            layoutRun.setVisibility(View.GONE);
            layoutMap.setVisibility(View.VISIBLE);
        }
    }

    private void setTimer() {
        timer2 = new Timer();
        timerTask2 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeCheck = timeCheck + 1000;
                    }
                });
            }
        };
        timer2.scheduleAtFixedRate(timerTask2, 0, 1000);
    }

    private void test() {

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        setTimer();
        SensorEventListener eventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event != null) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];
                    double magnitude = Math.sqrt(x * x + y * y + z * z);
                    double magnitudeDefault = magnitude - Magni;
                    Magni = magnitude;

                    if (magnitudeDefault > 6) {
                        if (timeCheck - 500 > timeCheck2) {
                            timeCheck2 = timeCheck;
                            stepCount++;
                            double km = ((double) stepCount * 0.69) / 1000;
                            DecimalFormat format = new DecimalFormat("0.##");
                            kmCount.setText(String.valueOf(format.format(km)));

                        }
                    }
                    if (stepCount >= 0) {
                        tvTest.setText(stepCount.toString());
                        tvBuoc.setText(stepCount.toString());
                    }

                    coutKcal(stepCount, timeCout);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(eventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    private boolean checkLocation() {
        if (!isLocationEnabled()) {
            showAlert();
        }
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'. \nPlease Enable Location to " + "Đếm bước chân")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Canel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean requesrSinglePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            isPermission = false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        return isPermission;
    }

    private void init() {
        tvTime = findViewById(R.id.tvTime);
        tvKcal = findViewById(R.id.tvKcal);
        tvBuoc = findViewById(R.id.tvBuoc);
        btnLocation = findViewById(R.id.btnLocation);
        btnChange = findViewById(R.id.btnChange);
        btnPause = findViewById(R.id.btnPause);
        tvTest = findViewById(R.id.tvTest);
        kmCount = findViewById(R.id.kmCount);
        timeCountTv = findViewById(R.id.timeCout);
        kcalCount = findViewById(R.id.kcalCount);
        btnDoi = findViewById(R.id.btnDoi);
        btnCamera = findViewById(R.id.btnCamera);
        layoutRun = findViewById(R.id.layoutRun);
        layoutMap = findViewById(R.id.layoutMap);
    }

    private void initEvent() {
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //timerTask.cancel();
                //alertDiaglog2();
            }
        });
        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLayout == 0) {
                    btnDoi.setImageResource(R.drawable.ic_clear_black_50dp);
                    layoutRun.setVisibility(View.GONE);
                    layoutMap.setVisibility(View.VISIBLE);
                    checkLayout = 1;
                } else {
                    btnDoi.setImageResource(R.drawable.ic_pin_drop_black_50dp);
                    layoutRun.setVisibility(View.VISIBLE);
                    layoutMap.setVisibility(View.GONE);
                    checkLayout = 0;
                }
            }
        });

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeCout = timeCout + 1000;
                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                        cal.setTimeInMillis(Long.parseLong(String.valueOf(timeCout)));
                        String dateTime = DateFormat.format("mm:ss", cal).toString();
                        tvTime.setText(dateTime);
                        timeCountTv.setText(dateTime);

                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void coutKcal(int a, long b) {


        double calculation = a * 0.69;      //tính bằng m
        long time = b / 1000;               // tính bằng s
        double v = calculation / time;      //m/s
        if (v <= 5 * 0.27777778) {
            double kcal = ((double) 200 / 5000) * calculation;
            Log.d("RP", "coutKcal: " + kcal);
            DecimalFormat format = new DecimalFormat("0.##");
            if (kcal>=0){
                tvKcal.setText(String.valueOf(format.format(kcal)));
                kcalCount.setText(String.valueOf(format.format(kcal)));
            }
        } else if (v > 5 * 0.27777778 && v < 8 * 0.27777778) {
            double kcal = ((double) 370 / 5000) * calculation;
            DecimalFormat format = new DecimalFormat("0.##");
            Log.d("RP", "coutKcal: " + kcal);
            if (kcal>=0){
                tvKcal.setText(String.valueOf(format.format(kcal)));
                kcalCount.setText(String.valueOf(format.format(kcal)));
            }
        } else {
            double kcal = ((double) 500 / 5000) * calculation;
            DecimalFormat format = new DecimalFormat("0.##");
            Log.d("RP", "coutKcal: " + kcal);

            if (kcal>=0){
                tvKcal.setText(String.valueOf(format.format(kcal)));
                kcalCount.setText(String.valueOf(format.format(kcal)));
            }
        }
    }

    private void startLocationUpdate() {

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("KQ", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    private void alertDiaglog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.item_dialog_test, null);
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnNO = view.findViewById(R.id.btnNo);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btnNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
    }

    private void alertDiaglog2() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.item_dialog2, null);
        Button btnYes = view.findViewById(R.id.btnYes2);
        Button btnNO = view.findViewById(R.id.btnNo2);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btnNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                alertDialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (latLngList.size() >= 2) {
            LatLng lng = latLngList.get(latLngList.size() - 1);
            for (int i = 0; i < latLngList.size() - 1; i++) {
                double KQ = CalculationByDistance(latLngList.get(i), latLngList.get(i + 1));
                coutCalculate = coutCalculate + KQ;

            }

            Toast.makeText(this, "" + coutCalculate, Toast.LENGTH_SHORT).show();

            Log.d("SIZE", "onMapReady: " + String.valueOf((int) (coutCalculate * 1000 * 2)));
            latLngList.clear();
            latLngList.add(lng);

        }

        if (latLng != null) {

            if (markerList.size() > 0) {
                for (Marker marker : markerList) {
                    marker.remove();
                }
                markerList.clear();
            }
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Đây là vị trí hiện tại");
            Marker marker = mMap.addMarker(markerOptions);
            markerList.add(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

        }
        if (isChange) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latLng != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                }
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChange) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    isChange = true;
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    isChange = false;
                }
            }
        });

        if (polyline != null) {
            polyline.remove();
        }
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(latLngList2)
                .clickable(true);
        polyline = mMap.addPolyline(polylineOptions);
        polyline.setColor(Color.RED);
        polyline.setWidth(15);


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startLocationUpdate();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation == null) {
            startLocationUpdate();
        } else {
            Toast.makeText(this, "Location not Detecled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " + Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        //Marker marker = mMap.addMarker(markerOptions);
        latLngList.add(latLng);
        latLngList2.add(latLng);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        alertDiaglog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alertDiaglog();
    }

    @Override
    public void onBackPressed() {
        alertDiaglog();
    }


}

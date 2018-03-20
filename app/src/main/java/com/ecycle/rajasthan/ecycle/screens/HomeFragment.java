package com.ecycle.rajasthan.ecycle.screens;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecycle.rajasthan.ecycle.R;
import com.ecycle.rajasthan.ecycle.databinding.FragmentHomeBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;
import com.kishan.askpermission.PermissionInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavdip on 3/20/18.
 */

public class HomeFragment extends Fragment implements PermissionCallback, ErrorCallback, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = HomeFragment.class.getName();
    private static final int REQUEST_PERMISSIONS = 20;
    private static final int ZOOM_LEVEL = 17;
    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;


    private FragmentHomeBinding mHomeBinding;
    private GoogleMap mGoogleMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastKnowLocation;
    private List<MarkerOptions> optionsList = new ArrayList<>();
    private List<LatLng> latlanList = new ArrayList<>();
    private boolean isInDetails;
    private PolygonOptions mPolygonOptions;

    public static HomeFragment getInstance(boolean value) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle mBundle = new Bundle();
        mBundle.putBoolean("details", value);
        homeFragment.setArguments(mBundle);
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extractBundle();
        preparePlacesList();
    }

    private void extractBundle() {
        if (getArguments().containsKey("details")) {
            if (getArguments().getBoolean("details")) {
                isInDetails = true;
            } else {
                isInDetails = false;
            }
        }
    }

    private void preparePlacesList() {
        MapsInitializer.initialize(getContext());
        if (!isInDetails) {
            //education
            optionsList.add(new MarkerOptions().icon(getDescriptor(R.drawable.ic_mortarboard)).position(new LatLng(26.879660, 75.812482)).title("Rajasthan university"));
            //place
            optionsList.add(new MarkerOptions().icon(getDescriptor(R.drawable.ic_store)).position(new LatLng(26.9115458, 75.8070547)).title("Hawa Mahal"));
            //goverment
            optionsList.add(new MarkerOptions().icon(getDescriptor(R.drawable.ic_govement)).position(new LatLng(26.9047202, 75.797173)).title("Department of Information Technology & Communication"));
            //palce
            optionsList.add(new MarkerOptions().icon(getDescriptor(R.drawable.ic_store)).position(new LatLng(26.9534306, 75.8110557)).title("Jal Mahal"));
            //place
            optionsList.add(new MarkerOptions().icon(getDescriptor(R.drawable.ic_store)).position(new LatLng(26.8945295, 75.8263396)).title("Raja Park"));
        } else {
            mPolygonOptions = new PolygonOptions();
            latlanList.add(new LatLng(26.891643, 75.814846));
            latlanList.add(new LatLng(26.891415, 75.814377));
            latlanList.add(new LatLng(26.884753, 75.812922));
            latlanList.add(new LatLng(26.880167, 75.819658));
            latlanList.add(new LatLng(26.880159, 75.819657));
            latlanList.add(new LatLng(26.880465, 75.821425));
            latlanList.add(new LatLng(26.882570, 75.824500));
            latlanList.add(new LatLng(26.884454, 75.825149));
            latlanList.add(new LatLng(26.885834, 75.826258));
            latlanList.add(new LatLng(26.885801, 75.826243));
            latlanList.add(new LatLng(26.886469, 75.827424));
            latlanList.add(new LatLng(26.889893, 75.819247));
            latlanList.add(new LatLng(26.890332, 75.817787));
            latlanList.add(new LatLng(26.891643, 75.814846));
            mPolygonOptions.strokeColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                    .fillColor(ContextCompat.getColor(getContext(), R.color.colorAqua))
                    .strokeWidth(4);
            mPolygonOptions.addAll(latlanList);
        }

    }

    private BitmapDescriptor getDescriptor(int resourceId) {
        return BitmapDescriptorFactory.fromResource(resourceId);
    }

    private void spreadPlaceOnMap() {
        if (!isInDetails) {
            for (MarkerOptions item : optionsList) {
                mGoogleMap.addMarker(item);
            }
        }
    }

    private void drawAPolygonOnMap() {
        boolean hasPoints = false;
        Double maxLat = null, minLat = null, minLon = null, maxLon = null;
        if (mGoogleMap != null) {
            mGoogleMap.addPolygon(mPolygonOptions);
        }

        if (mPolygonOptions != null && mPolygonOptions.getPoints() != null) {
            List<LatLng> pts = mPolygonOptions.getPoints();
            for (LatLng coordinate : pts) {
                // Find out the maximum and minimum latitudes & longitudes
                // Latitude
                maxLat = maxLat != null ? Math.max(coordinate.latitude, maxLat) : coordinate.latitude;
                minLat = minLat != null ? Math.min(coordinate.latitude, minLat) : coordinate.latitude;

                // Longitude
                maxLon = maxLon != null ? Math.max(coordinate.longitude, maxLon) : coordinate.longitude;
                minLon = minLon != null ? Math.min(coordinate.longitude, minLon) : coordinate.longitude;
                hasPoints = true;
            }
        }

        if (hasPoints) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(new LatLng(maxLat, maxLon));
            builder.include(new LatLng(minLat, minLon));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 48));
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return mHomeBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        askForPermission();
    }

    private void askForPermission() {
        new AskPermission.Builder(this)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .setCallback(this)
                .setErrorCallback(this)
                .request(REQUEST_PERMISSIONS);


    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        initGMaps();
        createGoogleApi();
    }

    @Override
    public void onPermissionsDenied(int requestCode) {

    }

    @Override
    public void onShowRationalDialog(PermissionInterface permissionInterface, int requestCode) {

    }

    @Override
    public void onShowSettings(PermissionInterface permissionInterface, int requestCode) {

    }

    private void initGMaps() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (checkPermission()) {
            mGoogleMap.setMyLocationEnabled(true);
        }
        mGoogleMap.setOnInfoWindowClickListener(this);
        spreadPlaceOnMap();
    }

    private void createGoogleApi() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Call GoogleApiClient connection when starting the Activity
        if (googleApiClient != null)
            googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null) {
            // Disconnect GoogleApiClient when stopping Activity
            googleApiClient.disconnect();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected()");
        getLastKnownLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed()");
    }

    // Check for permission to access Location
    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    // Asks for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                13
        );
    }

    // Get last known location
    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation()");
        if (lastKnowLocation != null && !isInDetails) {
            LatLng newLatLng = new LatLng(lastKnowLocation.getLatitude(), lastKnowLocation.getLongitude());
            // Position the map's camera at the location of the marker.
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng,
                    ZOOM_LEVEL));
        }

        if (checkPermission()) {
            Location nwLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (nwLocation != null && !isInDetails) {
                if (lastKnowLocation == null) {
                    lastKnowLocation = nwLocation;
                    LatLng newLatLng = new LatLng(lastKnowLocation.getLatitude(), lastKnowLocation.getLongitude());
                    // Position the map's camera at the location of the marker.
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng,
                            ZOOM_LEVEL));
                }
            } else {
                startLocationUpdates();
            }

        } else askPermission();

        if (isInDetails) {
            drawAPolygonOnMap();
        }

    }

    // Start location Updates
    private void startLocationUpdates() {
        Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (checkPermission())
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lastKnowLocation = location;
        if (mGoogleMap != null && !isInDetails) {
            LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            // Position the map's camera at the location of the marker.
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng,
                    ZOOM_LEVEL));
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        DetailsActivity.startDetailsScreen(getActivity());
    }
}

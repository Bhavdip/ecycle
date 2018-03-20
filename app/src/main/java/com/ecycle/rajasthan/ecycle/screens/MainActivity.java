package com.ecycle.rajasthan.ecycle.screens;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.Toast;

import com.ecycle.rajasthan.ecycle.R;
import com.ecycle.rajasthan.ecycle.databinding.ActivityMainBinding;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, ScanListener, BarcodeRetriever {

    private ActivityMainBinding mBinding;
    private boolean isScanCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        mBinding.bottomNavigation.setSelectedItemId(R.id.action_find_cycle);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan_cycle:
                setScanFragment();
                return true;
            case R.id.action_find_cycle:
                setFragment(HomeFragment.getInstance(false));
                return true;
            case R.id.action_profile:
                setFragment(new ProfileFragment());
                return true;
        }
        return true;
    }

    private void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onScanCompleted(String message) {
        isScanCompleted = true;
        setScanFragment();
    }

    @Override
    public void showScanScreen() {
        isScanCompleted = false;
        setScanFragment();
    }

    private void setScanFragment() {
        if (isScanCompleted) {
            setFragment(new BlankFragment());
        } else {
//            setFragment(new ScanFragment());
            BarcodeCapture mCapture = new BarcodeCapture();
            mCapture.setRetrieval(this);
            setFragment(mCapture);
        }
    }

    @Override
    public void onRetrieved(Barcode barcode) {
        isScanCompleted = true;
        setScanFragment();
    }

    @Override
    public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> barcode) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onRetrievedFailed(String reason) {
        Toast.makeText(this, reason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionRequestDenied() {

    }
}

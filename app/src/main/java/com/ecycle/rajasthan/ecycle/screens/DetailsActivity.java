package com.ecycle.rajasthan.ecycle.screens;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ecycle.rajasthan.ecycle.R;
import com.ecycle.rajasthan.ecycle.databinding.ActivityDetailsBinding;

/**
 * Created by bhavdip on 3/20/18.
 */

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding mDetailsBinding;

    public static void startDetailsScreen(Activity activity) {
        Intent detailIntent = new Intent(activity, DetailsActivity.class);
        activity.startActivity(detailIntent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        loadDetailFragment();
    }

    private void loadDetailFragment() {
        setFragment(HomeFragment.getInstance(true));
    }

    private void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}

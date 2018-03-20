package com.ecycle.rajasthan.ecycle.screens;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.ecycle.rajasthan.ecycle.R;
import com.ecycle.rajasthan.ecycle.databinding.ActivityRegBinding;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityRegBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_reg);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mBinding.bReg.setOnClickListener(this);
        mBinding.aReg.setOnClickListener(this);
        mBinding.fReg.setOnClickListener(this);
        mBinding.gReg.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bReg ||
                view.getId() == R.id.aReg ||
                view.getId() == R.id.fReg ||
                view.getId() == R.id.gReg){
            Intent intent = new Intent(RegActivity.this,MainActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }
}

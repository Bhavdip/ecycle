package com.ecycle.rajasthan.ecycle.screens;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ecycle.rajasthan.ecycle.R;
import com.ecycle.rajasthan.ecycle.screens.viewpagercards.CardItem;
import com.ecycle.rajasthan.ecycle.screens.viewpagercards.CardPagerAdapter;
import com.ecycle.rajasthan.ecycle.screens.viewpagercards.ShadowTransformer;

public class SubcriptionActivity extends AppCompatActivity  {

    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcription);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.title_education, R.string.text_edu));
        mCardAdapter.addCardItem(new CardItem(R.string.title_tourist, R.string.text_tourist));
        mCardAdapter.addCardItem(new CardItem(R.string.title_premium, R.string.text_pr));
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
//        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
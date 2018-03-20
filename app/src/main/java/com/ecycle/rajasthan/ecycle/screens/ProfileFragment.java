package com.ecycle.rajasthan.ecycle.screens;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecycle.rajasthan.ecycle.R;
import com.ecycle.rajasthan.ecycle.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    private FragmentProfileBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);
        mBinding.changeSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSubscriptionActivity();
            }
        });
        return mBinding.getRoot();
    }

    private void openSubscriptionActivity() {
        Intent intent = new Intent(getActivity(), SubcriptionActivity.class);
        startActivity(intent);
    }

}

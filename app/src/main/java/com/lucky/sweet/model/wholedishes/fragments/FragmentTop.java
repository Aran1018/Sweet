package com.lucky.sweet.model.wholedishes.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lucky.sweet.R;
import com.lucky.sweet.utility.OssUtils;

import java.io.InputStream;

public class FragmentTop extends Fragment {

    static final String ARG_TRAVEL = "ARG_TRAVEL";
    private String path;

    public FragmentTop() {
    }

    public static FragmentTop newInstance(String path) {
        Bundle args = new Bundle();
        Log.i(ARG_TRAVEL, path);
        FragmentTop fragmentTop = new FragmentTop();
        args.putString(ARG_TRAVEL, path);
        fragmentTop.setArguments(args);
        return fragmentTop;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        if (args != null) {
            path = (String) args.get(ARG_TRAVEL);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_front, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        final ImageView topImage = getView().findViewById(R.id.imv_itme_whole_dis);
        if (path != null) {

            Glide.with(getActivity()).load(path).into(topImage);

        }
    }
}

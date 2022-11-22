package com.tikay.homitest.presentation.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tikay.homitest.R;

import butterknife.ButterKnife;

public class AuthFragment extends Fragment {

    public AuthFragment(){
        super(R.layout.fragment_player);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }
}

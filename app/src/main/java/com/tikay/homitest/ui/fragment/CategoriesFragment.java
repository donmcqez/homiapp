package com.tikay.homitest.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tikay.homitest.R;

import butterknife.ButterKnife;

public class CategoriesFragment extends Fragment {
    private NavController navController;

    public CategoriesFragment(){
        super(R.layout.fragment_categories);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        this.navController = Navigation.findNavController(view);
    }
}

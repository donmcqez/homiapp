package com.tikay.homitest.features.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.tikay.homitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class DashBoardActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dash_board);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);



    }

    private void loadFragment(Fragment fragment, boolean isAddToBackStack, String name){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isAddToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(R.id.flHomeContainer,fragment);
        transaction.commit();
    }
}

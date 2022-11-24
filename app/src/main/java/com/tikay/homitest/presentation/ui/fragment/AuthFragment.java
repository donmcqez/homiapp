package com.tikay.homitest.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.presentation.utils.Utils;
import com.tikay.homitest.presentation.veiwmodel.MainViewModel;
import com.tikay.homitest.presentation.veiwmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class AuthFragment extends Fragment {
    private static final String TAG = AuthFragment.class.getSimpleName();
    private int seriesId = -1;
    private UserViewModel userViewModel;

    public AuthFragment() {
        super(R.layout.fragment_auth);
    }

    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.cvLogin)
    CardView cvLogin;
    @BindView(R.id.ivFaceBook)
    ImageView ivFaceBook;
    @BindView(R.id.ivGoogle)
    ImageView ivGoogle;
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;

    private NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        navController = Navigation.findNavController(view);

//        mediaPosition = SeasonFragmentArgs.fromBundle(getArguments());
        seriesId = getArguments() != null ? getArguments().getInt("seriesId") : 0;

        initViewModel();
    }

    private void initViewModel() {
        // I'm using requireActivity() in order to share the same viewModel with host activity
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @OnClick(R.id.cvLogin)
    public void login() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (!Utils.isEmailValid(email)) {
            etEmail.setError("invalid email");
            return;
        }
        if (!Utils.isPasswordValid(password)) {
            etPassword.setError("invalid password");
            return;
        }

        User user = userViewModel.login(email, password);
        if (user != null) {
            Log.e(TAG, "login: =========> User: " + user);

            if (user.isPremium() && seriesId >= 0) {
                AuthFragmentDirections.ActionLoginFragmentToPlayerFragment action =
                        AuthFragmentDirections.actionLoginFragmentToPlayerFragment();
                navController.navigate(action);
            }
//            else {
////                AccountFragmentDirections.actionAccountFragmentToSeriesFragment();
//                navController.popBackStack();
//            }

            navController.popBackStack();
        } else {
            Log.e(TAG, "login: =========> AUTHENTICATION FAILED");
            Log.e(TAG, "login: =========> User: " + user);
        }
    }


}

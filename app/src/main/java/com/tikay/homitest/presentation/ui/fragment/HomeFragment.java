package com.tikay.homitest.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tikay.homitest.R;
import com.tikay.homitest.presentation.adapters.MediaAdapter;
import com.tikay.homitest.presentation.veiwmodel.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_home)
    RecyclerView recyclerView;
    @BindView(R.id.sr_home)
    SwipeRefreshLayout srHome;

    private NavController navController;

    private MediaAdapter mediaAdapter;
    private MainViewModel mainViewModel;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        initViewModel();
        setUpRecyclerView();
        observeSeriesData();

        navController = Navigation.findNavController(view);
    }

    private void initViewModel() {
        // I'm using requireActivity() in order to share the same viewModel with host activity
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    private void setUpRecyclerView() {
        mediaAdapter = new MediaAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mediaAdapter);

        srHome.setOnRefreshListener(() -> srHome.post(() -> {
            srHome.setRefreshing(true);
            fetchData();
        }));

        mediaAdapter.setOnItemClickListener((v, media, position) -> {
//            HomeFragmentDirections.ActionHomeFragmentToSeasonFragment action =
//                    HomeFragmentDirections.actionHomeFragmentToSeasonFragment();
//
//            action.setSeasonId(position);
////            action.setSeasonId(media.getId());
//            navController.navigate(action);

            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            navController.navigate(R.id.seasonFragment, bundle);

        });
    }

    private void observeSeriesData() {
        srHome.setRefreshing(true);
        mainViewModel.getMediaData().observe(getViewLifecycleOwner(), mediaList -> {
            mediaAdapter.submitList(mediaList);
            srHome.setRefreshing(false);
        });
    }

    private void fetchData() {
        mainViewModel.loadMediaList();
    }

    @Override
    public void onRefresh() {
        srHome.postDelayed(() -> srHome.setRefreshing(false), 1000);
    }
}

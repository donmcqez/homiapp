package com.tikay.homitest.ui.fragment;

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
import com.tikay.homitest.ui.adapters.SeriesAdapter;
import com.tikay.homitest.ui.veiwmodel.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class SeriesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public SeriesFragment() {
        super(R.layout.fragment_series);
    }

    @BindView(R.id.rv_home)
    RecyclerView recyclerView;
    @BindView(R.id.sr_home)
    SwipeRefreshLayout srHome;

    private NavController navController;

    private SeriesAdapter seriesAdapter;
    private MainViewModel mainViewModel;

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
        seriesAdapter = new SeriesAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(seriesAdapter);

        srHome.setOnRefreshListener(() -> srHome.post(() -> {
            srHome.setRefreshing(true);
            fetchData();
        }));

        seriesAdapter.setOnItemClickListener((v, media, position) -> {
//            HomeFragmentDirections.ActionHomeFragmentToSeasonFragment action =
//                    HomeFragmentDirections.actionHomeFragmentToSeasonFragment();
//
//            action.setSeasonId(position);
////            action.setSeasonId(media.getId());
//            navController.navigate(action);

            Bundle bundle = new Bundle();
            bundle.putInt("seriesId", position);
            navController.navigate(R.id.seasonFragment, bundle);

        });
    }

    private void observeSeriesData() {
        srHome.setRefreshing(true);
        mainViewModel.observeSeriesData().observe(getViewLifecycleOwner(), mediaList -> {
            seriesAdapter.submitList(mediaList);
            srHome.setRefreshing(false);
        });
    }

    private void fetchData() {
        mainViewModel.updateSeriesState();
    }

    @Override
    public void onRefresh() {
        srHome.postDelayed(() -> srHome.setRefreshing(false), 1000);
    }
}

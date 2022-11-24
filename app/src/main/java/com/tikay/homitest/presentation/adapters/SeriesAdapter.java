package com.tikay.homitest.presentation.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Series;
import com.tikay.homitest.presentation.utils.Utils;
import com.tikay.homitest.presentation.utils.images.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeriesAdapter extends ListAdapter<Series, SeriesAdapter.SeriesViewHolder> {

    public SeriesAdapter() {
        super(DIFF_CALLBACK);
    }

    @Nullable
    private static OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.holder_home_video, parent, false);
                .inflate(R.layout.holder_series, parent, false);

//        view = itemView;
        return new SeriesViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        holder.bind(getItem(position), position);
    }


    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    public static final DiffUtil.ItemCallback<Series> DIFF_CALLBACK = new DiffUtil.ItemCallback<Series>() {
        @Override
        public boolean areItemsTheSame(@NonNull Series oldItem, @NonNull Series newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Series oldItem, @NonNull Series newItem) {
            return oldItem.equals(newItem);
        }
    };

    @SuppressLint("NonConstantResourceId")
    public static class SeriesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.ivBanner)
        ImageView ivBanner;

        private View itemView;

        public SeriesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void bind(Series series, int position) {

            tvTitle.setText(Utils.fromHtml(series.getTitle()));
            int margin = (int) Utils.dp2px(itemView.getContext(),8);
            ImageUtils.loadImage(
                    itemView.getContext(),
                    ivBanner,
                    series.getBanner()
            );

            tvDescription.setText(Utils.fromHtml(series.getCategory()));

            itemView.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    ViewCompat.setTransitionName(ivBanner, series.getTitle());
                    mOnItemClickListener.onItemClick(view, series, position);
                }
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Series series, int position);
    }
}

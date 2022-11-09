package com.tikay.homitest.features.dashboard;

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

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tikay.homitest.HomiApp;
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Media;
import com.tikay.homitest.features.home.utils.Utils;
import com.tikay.homitest.features.home.utils.images.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardAdapter extends ListAdapter<Media, DashBoardAdapter.DashBoardViewHolder> {

    public DashBoardAdapter() {
        super(DIFF_CALLBACK);
    }

    @Nullable
    private static OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public DashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_home_feed, parent, false);
//        view = itemView;
        return new DashBoardViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull DashBoardViewHolder holder, int position) {
        holder.bind(getItem(position), position);
    }


    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    public static final DiffUtil.ItemCallback<Media> DIFF_CALLBACK = new DiffUtil.ItemCallback<Media>() {
        @Override
        public boolean areItemsTheSame(@NonNull Media oldItem, @NonNull Media newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Media oldItem, @NonNull Media newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static class DashBoardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.ivBanner)
        ImageView ivBanner;

        private View itemView;

        public DashBoardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void bind(Media media, int position) {

            tvTitle.setText(Utils.fromHtml(media.getTitle()));

            ImageUtils.showImage(
                    HomiApp.self().getApplicationContext(),
                    ivBanner,
                    media.getBanner(),
                    new CenterCrop(),
                    new RoundedCorners(HomiApp.self().getMargin())
            );

            tvDescription.setText(Utils.fromHtml(media.getCategory()));

            itemView.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    ViewCompat.setTransitionName(ivBanner, media.getTitle());
                    mOnItemClickListener.onItemClick(view, media, position);
                }
            });

        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Media media, int position);
    }
}
















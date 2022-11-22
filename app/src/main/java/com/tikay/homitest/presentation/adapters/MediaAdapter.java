package com.tikay.homitest.presentation.adapters;

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
import com.tikay.homitest.presentation.utils.Utils;
import com.tikay.homitest.presentation.utils.images.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaAdapter extends ListAdapter<Media, MediaAdapter.MediaViewHolder> {

    public MediaAdapter() {
        super(DIFF_CALLBACK);
    }

    @Nullable
    private static OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public MediaAdapter.MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.holder_home_video, parent, false);
                .inflate(R.layout.row_home_feed, parent, false);

//        view = itemView;
        return new MediaViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MediaAdapter.MediaViewHolder holder, int position) {
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


    public static class MediaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.ivBanner)
        ImageView ivBanner;

        private View itemView;

        public MediaViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void bind(Media media, int position) {

            tvTitle.setText(Utils.fromHtml(media.getTitle()));
            int margin = (int) Utils.dp2px(itemView.getContext(),8);
            ImageUtils.showImage(
                    itemView.getContext(),
                    ivBanner,
                    media.getBanner(),
                    new CenterCrop(),
                    new RoundedCorners(margin)
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
















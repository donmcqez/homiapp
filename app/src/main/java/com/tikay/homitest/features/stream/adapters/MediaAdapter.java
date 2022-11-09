package com.tikay.homitest.features.stream.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tikay.homitest.HomiApp;
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Media;
import com.tikay.homitest.features.home.utils.Utils;
import com.tikay.homitest.features.home.utils.images.ImageUtils;

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
        @BindView(R.id.iv_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_channel_avatar)
        ImageView ivChannelAvatar;
        @BindView(R.id.tv_channel_title)
        TextView tvChannelTitle;
        @BindView(R.id.root_holder_home_video)
        ConstraintLayout rootHolderHomeVideo;

        private View itemView;

       public MediaViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            itemView = view;
        }

        private void bind(Media media,int position) {

            tvTitle.setText(Utils.fromHtml(media.getTitle()));

            ImageUtils.showImage(
                    HomiApp.self().getApplicationContext(),
                    ivThumb,
                    media.getBanner(),
                    new CenterCrop(),
                    new RoundedCorners(HomiApp.self().getMargin())
            );

//                tvChannelTitle.setText(Utils.fromHtml(HomiApp.self().getString(video.getChannel().getTitle())));
            tvChannelTitle.setText(Utils.fromHtml(media.getCategory()));

            ImageUtils.showImage(
                    HomiApp.self().getApplicationContext(),
                    ivChannelAvatar,
                    media.getBanner(),
                    new CenterCrop(),
                    new CircleCrop()
            );

            itemView.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    ViewCompat.setTransitionName(ivChannelAvatar, media.getTitle());
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
















package com.tikay.homitest.presentation.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tikay.homitest.HomiApp;
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Episode;
import com.tikay.homitest.domain.model.Media;
import com.tikay.homitest.presentation.utils.Utils;
import com.tikay.homitest.presentation.utils.images.ImageUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayListAdapter extends ListAdapter<Episode, PlayListAdapter.PlayListViewHolder> {
    private int selectedPos = RecyclerView.NO_POSITION;

    public PlayListAdapter() {
        super(DIFF_CALLBACK);
    }

    @Nullable
    private static OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_playlist, parent, false);
        return new PlayListViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder holder, int position) {
        holder.bind(getItem(position), position);
        holder.itemView.setSelected(selectedPos == position);
    }


    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }


    public class PlayListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvPublishedAt)
        TextView tvPublishedAt;
        @BindView(R.id.ivBanner)
        ImageView ivBanner;

        private View itemView;

        public PlayListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void bind(Episode episode, int position) {
            Context context = itemView.getContext();
            int margin = (int) Utils.dp2px(context, 8);
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                String publishedAt = convert(episode.getCreatedAt());
//                tvPublishedAt.setText(publishedAt);
//            }
            String publishedAt = Utils.getDateTimeFromString(episode.getCreatedAt());
            tvPublishedAt.setText(publishedAt);
            tvTitle.setText(Utils.fromHtml(episode.getTitle()));
            ImageUtils.showImage(
                    context,
                    ivBanner,
                    episode.getBanner(),
                    new CenterCrop(),
                    new RoundedCorners(margin)
            );
            tvDescription.setText(Utils.fromHtml(episode.getStatus()));
            itemView.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    ViewCompat.setTransitionName(ivBanner, episode.getTitle());
                    mOnItemClickListener.onItemClick(view, episode, position);

                    notifyItemChanged(selectedPos);
                    selectedPos = getBindingAdapterPosition();
                    notifyItemChanged(selectedPos);
                }
            });

        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Episode episode, int position);
    }

    public static final DiffUtil.ItemCallback<Episode> DIFF_CALLBACK = new DiffUtil.ItemCallback<Episode>() {
        @Override
        public boolean areItemsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
            return oldItem.equals(newItem);
        }
    };

}
















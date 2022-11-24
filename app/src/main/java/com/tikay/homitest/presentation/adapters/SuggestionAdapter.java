package com.tikay.homitest.presentation.adapters;

import android.content.Context;
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
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Suggestion;
import com.tikay.homitest.presentation.utils.Utils;
import com.tikay.homitest.presentation.utils.images.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuggestionAdapter extends ListAdapter<Suggestion, SuggestionAdapter.SuggestionViewHolder> {

    public SuggestionAdapter() {
        super(DIFF_CALLBACK);
    }
    private Suggestion suggestion;
    @Nullable
    private static OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_suggestion, parent, false);

//        view = itemView;
        return new SuggestionViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
        holder.bind(getItem(position), position);
    }


    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    public static final DiffUtil.ItemCallback<Suggestion> DIFF_CALLBACK = new DiffUtil.ItemCallback<Suggestion>() {
        @Override
        public boolean areItemsTheSame(@NonNull Suggestion oldItem, @NonNull Suggestion newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Suggestion oldItem, @NonNull Suggestion newItem) {
            return oldItem.equals(newItem);
        }
    };


    public static class SuggestionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.ivBanner)
        ImageView ivBanner;

        private View itemView;

        public SuggestionViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }

        private void bind(Suggestion suggestion, int position) {
            Context context = itemView.getContext();
            int margin = (int) Utils.dp2px(context,8);
            tvTitle.setText(Utils.fromHtml(suggestion.getTitle()));

            ImageUtils.loadImage(
                    context,
                    ivBanner,
                    suggestion.getBanner()
            );

            tvDescription.setText(Utils.fromHtml(suggestion.getCategory()));

            itemView.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    ViewCompat.setTransitionName(ivBanner, suggestion.getTitle());
                    mOnItemClickListener.onItemClick(view, suggestion, position);
                }
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Suggestion suggestion, int position);
    }
}

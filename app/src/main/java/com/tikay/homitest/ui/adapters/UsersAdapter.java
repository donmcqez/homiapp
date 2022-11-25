package com.tikay.homitest.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Series;
import com.tikay.homitest.utils.Utils;
import com.tikay.homitest.utils.images.ImageUtils;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
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

        UsersViewHolder(View view) {
            super(view);
            itemView = view;
        }

        private void bind(Series series) {
            Context context = itemView.getContext();
            int margin = (int) Utils.dp2px(context,8);

            tvTitle.setText(Utils.fromHtml(series.getTitle()));
            ImageUtils.loadImage(
                    context,
                    ivThumb,
                    R.drawable.homi_placeholder_lans,
                    new CenterCrop(),
                    new RoundedCorners(margin)
            );
            tvChannelTitle.setText(Utils.fromHtml(series.getTitle()));
            ImageUtils.loadImage(
                    context,
                    ivChannelAvatar,
                    R.drawable.homi_placeholder_lans,
                    new CenterCrop(),
                    new CircleCrop()
            );


        }
    }

    private Context context;
    private ArrayList<Series> seriesArrayList;
    private ArrayList<Series> list = null;
    private ArrayList<Series> tempList;
    private String TAG = UsersAdapter.class.getSimpleName();
    private int previousPosition = 0;
    private String mName, mPhone, mEmail, user_id, id;
    private View view;


    public UsersAdapter(Context context, ArrayList<Series> classList) {
        this.context = context;
        this.seriesArrayList = classList;
        this.list = classList;
        this.tempList = new ArrayList<>();
        this.tempList.addAll(classList);
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_home_video, parent, false);
        view = itemView;
        return new UsersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder holder, int position) {
        Series user = seriesArrayList.get(position);
        holder.bind(user);

        holder.itemView.setOnClickListener(v -> {
            Log.d(TAG, "onBindViewHolder: ITEM CLICKED ===============>");
        });
    }


    @Override
    public int getItemCount() {
        return seriesArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    private Series getItem(int position){
        return seriesArrayList.get(position);
    }

    // Filter method
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        seriesArrayList.clear();
        if (charText.length() == 0) {
            seriesArrayList.addAll(tempList);
        } else {
            for (Series series : tempList) {
                if (series.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(series);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setMediaList(ArrayList<Series> seriesList) {
        this.seriesArrayList = seriesList;
        notifyDataSetChanged();
    }

    interface OnItemClickListener {
        void onItemClick(View view, Series series, int pos);
    }
}
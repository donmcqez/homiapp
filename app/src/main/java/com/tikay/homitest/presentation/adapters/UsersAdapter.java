package com.tikay.homitest.presentation.adapters;

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
import com.tikay.homitest.HomiApp;
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Media;
import com.tikay.homitest.presentation.utils.Utils;
import com.tikay.homitest.presentation.utils.images.ImageUtils;

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

        private void bind(Media media) {
//            setName(user.getFull_name());
//            setStatus(user.getStatus());
//            setImage(user.getThumb_image());
//
//            if (user.getOnline().equals(Config.ONLINE)) {
//                ivOnline.setVisibility(View.VISIBLE);
//            } else {
//                ivOnline.setVisibility(View.GONE);
//            }


            tvTitle.setText(Utils.fromHtml(media.getTitle()));
            ImageUtils.showImage(
                    HomiApp.self().getApplicationContext(),
                    ivThumb,
                    R.drawable.test_0,
                    new CenterCrop(),
                    new RoundedCorners(HomiApp.self().getMargin())
            );

//                tvChannelTitle.setText(Utils.fromHtml(HomiApp.self().getString(video.getChannel().getTitle())));
            tvChannelTitle.setText(Utils.fromHtml(media.getTitle()));

            ImageUtils.showImage(
                    HomiApp.self().getApplicationContext(),
                    ivChannelAvatar,
                    R.drawable.test_8,
                    new CenterCrop(),
                    new CircleCrop()
            );


        }
    }

    private Context context;
    private ArrayList<Media> mediaArrayList;
    private ArrayList<Media> list = null;
    private ArrayList<Media> tempList;
    private String TAG = UsersAdapter.class.getSimpleName();
    private int previousPosition = 0;
    private String mName, mPhone, mEmail, user_id, id;
    private View view;


    public UsersAdapter(Context context, ArrayList<Media> classList) {
        this.context = context;
        this.mediaArrayList = classList;
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
        Media user = mediaArrayList.get(position);
        holder.bind(user);

        holder.itemView.setOnClickListener(v -> {
            Log.d(TAG, "onBindViewHolder: ITEM CLICKED ===============>");
            // get the user unique key
//            int userId = user.getId();
//            Intent intent = new Intent(context, ChatActivity.class);
//            intent.putExtra(Config.USER_ID, userId);
//            intent.putExtra("name", user.getFull_name());
//            intent.putExtra(Config.LAST_SEEN, user.getLast_seen());
//
//
//            View sharedView = holder.civThumbnail;
//            String transitionName = context.getString(R.string.transition_poster);
//            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation((AppCompatActivity)context, sharedView, transitionName);
//            context.startActivity(intent, activityOptions.toBundle());
//            ((AppCompatActivity) context).overridePendingTransition(0,0);
        });
    }


    @Override
    public int getItemCount() {
        return mediaArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    private Media getItem(int position){
        return mediaArrayList.get(position);
    }

    // Filter method
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mediaArrayList.clear();
        if (charText.length() == 0) {
            mediaArrayList.addAll(tempList);
        } else {
            for (Media media : tempList) {
                if (media.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(media);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setMediaList(ArrayList<Media> mediaList) {
        this.mediaArrayList = mediaList;
        notifyDataSetChanged();
    }

    interface OnItemClickListener {
        void onItemClick(View view, Media media, int pos);
    }
}



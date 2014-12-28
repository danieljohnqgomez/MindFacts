package com.danielgomez.mindfacts.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielgomez.mindfacts.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import twitter4j.Status;

public class FeedAdapter extends BaseAdapter {

    Activity activity;
    List<Status> statuses;

    public FeedAdapter(Activity activity, List<Status> statuses) {
        this.activity = activity;
        this.statuses = statuses;
    }

    @Override
    public int getCount() {
        return statuses.size();
    }

    @Override
    public Object getItem(int i) {
        return statuses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = view;
        ViewHolder holder = null;

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_feed_list_item, viewGroup, false);
            holder = new ViewHolder();
            holder.name = (TextView) v.findViewById(R.id.name);
            holder.date = (TextView) v.findViewById(R.id.date);
            holder.statusText = (TextView) v.findViewById(R.id.status);
            holder.icon = (ImageView) v.findViewById(R.id.image);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Status status = (Status) getItem(i);

        holder.name.setText("@" + status.getUser().getScreenName());
        String formattedDate = new SimpleDateFormat("MMM dd yyyy").format(status.getCreatedAt());
        holder.date.setText(formattedDate);
        holder.statusText.setText(status.getText());
        Picasso.with(activity).setIndicatorsEnabled(true);
        Picasso.with(activity).load(status.getUser().getBiggerProfileImageURL()).into(holder.icon);
        return v;
    }

    static class ViewHolder {
        TextView name;
        TextView date;
        TextView statusText;
        ImageView icon;
    }
}

package com.example.simplelauncher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder>
{
    //    private List appList;
    private List<AppInfo> appList;


    public AppAdapter(List<AppInfo> appList)
    {
        this.appList = appList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        AppInfo app = appList.get(position);
        holder.iconView.setImageDrawable(app.getAppIcon());
        holder.labelView.setText(app.getAppName());
    }

    @Override
    public int getItemCount()
    {
        return appList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView iconView;
        public TextView labelView;

        public ViewHolder(View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.icon);
            labelView = itemView.findViewById(R.id.label);
        }
    }
}
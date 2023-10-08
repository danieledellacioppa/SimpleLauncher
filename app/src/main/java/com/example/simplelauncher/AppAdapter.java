package com.example.simplelauncher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
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

    private Context context;

    public AppAdapter(List<AppInfo> appList, Context context)
    {
        this.appList = appList;
        this.context = context;
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

        //here we set the click listener to launch the app
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(app.getPackageName());
                if (launchIntent != null)
                {
                    context.startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });

        // Imposta il long click listener
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Qui puoi lanciare l'activity delle impostazioni dell'app
                // Ad esempio, puoi creare un Intent per l'Activity delle impostazioni
                // e lanciarlo con startActivity()

                // Per esempio, puoi lanciare l'activity delle impostazioni dell'app con:
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + app.getPackageName()));
                context.startActivity(intent);
                return true; // Indica che l'evento di long click è stato gestito
            }
        });
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
package com.example.simplelauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AppDrawerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppAdapter adapter;
    private static List<AppInfo> appInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_drawer);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));

        Intent intent = getIntent();
        boolean update = intent.getBooleanExtra("update",false);

        if(update){
            appInfoList = getAppInfoList();
        }

        adapter = new AppAdapter(appInfoList,this);
        recyclerView.setAdapter(adapter);

    }


private List getAppInfoList()
{
    List appList = new ArrayList<>();
    Intent intent = new Intent(Intent.ACTION_MAIN, null);
    intent.addCategory(Intent.CATEGORY_LAUNCHER);
    PackageManager packageManager = getPackageManager();
    try
    {
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfoList)
        {
            AppInfo appInfo = new AppInfo();
            CharSequence label = resolveInfo.loadLabel(packageManager);
            if (label != null)
            {
                appInfo.setAppName(label.toString());
            }
            appInfo.setPackageName(resolveInfo.activityInfo.packageName);
            Drawable icon = resolveInfo.loadIcon(packageManager);
            if (icon != null)
            {
                appInfo.setAppIcon(icon);
            }
            appList.add(appInfo);
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    finally
    {
        if (packageManager != null)
        {
            packageManager = null;
        }
    }
    return appList;
}

}
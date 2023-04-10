package com.example.simplelauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AppDrawerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppAdapter adapter;
    private List<AppInfo> appInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_drawer);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));

        appInfoList = getAppInfoList();
        adapter = new AppAdapter(appInfoList,this);
        recyclerView.setAdapter(adapter);

//        int spanCount = 3; // number of columns in the grid
//        int spacing = 50; // spacing between items in pixels
//        boolean includeEdge = true; // include edge spacing
//
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
    }

//    private List getAppInfoList() {
//        List<AppInfo> appList = new ArrayList<>();
//        PackageManager packageManager = getPackageManager();
//        Intent intent = new Intent(Intent.ACTION_MAIN, null);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        List resolveInfoList = packageManager.queryIntentActivities(intent, 0);
//
//        IntStream.range(0, resolveInfoList.size()).forEach(i -> {
//            AppInfo appInfo = new AppInfo();
//            appInfo.setAppName(((ResolveInfo) resolveInfoList.get(i)).loadLabel(packageManager).toString());
//            appInfo.setPackageName(((ResolveInfo) resolveInfoList.get(i)).activityInfo.packageName);
//            appInfo.setAppIcon(((ResolveInfo) resolveInfoList.get(i)).loadIcon(packageManager));
//            appList.add(appInfo);
//        });
//        return appList;
//    }
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
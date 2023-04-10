package com.example.simplelauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AppDrawerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppAdapter adapter;
    private List<AppInfo> appInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_drawer);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appInfoList = getAppInfoList();
        adapter = new AppAdapter(appInfoList);
        recyclerView.setAdapter(adapter);
    }

    private List getAppInfoList() {
        List<AppInfo> appList = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List resolveInfoList = packageManager.queryIntentActivities(intent, 0);
//        for (ResolveInfo resolveInfo : resolveInfoList) {
//            AppInfo appInfo = new AppInfo();
//            appInfo.label = resolveInfo.loadLabel(packageManager).toString();
//            appInfo.packageName = resolveInfo.activityInfo.packageName;
//            appInfo.icon = resolveInfo.loadIcon(packageManager);
//            appList.add(appInfo);
//        }
        for (int i = 0; i < resolveInfoList.size(); i++) {
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(((ResolveInfo) resolveInfoList.get(i)).loadLabel(packageManager).toString());
            appInfo.setPackageName(((ResolveInfo) resolveInfoList.get(i)).activityInfo.packageName);
            appInfo.setAppIcon(((ResolveInfo) resolveInfoList.get(i)).loadIcon(packageManager));
            appList.add(appInfo);
        }
        return appList;
    }
}
package com.example.jbtang.agi_4buffer.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jbtang.agi_4buffer.R;

public class WelcomeActivity extends Activity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }
    private void init(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        loginFragment = new LoginFragment();
        fragmentTransaction.add(R.id.fragmentPager,loginFragment).commit();

        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
            versionNumber.setText("Version " + pi.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

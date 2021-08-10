package com.rapidplus.shop.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.rapidplus.shop.R;
import com.rapidplus.shop.fragment.DishesFragment;
import com.rapidplus.shop.fragment.HomeFragment;
import com.rapidplus.shop.fragment.RevenueFragment;
import com.rapidplus.shop.fragment.SettingFragment;
import com.rapidplus.shop.helper.ConnectionHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.main_container)
    FrameLayout mainContainer;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;
    FragmentTransaction transaction;
    private ConnectionHelper connectionHelper;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.empty_string, R.drawable.salver, R.color.grey);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.empty_string, R.drawable.cash, R.color.grey);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.empty_string, R.drawable.options, R.color.grey);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.empty_string, R.drawable.shop, R.color.grey);
        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        // Set background color
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.background_color));

        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        // Change colors
        bottomNavigation.setAccentColor(getResources().getColor(R.color.bottomSheetInAccentColor));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.bottomSheetInActiveColor));


        // Set current item programmatically
        fragment = new HomeFragment();
        transaction.add(R.id.main_container, fragment).commit();
        bottomNavigation.setCurrentItem(0);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position) {
                    case 0:
                        fragment = new HomeFragment();
                        break;
                    case 1:
                        fragment = new RevenueFragment();
                        break;
                    case 2:
                        fragment = new DishesFragment();
                        break;
                    case 3:
                        fragment = new SettingFragment();
                        break;
                }

                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });

    }
}

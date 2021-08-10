package com.rapidplus.shop.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rapidplus.shop.R;
import com.rapidplus.shop.adapter.ViewPagerAdapter;
import com.rapidplus.shop.fragment.CancelOrderFragment;
import com.rapidplus.shop.fragment.PastVisitFragment;
import com.rapidplus.shop.fragment.UpcomingVisitFragment;
import com.rapidplus.shop.helper.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends AppCompatActivity {

    public static CustomDialog customDialog;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    public static void showDialog() {
        if (customDialog != null && !customDialog.isShowing()) {
            customDialog.setCancelable(false);
            customDialog.show();
        }
    }

    public static void dismissDialog() {
        if (customDialog != null & customDialog.isShowing())
            customDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        backImg.setVisibility(View.VISIBLE);
        title.setText(getResources().getString(R.string.history));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpcomingVisitFragment(), getString(R.string.ongoing));
        adapter.addFragment(new PastVisitFragment(), getString(R.string.past));
        adapter.addFragment(new CancelOrderFragment(), getString(R.string.cancelled));
        viewPager.setAdapter(adapter);
        //set ViewPager
        tabLayout.setupWithViewPager(viewPager);
        changeTabsFont();
        viewPager.setOffscreenPageLimit(3);
        customDialog = new CustomDialog(this);
    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    Typeface custom_font = ResourcesCompat.getFont(getApplicationContext(), R.font.nunito_semi_bold);
                    ((TextView) tabViewChild).setTypeface(custom_font);
                }
            }
        }
    }

    @OnClick(R.id.back_img)
    public void onViewClicked() {
        finish();
    }
}

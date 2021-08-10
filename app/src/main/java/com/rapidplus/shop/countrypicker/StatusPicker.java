package com.rapidplus.shop.countrypicker;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rapidplus.shop.R;
import com.rapidplus.shop.activity.EditRestaurantActivity;
import com.rapidplus.shop.activity.RegisterActivity;

public class StatusPicker extends DialogFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.status_picker, null);

        view.findViewById(R.id.tvActive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (getActivity() instanceof RegisterActivity)
                    ((RegisterActivity) getActivity()).bindStatus(getContext().getText(R.string.status_active));
                else if (getActivity() instanceof EditRestaurantActivity)
                    ((EditRestaurantActivity) getActivity()).bindStatus(getContext().getText(R.string.status_active));
            }
        });

        view.findViewById(R.id.tvOnBoarding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (getActivity() instanceof RegisterActivity)
                    ((RegisterActivity) getActivity()).bindStatus(getContext().getText(R.string.status_onboarding));
                else if (getActivity() instanceof EditRestaurantActivity)
                    ((EditRestaurantActivity) getActivity()).bindStatus(getContext().getText(R.string.status_onboarding));
            }
        });

        view.findViewById(R.id.tvBanned).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (getActivity() instanceof RegisterActivity)
                    ((RegisterActivity) getActivity()).bindStatus(getContext().getText(R.string.status_banned));
                else if (getActivity() instanceof EditRestaurantActivity)
                    ((EditRestaurantActivity) getActivity()).bindStatus(getContext().getText(R.string.status_banned));
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.CENTER);
        }
    }
}

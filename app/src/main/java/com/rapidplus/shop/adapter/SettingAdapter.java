package com.rapidplus.shop.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.R;
import com.rapidplus.shop.activity.ChangePasswordActivity;
import com.rapidplus.shop.activity.DeliveriesActivity;
import com.rapidplus.shop.activity.EditRestaurantActivity;
import com.rapidplus.shop.activity.HistoryActivity;
import com.rapidplus.shop.activity.HomeActivity;
import com.rapidplus.shop.activity.LoginActivity;
import com.rapidplus.shop.activity.RestaurantTimingActivity;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.helper.GlobalData;
import com.rapidplus.shop.helper.SharedHelper;
import com.rapidplus.shop.model.ServerError;
import com.rapidplus.shop.model.Setting;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Constants;
import com.rapidplus.shop.utils.LocaleUtils;
import com.rapidplus.shop.utils.Utils;

import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.MyViewHolder> {
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    CustomDialog customDialog;
    ConnectionHelper connectionHelper;
    private List<Setting> list;
    private Context context;
    private Activity activity;


    public SettingAdapter(List<Setting> list, Context con, Activity activity) {
        this.list = list;
        this.context = con;
        this.activity = activity;

        customDialog = new CustomDialog(context);
        connectionHelper = new ConnectionHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Setting setting = list.get(position);
        holder.title.setText(setting.getTitle());
        holder.icon.setImageResource(setting.getIcon());
        holder.llMain.setTag(position);
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                Setting data = list.get(pos);
                String title = data.getTitle();
                redirectPage(title);
            }
        });
    }

    private void redirectPage(String title) {
        if (title.equalsIgnoreCase(context.getString(R.string.history))) {
            context.startActivity(new Intent(context, HistoryActivity.class));
        } else if (title.equalsIgnoreCase(context.getString(R.string.edit_restaurant))) {
            context.startActivity(new Intent(context, EditRestaurantActivity.class));
        } else if (title.equalsIgnoreCase(context.getString(R.string.edit_timing))) {
            Intent intent = new Intent(context, RestaurantTimingActivity.class);
            intent.putExtra("from", "Settings");
            context.startActivity(intent);
        } else if (title.equalsIgnoreCase(context.getString(R.string.deliveries))) {
            context.startActivity(new Intent(context, DeliveriesActivity.class));
        } else if (title.equalsIgnoreCase(context.getString(R.string.change_language))) {
            changeLanguage();
        } else if (title.equalsIgnoreCase(context.getString(R.string.change_password))) {
            context.startActivity(new Intent(context, ChangePasswordActivity.class));
        } else if (title.equalsIgnoreCase(context.getString(R.string.logout))) {
            showLogoutAlertDialog();
        } else if (title.equalsIgnoreCase(context.getString(R.string.delete_account))) {
            showDeleteAccountAlertDialog();
        }
    }

    private void changeLanguage() {
        List<String> languages = Arrays.asList(context.getResources().getStringArray(R.array.languages));
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);
        View convertView = (View) LayoutInflater.from(context).inflate(R.layout.language_dialog, null);
        alertDialog.setView(convertView);
        alertDialog.setCancelable(true);
        alertDialog.setTitle(context.getResources().getString(R.string.change_language));
        final android.app.AlertDialog alert = alertDialog.create();

        final ListView lv = (ListView) convertView.findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_single_choice, languages);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String item = lv.getItemAtPosition(position).toString();
                setLanguage(item);
                alert.dismiss();
            }
        });
        alert.show();
    }


    private void setLanguage(String value) {
        SharedHelper.putKey(context, "language", value);
        Log.d("11111111111111", "Selected Langauge::::" + value);
        switch (value) {
            case "English":
            case "Inglés":
                LocaleUtils.setLocale(context, "en");
                break;
            case "Spanish":
                LocaleUtils.setLocale(context, "es");
                break;
            default:
                LocaleUtils.setLocale(context, "es");
                break;
        }
        context.startActivity(new Intent(context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("change_language",true));
    }

    private void showLogoutAlertDialog() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setMessage(context.getString(R.string.are_you_sure_want_logout));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                logOut();
            }
        });
        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void logOut() {
        customDialog.show();
        // String shop_id = SharedHelper.getKey(context, Constants.PREF.PROFILE_ID);
        Call<ResponseBody> call = apiInterface.logOut();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    clearAndExit();
                } else {
                    Gson gson = new Gson();
                    try {
                        ServerError serverError = gson.fromJson(response.errorBody().charStream(), ServerError.class);
                        Utils.displayMessage(activity, serverError.getError());
                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(activity, activity.getString(R.string.something_went_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(activity, activity.getString(R.string.something_went_wrong));
            }
        });
    }

    private void showDeleteAccountAlertDialog() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setMessage(context.getString(R.string.are_you_sure_want_delete_restaurant));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (connectionHelper.isConnectingToInternet()) {
                    deleteAccount();
                } else {
                    Utils.displayMessage(activity, context.getString(R.string.oops_no_internet));
                }


            }
        });
        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void deleteAccount() {
        customDialog.show();
        String shop_id = SharedHelper.getKey(context, Constants.PREF.PROFILE_ID);
        Call<ResponseBody> call = apiInterface.deleteAccount(shop_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    clearAndExit();
                } else {
                    Gson gson = new Gson();
                    try {
                        ServerError serverError = gson.fromJson(response.errorBody().charStream(), ServerError.class);
                        Utils.displayMessage(activity, serverError.getError());
                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(activity, activity.getString(R.string.something_went_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(activity, activity.getString(R.string.something_went_wrong));
            }
        });
    }


    private void clearAndExit() {
        SharedHelper.clearSharedPreferences(context);
        GlobalData.accessToken = "";
        context.startActivity(new Intent(context, LoginActivity.class));
        activity.finish();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Setting item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Setting item) {
        int position = list.indexOf(item);
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;
        LinearLayout llMain;

        MyViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.setting_icon);
            title = view.findViewById(R.id.setting_title);
            llMain = view.findViewById(R.id.llMain);
        }
    }
}

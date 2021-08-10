package com.rapidplus.shop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rapidplus.shop.R;
import com.rapidplus.shop.activity.AddAddOnsActivity;
import com.rapidplus.shop.activity.AddOnsActivity;
import com.rapidplus.shop.helper.GlobalData;
import com.rapidplus.shop.model.Addon;

import java.util.List;

public class AddOnsAdapter extends RecyclerView.Adapter<AddOnsAdapter.MyViewHolder> {
    Context context;
    Activity activity;
    private List<Addon> list;

    public AddOnsAdapter(List<Addon> list, Context con) {
        this.list = list;
        this.context = con;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addons_list_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Addon Addon = list.get(position);
        holder.name.setText(Addon.getName());
        holder.closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder cancelAlert = new AlertDialog.Builder(context);
                cancelAlert.setTitle(context.getResources().getString(R.string.add_ons));
                cancelAlert.setMessage(context.getResources().getString(R.string.are_you_sure_want_to_delete_addon));
                cancelAlert.setPositiveButton(context.getResources().getString(R.string.okay), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ((AddOnsActivity) context).deleteAddon(list.get(position), position);
                    }
                });
                cancelAlert.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                cancelAlert.show();
            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalData.selectedAddon = list.get(position);
                context.startActivity(new Intent(context, AddAddOnsActivity.class).putExtra("is_update", true));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Addon item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Addon item) {
        int position = list.indexOf(item);
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView closeImg;
        RelativeLayout itemLayout;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.addons_name_txt);
            closeImg = view.findViewById(R.id.close_img);
            itemLayout = view.findViewById(R.id.item_layout);
        }
    }


}

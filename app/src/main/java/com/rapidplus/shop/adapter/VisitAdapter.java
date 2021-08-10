package com.rapidplus.shop.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rapidplus.shop.R;
import com.rapidplus.shop.model.House;

import java.util.List;

public class VisitAdapter extends RecyclerView.Adapter<VisitAdapter.MyViewHolder> {
    Context context;
    Activity activity;
    private List<House> list;

    public VisitAdapter(List<House> list, Context con) {
        this.list = list;
        this.context = con;
        this.activity = activity;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visit_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        House House = list.get(position);
        holder.addressHeader.setText(House.getAddressHeader());
        holder.address.setText(House.getAddress());
        holder.status.setText(House.getStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(House item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(House item) {
        int position = list.indexOf(item);
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView addressHeader, address, status;

        public MyViewHolder(View view) {
            super(view);
            addressHeader = view.findViewById(R.id.address_header);
            address = view.findViewById(R.id.address);
            status = view.findViewById(R.id.status);
        }
    }

}

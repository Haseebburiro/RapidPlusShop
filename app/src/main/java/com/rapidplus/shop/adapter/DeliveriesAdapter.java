package com.rapidplus.shop.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rapidplus.shop.R;
import com.rapidplus.shop.helper.GlobalData;
import com.rapidplus.shop.model.Address;
import com.rapidplus.shop.model.Invoice;
import com.rapidplus.shop.model.Order;
import com.rapidplus.shop.model.Shop;
import com.rapidplus.shop.model.Transporter;
import com.rapidplus.shop.model.User;

import java.util.List;

public class DeliveriesAdapter extends RecyclerView.Adapter<DeliveriesAdapter.MyViewHolder> {
    private List<Order> list;
    private Context context;
    private Activity activity;

    public DeliveriesAdapter(List<Order> list, Context con) {
        this.list = list;
        this.context = con;
        this.activity = activity;
    }

    public void setList(List<Order> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deliveries_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Order order = list.get(position);
        if (order != null) {

            Transporter transporter = order.getTransporter();
            if (transporter != null && transporter.getName() != null) {
                holder.deliveryPersonName.setText(transporter.getName());
                holder.deliveryPersonName.setVisibility(View.VISIBLE);
            } else {
                holder.deliveryPersonName.setVisibility(View.GONE);
            }

            Shop shop = order.getShop();
            if (shop != null && shop.getName() != null) {
                holder.shopName.setText(shop.getName());
            }

            User user = order.getUser();
            if (user != null && user.getName() != null) {
                holder.userName.setText(user.getName());
            }

            Address address = order.getAddress();
            if (address != null && address.getMapAddress() != null) {
                holder.address.setText(address.getMapAddress());
            }

            Invoice invoice = order.getInvoice();
            if (invoice != null && invoice.getNet() != 0) {
                holder.totalAmt.setText(GlobalData.profile.getCurrency() + String.valueOf(invoice.getNet()));
            }

            Log.d("11111111111111", "Delivery Status::::" + order.getStatus());
            if (order.getStatus() != null) {
                if (order.getStatus().equalsIgnoreCase(context.getString(R.string.completed)))
                    holder.statusTxt.setText(context.getString(R.string.completed));
                else if (order.getStatus().equalsIgnoreCase(context.getString(R.string.cancelled)))
                    holder.statusTxt.setText(context.getString(R.string.cancelled));
                else
                    holder.statusTxt.setText(order.getStatus());
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Order item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Order item) {
        int position = list.indexOf(item);
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName, address, deliveryPersonName, statusTxt, shopName, totalAmt;
        CardView itemLayout;

        MyViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.user_name);
            address = view.findViewById(R.id.address);
            deliveryPersonName = view.findViewById(R.id.delivery_person_name);
            itemLayout = view.findViewById(R.id.item_layout);
            statusTxt = view.findViewById(R.id.status_txt);
            totalAmt = view.findViewById(R.id.total_amnt);
            shopName = view.findViewById(R.id.shop_name);
        }
    }

}

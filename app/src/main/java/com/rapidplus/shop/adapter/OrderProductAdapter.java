package com.rapidplus.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.rapidplus.shop.R;
import com.rapidplus.shop.application.MyApplication;
import com.rapidplus.shop.helper.GlobalData;
import com.rapidplus.shop.model.CartAddon;
import com.rapidplus.shop.model.Item;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderProductAdapter extends SectionedRecyclerViewAdapter<OrderProductAdapter.ViewHolder> {

    Context context;
    private List<Item> list = new ArrayList<>();
    private LayoutInflater inflater;

    public
    OrderProductAdapter(Context context, List<Item> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                v = inflater.inflate(R.layout.order_product_header, parent, false);
                return new ViewHolder(v, true);
            case VIEW_TYPE_ITEM:
                v = inflater.inflate(R.layout.order_addons_list_item, parent, false);
                return new ViewHolder(v, false);
            default:
                v = inflater.inflate(R.layout.order_addons_list_item, parent, false);
                return new ViewHolder(v, false);
        }
    }


    @Override
    public int getSectionCount() {
        return list.size();
    }


    @Override
    public int getItemCount(int section) {
        if (list.get(section).getCartAddons().isEmpty())
            return 1;
        else
            return list.get(section).getCartAddons().size();
    }

    @Override
    public void onBindHeaderViewHolder(OrderProductAdapter.ViewHolder holder, final int section) {
        Item item = list.get(section);
        String value = context.getResources().getString(R.string.product_, item.getProduct().getName(), item.getQuantity(), MyApplication.getNumberFormat().format(item.getProduct().getPrices().getOrignalPrice()));
        holder.productDetail.setText(value);
        Double totalAmount = Double.valueOf(item.getQuantity() * item.getProduct().getPrices().getOrignalPrice());
        Log.e("Country","------"+GlobalData.profile.getCurrency());
        //holder.productPrice.setText(MyApplication.getNumberFormat(GlobalData.profile.getCurrency()).format(totalAmount));
        holder.productPrice.setText(GlobalData.profile.getCurrency()+String.format(context.getString(R.string.round_off),totalAmount));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int section, int relativePosition, int absolutePosition) {
        if (!list.get(section).getCartAddons().isEmpty()) {
            CartAddon object = list.get(section).getCartAddons().get(relativePosition);
            holder.itemLayout.setVisibility(View.VISIBLE);
            String value = context.getString(R.string.addon_, object.getAddonProduct().getAddon().getName(), object.getQuantity(), MyApplication.getNumberFormat().format(object.getAddonProduct().getPrice()));
            holder.addonDetail.setText(value);
            Double totalAmount = Double.valueOf(object.getAddonProduct().getPrice() * object.getQuantity());
           // NumberFormat numberFormat = MyApplication.getNumberFormat(GlobalData.profile.getCountry_code());
            holder.addonPrice.setText(MyApplication.getNumberFormat().format(totalAmount));

          //  Log.e("Country","------"+GlobalData.profile.getCountry_code());
        } else {
            holder.itemLayout.setVisibility(View.GONE);
        }
    }

    private String getDetail(Integer quantity, Double price) {
        StringBuilder data = new StringBuilder("( ");
        data.append(quantity);
        data.append("x ");
        data.append(GlobalData.profile.getCurrency());
        data.append(price);
        data.append(")");
        return String.valueOf(data);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productDetail;
        TextView productPrice;
        TextView addonDetail;
        TextView addonPrice;
        LinearLayout itemLayout;

        public ViewHolder(View itemView, boolean isHeader) {
            super(itemView);
            if (isHeader) {
                productDetail = (TextView) itemView.findViewById(R.id.product_detail);
                productPrice = (TextView) itemView.findViewById(R.id.product_price);
            } else {
                addonPrice = (TextView) itemView.findViewById(R.id.addon_price);
                addonDetail = (TextView) itemView.findViewById(R.id.addon_detail);
                itemLayout = itemView.findViewById(R.id.item_layout);
            }

        }


    }
}
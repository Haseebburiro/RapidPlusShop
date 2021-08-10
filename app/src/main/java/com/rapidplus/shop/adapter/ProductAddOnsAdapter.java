package com.rapidplus.shop.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.EditText;

import com.rapidplus.shop.R;
import com.rapidplus.shop.model.Addon;
import com.rapidplus.shop.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProductAddOnsAdapter extends RecyclerView.Adapter<ProductAddOnsAdapter.MyViewHolder> {
    private static final String TAG = "ProductAddOnsAdapter";
    Activity activity;
    ArrayList<Addon> selectedAddOnlist = new ArrayList<>();
    private List<Addon> list;

    public ProductAddOnsAdapter(List<Addon> list, Activity con) {
        this.list = list;
        this.activity = con;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_addons_list_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Addon data = list.get(position);
        holder.ctvAddOnName.setText(data.getName());

        if (data.isChecked()) {
            holder.ctvAddOnName.setChecked(true);
        } else {
            holder.ctvAddOnName.setChecked(false);
        }

        holder.ctvAddOnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addon data = list.get((int) v.getTag());
                if (data.isChecked()) {
                    holder.ctvAddOnName.setChecked(false);
                    data.setChecked(false);
                } else {
                    holder.ctvAddOnName.setChecked(true);
                    data.setChecked(true);
                }
            }
        });
        final String prefix = activity.getResources().getString(R.string.currency_value);
        if (!data.getPrice().isEmpty() && !data.getPrice().startsWith(prefix)) {
            holder.etPrice.setText(prefix + data.getPrice());
        } else if (!data.getPrice().isEmpty() && data.getPrice().startsWith(prefix)) {
            holder.etPrice.setText(data.getPrice());
        } else {
            holder.etPrice.setText(prefix);
        }
        Selection.setSelection(holder.etPrice.getText(), holder.etPrice.getText().length());
        holder.etPrice.addTextChangedListener(new GenericTextWatcher(holder.etPrice));
        holder.etPrice.setTag(position);
        holder.ctvAddOnName.setTag(position);
    }

    public void setList(List<Addon> list) {
        this.list = list;
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

    public ArrayList<Addon> getSelectAddOnList() {
        return selectedAddOnlist;
    }

    public boolean verifyPrice() {
        selectedAddOnlist.clear();
        final String prefix = activity.getResources().getString(R.string.currency_value);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                if (list.get(i).getPrice().replace(prefix, "").isEmpty()) {
                    Utils.displayMessage(activity, activity.getResources().getString(R.string.please_enter_price));
                    return false;
                }

                selectedAddOnlist.add(list.get(i));
            }
        }

        return true;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        EditText etPrice;
        CheckedTextView ctvAddOnName;

        public MyViewHolder(View view) {
            super(view);

            etPrice = view.findViewById(R.id.etPrice);
            ctvAddOnName = view.findViewById(R.id.ctvAddOnName);
        }
    }

    class GenericTextWatcher implements TextWatcher {
        final String prefix = activity.getResources().getString(R.string.currency_value);
        private EditText view;

        private GenericTextWatcher(EditText view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!editable.toString().startsWith(prefix)) {
                view.setText(prefix);
                Selection.setSelection(view.getText(), view.getText().length());
            } else {
                Addon data = list.get((int) view.getTag());
                data.setPrice(editable.toString());
            }
        }
    }


}

package com.rapidplus.shop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rapidplus.shop.R;
import com.rapidplus.shop.model.Category;
import com.rapidplus.shop.model.Image;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Category> list;
    private Context context;
    private CategoryAdapterListener categoryAdapterListener;

    public CategoryAdapter(List<Category> list, Context con) {
        this.list = list;
        this.context = con;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Category object = list.get(position);
        if (object != null) {

            if (object.getName() != null)
                holder.name.setText(object.getName());

            if (object.getDescription() != null)
                holder.descTxt.setText(object.getDescription());

            if (object.getImages() != null && object.getImages().size() > 0) {
                List<Image> images = object.getImages();
                if (images != null && images.size() > 0) {
                    String img = images.get(0).getUrl();
                    Glide.with(context).load(img)
                            .apply(new RequestOptions().centerCrop().placeholder(R.drawable.ic_place_holder_image).error(R.drawable.ic_place_holder_image).dontAnimate()).into(holder.categoryImg);
                }
            }

            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (categoryAdapterListener != null)
                        categoryAdapterListener.onCategoryClick(object);
                }
            });

            holder.closeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (categoryAdapterListener != null) {
                        AlertDialog.Builder cancelAlert = new AlertDialog.Builder(context);
                        cancelAlert.setTitle(context.getResources().getString(R.string.add_ons));
                        cancelAlert.setMessage(context.getResources().getString(R.string.are_you_sure_want_to_delete_category));
                        cancelAlert.setPositiveButton(context.getResources().getString(R.string.okay), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                categoryAdapterListener.onCategoryDeleteClick(object);
                            }
                        });
                        cancelAlert.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        });
                        cancelAlert.show();
                    }
                }
            });

        }
    }

    public void setCategoryAdapterListener(CategoryAdapterListener categoryAdapterListener) {
        this.categoryAdapterListener = categoryAdapterListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Category item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Category item) {
        int position = list.indexOf(item);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void setList(List<Category> list) {
        this.list = list;
    }

    public interface CategoryAdapterListener {
        void onCategoryClick(Category category);

        void onCategoryDeleteClick(Category category);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, descTxt;
        ImageView closeImg, categoryImg;
        LinearLayout itemLayout;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.category_name_txt);
            descTxt = view.findViewById(R.id.category_desc_txt);
            closeImg = view.findViewById(R.id.close_img);
            categoryImg = view.findViewById(R.id.category_img);
            itemLayout = view.findViewById(R.id.item_layout);
        }
    }
}

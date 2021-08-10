package com.rapidplus.shop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rapidplus.shop.R;
import com.rapidplus.shop.model.Addon;
import com.rapidplus.shop.model.Image;
import com.rapidplus.shop.model.ProductModel;
import com.rapidplus.shop.model.product.ProductResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends SectionedRecyclerViewAdapter<ProductsAdapter.ViewHolder> {

    Context context;
    private List<ProductModel> list = new ArrayList<>();
    private LayoutInflater inflater;
    private ProductsAdapter.ProductAdapterListener productAdapterListener;


    public ProductsAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                v = inflater.inflate(R.layout.category_header, parent, false);
                return new ViewHolder(v, true);
            case VIEW_TYPE_ITEM:
                v = inflater.inflate(R.layout.product_list_item, parent, false);
                return new ViewHolder(v, false);
            default:
                v = inflater.inflate(R.layout.product_list_item, parent, false);
                return new ViewHolder(v, false);
        }
    }

    @Override
    public int getSectionCount() {
        return list.size();
    }


    @Override
    public int getItemCount(int section) {
        return list.get(section).getProductList().size();
    }

    @Override
    public void onBindHeaderViewHolder(ProductsAdapter.ViewHolder holder, final int section) {
        holder.header.setText(list.get(section).getHeader());
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(list.get(section).getHeader());
            }
        });
    }

    public void setList(List<ProductModel> list) {
        this.list = list;
    }





    @Override
    public void onBindViewHolder(ViewHolder holder, int section, int relativePosition, int absolutePosition) {

        final ProductResponse object = list.get(section).getProductList().get(relativePosition);
        holder.productName.setText(object.getName());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productAdapterListener != null)
                    productAdapterListener.onProductClick(object);
            }
        });

        holder.closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productAdapterListener != null) {
                    AlertDialog.Builder cancelAlert = new AlertDialog.Builder(context);
                    cancelAlert.setTitle(context.getResources().getString(R.string.product));
                    cancelAlert.setMessage(context.getResources().getString(R.string.are_you_sure_want_to_delete_product));
                    cancelAlert.setPositiveButton(context.getResources().getString(R.string.okay), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            productAdapterListener.onProductDeleteClick(object);
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

        if (object.getImages() != null && object.getImages().size() > 0) {
            List<Image> images = object.getImages();
            if (images != null && images.size() > 0) {
                String img = images.get(0).getUrl();
                Glide.with(context).load(img)
                        .apply(new RequestOptions().centerCrop().placeholder(R.drawable.ic_place_holder_image).error(R.drawable.ic_place_holder_image).dontAnimate()).into(holder.productImg);
            }
        }

        if (object.getAddons() != null && object.getAddons().size() > 0) {
            List<Addon> addonsList = object.getAddons();
            String addOnNames = "";
            for (int i = 0; i < addonsList.size(); i++) {
                if (addonsList.get(i).getAddon() != null) {
                    if (i == 0) {
                        addOnNames = addonsList.get(i).getAddon().getName();
                    } else {
                        addOnNames = addOnNames + "," + addonsList.get(i).getAddon().getName();
                    }
                }

            }
            holder.addOns.setVisibility(View.VISIBLE);
            holder.noAddOns.setVisibility(View.GONE);

            holder.addOns.setText(addOnNames);
        } else {
            holder.addOns.setVisibility(View.GONE);
            holder.noAddOns.setVisibility(View.VISIBLE);
        }

    }

    public void setProductAdapterListener(ProductsAdapter.ProductAdapterListener productAdapterListener) {
        this.productAdapterListener = productAdapterListener;
    }

    public interface ProductAdapterListener {
        void onProductClick(ProductResponse category);

        void onProductDeleteClick(ProductResponse category);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView header;
        TextView noAddOns;
        TextView addOns;
        TextView productName;
        ImageView productImg;
        ImageView closeImg;
        RelativeLayout itemLayout;

        public ViewHolder(View itemView, boolean isHeader) {
            super(itemView);
            if (isHeader) {
                header = (TextView) itemView.findViewById(R.id.category_header);
            } else {
                itemLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
                productName = (TextView) itemView.findViewById(R.id.product_name_txt);
                noAddOns = (TextView) itemView.findViewById(R.id.no_addons_txt);
                addOns = (TextView) itemView.findViewById(R.id.addons_name_txt);
                productImg = (ImageView) itemView.findViewById(R.id.product_img);
                closeImg = (ImageView) itemView.findViewById(R.id.close_img);
            }

        }


    }
}
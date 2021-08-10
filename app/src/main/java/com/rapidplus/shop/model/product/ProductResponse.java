package com.rapidplus.shop.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.rapidplus.shop.model.Addon;
import com.rapidplus.shop.model.Image;

import java.util.ArrayList;
import java.util.List;


public class ProductResponse implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProductResponse> CREATOR = new Parcelable.Creator<ProductResponse>() {
        @Override
        public ProductResponse createFromParcel(Parcel in) {
            return new ProductResponse(in);
        }

        @Override
        public ProductResponse[] newArray(int size) {
            return new ProductResponse[size];
        }
    };
    @SerializedName("featured")
    private int featured;
    @SerializedName("images")
    private List<Image> images;
    @SerializedName("featured_images")
    private List<Image> featuredImages;
    @SerializedName("max_quantity")
    private int maxQuantity;
    @SerializedName("shop")
    private Shop shop;
    @SerializedName("addons")
    private List<Addon> addons;
    @SerializedName("description")
    private String description;
    @SerializedName("cuisine_id")
    private int cuisineId;
    @SerializedName("avalability")
    private int avalability;
    @SerializedName("food_type")
    private String foodType;
    @SerializedName("variants")
    private List<String> variants;
    @SerializedName("shop_id")
    private int shopId;
    @SerializedName("addon_status")
    private int addonStatus;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;
    @SerializedName("position")
    private int position;
    @SerializedName("productcuisines")
    private Productcuisines productcuisines;
    @SerializedName("categories")
    private List<CategoriesItem> categories;
    @SerializedName("prices")
    private Prices prices;
    @SerializedName("status")
    private String status;

    public ProductResponse(Parcel in) {
        featured = in.readInt();
        if (in.readByte() == 0x01) {
            images = new ArrayList<Image>();
            in.readList(images, Image.class.getClassLoader());
        } else {
            images = null;
        }

        if (in.readByte() == 0x01) {
            featuredImages = new ArrayList<Image>();
            in.readList(featuredImages, Image.class.getClassLoader());
        } else {
            featuredImages = null;
        }

        maxQuantity = in.readInt();
        shop = (Shop) in.readValue(Shop.class.getClassLoader());
        if (in.readByte() == 0x01) {
            addons = new ArrayList<Addon>();
            in.readList(addons, Addon.class.getClassLoader());
        } else {
            addons = null;
        }
        description = in.readString();
        cuisineId = in.readInt();
        avalability = in.readInt();
        foodType = in.readString();
        if (in.readByte() == 0x01) {
            variants = new ArrayList<String>();
            in.readList(variants, String.class.getClassLoader());
        } else {
            variants = null;
        }
        shopId = in.readInt();
        addonStatus = in.readInt();
        name = in.readString();
        id = in.readInt();
        position = in.readInt();
        productcuisines = (Productcuisines) in.readValue(Productcuisines.class.getClassLoader());
        if (in.readByte() == 0x01) {
            categories = new ArrayList<CategoriesItem>();
            in.readList(categories, CategoriesItem.class.getClassLoader());
        } else {
            categories = null;
        }
        prices = (Prices) in.readValue(Prices.class.getClassLoader());
        status = in.readString();
    }

    public ProductResponse() {

    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Addon> getAddons() {
        return addons;
    }

    public void setAddons(List<Addon> addons) {
        this.addons = addons;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(int cuisineId) {
        this.cuisineId = cuisineId;
    }

    public int getAvalability() {
        return avalability;
    }

    public void setAvalability(int avalability) {
        this.avalability = avalability;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getAddonStatus() {
        return addonStatus;
    }

    public void setAddonStatus(int addonStatus) {
        this.addonStatus = addonStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Productcuisines getProductcuisines() {
        return productcuisines;
    }

    public void setProductcuisines(Productcuisines productcuisines) {
        this.productcuisines = productcuisines;
    }

    public List<CategoriesItem> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesItem> categories) {
        this.categories = categories;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Image> getFeaturedImages() {
        return featuredImages;
    }

    public void setFeaturedImages(List<Image> featuredImages) {
        this.featuredImages = featuredImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(featured);
        if (images == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(images);
        }

        if (featuredImages == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(featuredImages);
        }
        dest.writeInt(maxQuantity);
        dest.writeValue(shop);
        if (addons == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(addons);
        }
        dest.writeString(description);
        dest.writeInt(cuisineId);
        dest.writeInt(avalability);
        dest.writeString(foodType);
        if (variants == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(variants);
        }
        dest.writeInt(shopId);
        dest.writeInt(addonStatus);
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(position);
        dest.writeValue(productcuisines);
        if (categories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(categories);
        }
        dest.writeValue(prices);
        dest.writeString(status);
    }
}
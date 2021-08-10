
package com.rapidplus.shop.network;

/**
 * Created by tamil@appoets.com on 30-08-2017.
 */


import com.rapidplus.shop.model.Addon;
import com.rapidplus.shop.model.AuthToken;
import com.rapidplus.shop.model.Category;
import com.rapidplus.shop.model.ChangePassword;
import com.rapidplus.shop.model.Cuisine;
import com.rapidplus.shop.model.ForgotPasswordResponse;
import com.rapidplus.shop.model.HistoryModel;
import com.rapidplus.shop.model.IncomingOrders;
import com.rapidplus.shop.model.Order;
import com.rapidplus.shop.model.Profile;
import com.rapidplus.shop.model.ResetPasswordResponse;
import com.rapidplus.shop.model.RevenueResponse;
import com.rapidplus.shop.model.Transporter;
import com.rapidplus.shop.model.ordernew.OrderResponse;
import com.rapidplus.shop.model.product.ProductResponse;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {


    /*-------------USER--------------------*/

    @GET("api/shop/profile")
    Call<Profile> getProfile(@QueryMap HashMap<String, String> params);

    @Multipart
    @POST("api/shop/profile/{id}")
    Call<Profile> updateProfileWithFile(@Path("id") int id, @PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part filename1, @Part MultipartBody.Part filename2);

    @Multipart
    @POST("api/shop/profile/{id}")
    Call<Profile> updateProfile(@Path("id") int id, @PartMap HashMap<String, RequestBody> params);
//
//    @Multipart
//    @POST("api/user/profile")
//    Call<User> updateProfileWithImage(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part filename);
//
//    @FormUrlEncoded
//    @POST("api/user/otp")
//    Call<Otp> postOtp(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("oauth/token")
    Call<AuthToken> login(@FieldMap HashMap<String, String> params);

    @Multipart
    @POST("api/shop/shopregister")
    Call<Profile> signUp(@PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part filename1, @Part MultipartBody.Part filename2);


    @GET("api/shop/cuisines")
    Call<List<Cuisine>> getCuisines();

    @FormUrlEncoded
    @PATCH("api/shop/order/{id}")
    Call<Order> updateOrderStatus(@Path("id") int id, @FieldMap HashMap<String, String> params);

    @GET("api/shop/order/{id}")
    Call<OrderResponse> getParticularOrders(@Path("id") int id);

    @GET("api/shop/products")
    Call<List<ProductResponse>> getProductList();


    /*-------------ADD-ONS--------------------*/
    @GET("api/shop/addons")
    Call<List<Addon>> getAddons();

    @FormUrlEncoded
    @POST("api/shop/addons")
    Call<Addon> addAddon(@Field("name") String name);

    @FormUrlEncoded
    @PATCH("api/shop/addons/{id}")
    Call<Addon> updateAddon(@Path("id") int id, @Field("name") String name);

    @DELETE("api/shop/addons/{id}")
    Call<List<Addon>> deleteAddon(@Path("id") int id);


    /*------------- CATEGORY --------------------*/
    @GET("api/shop/categories")
    Call<List<Category>> getCategory();

    @Multipart
    @POST("api/shop/categories")
    Call<Category> addCategory(@PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part filename);

    @Multipart
    @POST("api/shop/categories/{id}")
    Call<Category> updateCategory(@Path("id") int id, @PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part filename);

    @DELETE("api/shop/categories/{id}")
    Call<List<Category>> deleteCategory(@Path("id") int id);

    /*---------------Change Password---------------*/
    @FormUrlEncoded
    @POST("api/shop/password")
    Call<ChangePassword> changePassword(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("api/shop/forgot/password")
    Call<ForgotPasswordResponse> forgotPassword(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("api/shop/verifyotp")
    Call<ForgotPasswordResponse> verifyOTP(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("api/shop/reset/password")
    Call<ResetPasswordResponse> resetPassword(@FieldMap HashMap<String, String> params);

    /*........Revenue Fragment......*/

    @GET("api/shop/revenue")
    Call<RevenueResponse> getRevenueDetails();

    @Multipart
    @POST("api/shop/products")
    Call<ProductResponse> addProduct(@PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part filepart1, @Part MultipartBody.Part filepart2);


    @Multipart
    @POST("api/shop/products/{id}")
    Call<ProductResponse> updateProduct(@Path("id") int id, @PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part filepart1, @Part MultipartBody.Part filepart2);

    @DELETE("api/shop/products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    /*....Account....*/

    @DELETE("api/shop/remove/{id}")
    Call<ResponseBody> deleteAccount(@Path("id") String id);

    @GET("api/shop/logout")
    Call<ResponseBody> logOut();

    /*-------------ORDER--------------------*/

    @GET("api/shop/order")
    Call<IncomingOrders> getIncomingOrders(@Query("t") String type);

    @GET("api/shop/history")
    Call<HistoryModel> getHistory();

    @GET("api/shop/history")
    Call<HistoryModel> getFilterHistory(@QueryMap HashMap<String, String> params);

    @GET("api/shop/transporterlist")
    Call<List<Transporter>> getTransporter();

//    @GET("api/user/ongoing/order")
//    Call<List<Order>> getOngoingOrders();
//
//    @GET("api/user/order/{id}")
//    Call<Order> getParticularOrders(@Path("id") int id);
//
//    @GET("api/user/order")
//    Call<List<Order>> getPastOders();
//
//    @DELETE("api/user/order/{id}")
//    Call<Order> cancelOrder(@Path("id") int id, @Query("reason") String reason);
//
//    @FormUrlEncoded
//    @POST("api/user/rating")
//    Call<ChangePassword> rate(@FieldMap HashMap<String, String> params);
//
//    @FormUrlEncoded
//    @POST("api/user/reorder")
//    Call <AddCart> reOrder(@FieldMap HashMap<String, String> params);
//
//     /*-------------DISPUTE--------------------*/
//
//    @GET("api/user/disputehelp")
//    Call<List<DisputeMessage>> getDisputeList();
//
//    @FormUrlEncoded
//    @POST("api/user/dispute")
//    Call<Order> postDispute(@FieldMap HashMap<String, String> params);
//
//
//    /*-------------SEARCH--------------------*/
//    @GET("api/user/search")
//    Call<Search> getSearch(@QueryMap HashMap<String, String> params);
//
//    /*-----------------------WALLET-----------------------*/
//    @GET("api/user/wallet")
//    Call<List<WalletHistory>> getWalletHistory();
//
//    @GET("api/user/wallet/promocode")
//    Call<List<Promotions>> getWalletPromoCode();
//
//    @FormUrlEncoded
//    @POST("api/user/wallet/promocode")
//    Call<PromotionResponse> applyWalletPromoCode(@Field("promocode_id") String id);
//
//
//    @GET("json?")
//    Call<ResponseBody> getResponse(@Query("latlng") String param1, @Query("key") String param2);
//
//    /*-------------PAYMENT--------------------*/
//    @GET("api/user/card")
//    Call<List<Card>> getCardList();
//
//    @FormUrlEncoded
//    @POST("api/user/card")
//    Call<ChangePassword> addCard(@Field("stripe_token") String stripeToken);
//
//    @FormUrlEncoded
//    @POST("api/user/add/money")
//    Call<AddMoney> addMoney(@FieldMap HashMap<String, String> params);
//
//    @DELETE("api/user/card/{id}")
//    Call<ChangePassword> deleteCard(@Path("id") int id);


}

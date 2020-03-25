package com.android.thresturent.common.network;



import com.android.thresturent.common.model.Advertising;
import com.android.thresturent.common.model.BookTableResponse;
import com.android.thresturent.common.model.LoginResponse;
import com.android.thresturent.common.model.MainResponse;
import com.android.thresturent.common.model.MenuItem;
import com.android.thresturent.common.model.MyOrderResponse;
import com.android.thresturent.common.model.User;
import com.android.thresturent.common.model.UserList;
import com.android.thresturent.common.model.UserListResponse;
import com.android.thresturent.common.model.table.Table;
import com.android.thresturent.common.model.verificationResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface API {

    @POST("Login_user.php")
    Call<LoginResponse> loginUser(@Body User user);//to send request and response the message if the account is login or not
    @POST("register_user.php")
    Call<MainResponse> registerUser(@Body User user);// to send request and response the message if the user inserted in database or not
    @FormUrlEncoded
    @POST("index.php")
    Call<verificationResponse> sendVerification(@Field("email") String email);
    @FormUrlEncoded
    @POST("verification.php")
    Call<verificationResponse> reciveVerification(@Field("email") String email, @Field("random") String verification);
    @FormUrlEncoded
    @POST("ResetPassword.php")
    Call<MainResponse> updatPassword(@Field("email") String email, @Field("password") String password) ;//to send request and response the message if the post update or not
    @POST("Login_user.php")
    Call<LoginResponse> loginTable(@Body Table table);
    @POST("get-all-user.php")
    Call<List<UserList>> getAllUsers();
    @FormUrlEncoded
    @POST("user_controller.php")
    Call<MainResponse> deleteUser(@Field("delete") String delete,@Field("id") int id);
    @FormUrlEncoded
    @POST("user_controller.php")
    Call<MainResponse> updatePermissionAdmin(@Field("permission_admin") String permission_admin,@Field("id") int id,@Field("value")String value);
    @FormUrlEncoded
    @POST("user_controller.php")
    Call<MainResponse> blockUser(@Field("value")int value,@Field("id") int id);
    @Multipart
    @POST("add_menu_table_item.php")
    Call<MainResponse> addManuUserItem(@Header("Cookie") String sessionIdAndRz,
                                        @Part MultipartBody.Part file,
                                        @Part("items") RequestBody items,
                                        @Part("isAny") RequestBody isAny,@Part("user") String user,@Part("item_name") String item_name,@Part("item_description") String item_description
            ,@Part("item_image") String item_image,@Part("price") String price);

    @POST("get-all-menu-item.php")
    Call<List<MenuItem>> getAllMenuItem();

    @FormUrlEncoded
    @POST("add-order-request.php")
    Call<MainResponse> addOrderRequest(@Field("order_id") int order_id ,@Field("user_id") int user_id,@Field("notifiy_token") String notifiy_token,@Field("location")String location ,@Field("user_type")String user_type);
    @FormUrlEncoded
    @POST("get-order-request.php")
    Call<MyOrderResponse> getOrderRequest(@Field("user_id") int user_id);
    @POST("get-order-request.php")
    Call<MyOrderResponse> getOrderRequest();

    @FormUrlEncoded
    @POST("update-order-status.php")
    Call<MainResponse> updateRequestStatus(@Field("user_id") int user_id,
                                           @Field("order_id") int order_id,
                                           @Field("request_id")int request_id,
                                           @Field("confirmation")int confirmation,
                                           @Field("token")String token);

    @FormUrlEncoded
    @POST("delete-order-request.php")
    Call<MainResponse> deleteItem(@Field("request_id")int request_id);
    @POST("check-user-blocked.php")
    Call<LoginResponse> checkBlocked(@Body User user);
    @FormUrlEncoded
    @POST("evaluate-order.php")
    Call<MainResponse> rateItem(@Field("rate")String rate,@Field("order_id")int order_id);

    @Multipart
    @POST("advertising.php")
    Call<MainResponse> addAdsItem(@Header("Cookie") String sessionIdAndRz,
                                       @Part MultipartBody.Part file,
                                       @Part("items") RequestBody items,
                                       @Part("isAny") RequestBody isAny,@Part("user_id")int user_id,@Part("ad_description") String item_description
                                      ,@Part("ad_image") String item_image);

    @POST("advertising.php")
    Call<List<Advertising>> getAds();

    @FormUrlEncoded
    @POST("update-user-information.php")
    Call<MainResponse> updateUserInformation(@Field("user_id")int user_id,@Field("user_name")String  userName,@Field("user_address")String userAddress);
    @FormUrlEncoded
    @POST("update-user-information.php")
    Call<MainResponse> updateUserInformation(@Field("user_id")int user_id,@Field("user_name")String  userName,@Field("user_address")String userAddress,@Field("password") String password);
    @FormUrlEncoded
    @POST("delete-menu-item.php")
    Call<MainResponse> deleteOrderItem(@Field("id")int orderId,@Field("image_item")String  imageItem);
    @FormUrlEncoded
    @POST("add-book-table.php")
    Call<MainResponse> addBookTable(@Field("user_id")int user_id,@Field("date")String  date,@Field("time")String time,@Field("persons_count")String person_ount,@Field("notifiy_token")String notifiy_token);
    @POST("get-all-book-table.php")
    Call<BookTableResponse> getAllBookTable();
    @FormUrlEncoded
    @POST("get-book-user-request.php")
    Call<BookTableResponse> getAllBookTableUsers(@Field("user_id")int user_id);
    @FormUrlEncoded
    @POST("update-book-status.php")
    Call<MainResponse> updateUserBookTable(@Field("id")int Id,@Field("user_id")int userId,@Field("confirmation")int confirmation,@Field("table_id") int tableId,@Field("token")String token);
    @FormUrlEncoded
    @POST("update_delete_request.php")
    Call<MainResponse> updateRequestIsDeleted(@Field("user_id") int user_id,
                                           @Field("order_id") int order_id,
                                           @Field("request_id")int request_id,
                                           @Field("is_deleted")int is_deleted
                                         );

    @POST("get-all-order-request-deleted.php")
    Call<MyOrderResponse> getAllDeletedOrders();

    @FormUrlEncoded
    @POST("add_report.php")
    Call<MainResponse> AddReports(@Field("user_id")int user_id,@Field("report_description")String description);
}

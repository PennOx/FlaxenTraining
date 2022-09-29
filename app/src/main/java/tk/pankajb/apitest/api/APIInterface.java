package tk.pankajb.apitest.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tk.pankajb.apitest.models.ImageUploadResponse;
import tk.pankajb.apitest.models.UserResponse;

public interface APIInterface {

    @GET("/aakarapp_api/getuser.php")
    Call<UserResponse> getUsers();

    @FormUrlEncoded
    @POST("/aakarapp_api/adduser.php")
    Call<UserResponse> createUser(@Field("name") String name, @Field("age") String age, @Field("gender") String gender, @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("/aakarapp_api/upload_img.php")
    Call<ImageUploadResponse> uploadImage(@Field("id") String id, @Field("disc") String desc, @Field("image") String image);
}

package mo.ed.prof.yusor.Listeners;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Prof-Mohamed Atef on 4/10/2019.
 */

public interface UploadBookApi {
    @Multipart
    @POST("Yusor/api/v1/add_book")
    Call<String> uploadFileAndTextData(
            @Part MultipartBody.Part file,
            @Part("title") RequestBody bookTitle,
            @Part("desc") RequestBody bookDescription,
            @Part("author_id") RequestBody authorID,
            @Part("publish_year") RequestBody publishYear,
            @Part("department_id") RequestBody departmentID,
            @Part("ISBN_num") RequestBody ISBN,
            @Part("name") RequestBody AuthorName,
            @Part("api_token") RequestBody apiToken);
}

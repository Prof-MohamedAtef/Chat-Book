package mo.ed.prof.yusor.helpers.RetrofitUtils;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Prof-Mohamed Atef on 3/30/2019.
 */

public class RetrofitClient {
    private static Retrofit retrofitClient =null;

    public static Retrofit getClient(String baseURl){
        if (retrofitClient ==null){
            retrofitClient =new Retrofit.Builder()
                    .baseUrl(baseURl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofitClient;
    }
}
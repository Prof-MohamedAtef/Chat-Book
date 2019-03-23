package mo.ed.prof.yusor.Listeners;

import mo.ed.prof.yusor.Dev.Notification.MyResponse;
import mo.ed.prof.yusor.Dev.Notification.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prof-Mohamed Atef on 3/22/2019.
 */

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAVOYdo8s:APA91bHFCK7ghotkqq6l2hDA08Fyf69qlo4RI94as4XgAfntFsOTa3Tc98Allofb65P3lRG63BhQNoLUDUGADtcXn7sYqCKNJEiST6v1Vt9FtaWXABJpt6d5JbIfIMPVhArLNPvjXWw3"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}

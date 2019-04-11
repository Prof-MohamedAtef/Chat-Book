package mo.ed.prof.yusor.helpers.RetrofitUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prof-Mohamed Atef on 4/10/2019.
 */

public class StatusError {
    @SerializedName("message")
    public String message;

    @SerializedName("errors")
    public ArrayList<String> errors;


    public String getMessage() {
        return message;
    }
    public void setMessage(String Message) {
        this.message = Message;
    }
}

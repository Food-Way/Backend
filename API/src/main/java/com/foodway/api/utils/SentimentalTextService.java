package com.foodway.api.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.gson.JsonParser;
import com.foodway.api.model.Enums.ESentiment;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException; // Add this import statement
@Service
 public class SentimentalTextService {
    private String urlCollab = "https://f8a1-34-105-122-67.ngrok-free.app/predict";
    public ESentiment getSentimental(String comment){
        Response response;
        OkHttpClient client = new OkHttpClient().newBuilder()
        .build();
        MediaType mediaType = MediaType.parse("application/json");
        JsonObject jsonCreate = new JsonObject();
        jsonCreate.addProperty("text", comment);

        RequestBody body = RequestBody.create( jsonCreate.toString() ,mediaType);
        Request request = new Request.Builder()
        .url(urlCollab)
        .method("POST", body)
        .addHeader("Content-Type", "application/json")
        .build();
        try {
            response = client.newCall(request).execute();
            if (response.code() == 200) {
                JsonObject json = JsonParser.parseString(response.body().string()).getAsJsonObject();
            switch (json.get("sentiment").getAsString()) {
                case "POSITIVE":
                    return ESentiment.POSITIVE;
                case "NEGATIVE":
                    return ESentiment.NEGATIVE;
                case "NEUTRAL":
                    return ESentiment.NEUTRAL;
                default:
                    return ESentiment.ERROR;
            }
            } else {
                return ESentiment.ERROR;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ESentiment.ERROR;
    }

}
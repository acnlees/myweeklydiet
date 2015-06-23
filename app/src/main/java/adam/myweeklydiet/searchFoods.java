package adam.myweeklydiet;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adam on 23/06/2015.
 */
public class searchFoods {

    public JSONObject result;
    public JSONArray list;
    public ArrayList<String> foods;
    public Map<String, String> foodlist;

    OkHttpClient client = new OkHttpClient();

    Map run (String url)throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();


        Response response = client.newCall(request).execute();

        try {

            foods = new ArrayList<String>();
            foodlist = new HashMap<String, String>();

            result = new JSONObject(response.body().string());



                list = result.getJSONObject("list").getJSONArray("item");


                for (int i = 0; i <= list.length(); i++) {

                    foodlist.put(result.getJSONObject("list").getJSONArray("item").getJSONObject(i).getString("name"), result.getJSONObject("list").getJSONArray("item").getJSONObject(i).getString("ndbno"));

                }


        }catch(JSONException e) {
            //handle
        }

        return foodlist;

        }




}

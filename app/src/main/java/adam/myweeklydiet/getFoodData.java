package adam.myweeklydiet;

    import android.util.Log;

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
    public class getFoodData {

        public JSONObject result;
        public JSONArray list;
        public ArrayList<String> foods;
        food newFood = new food();

        OkHttpClient client = new OkHttpClient();

        food newFood (String url)throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();

            try {
                result = new JSONObject(response.body().string());


                float weight = result.getJSONObject("report").getJSONArray("foods").getJSONObject(0).getInt("weight");

                float t = 100;
                float d = t/weight;

                float protein = result.getJSONObject("report").getJSONArray("foods").getJSONObject(0).getJSONArray("nutrients").getJSONObject(0).getInt("value");
                float calories =result.getJSONObject("report").getJSONArray("foods").getJSONObject(0).getJSONArray("nutrients").getJSONObject(1).getInt("value");
                float fat =result.getJSONObject("report").getJSONArray("foods").getJSONObject(0).getJSONArray("nutrients").getJSONObject(2).getInt("value");
                float carbs =result.getJSONObject("report").getJSONArray("foods").getJSONObject(0).getJSONArray("nutrients").getJSONObject(3).getInt("value");

                calories = calories*d;
                protein = protein*d;
                fat = fat*d;
                carbs = carbs *d;



                newFood.protein = String.valueOf(protein);
                newFood.calories = String.valueOf(calories);
                newFood.fat =  String.valueOf(fat);
                newFood.carbs = String.valueOf(carbs);



            }catch(JSONException e) {
                //handle
            }

            return newFood;

        }




    }



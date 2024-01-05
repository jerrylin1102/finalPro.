package com.example.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GPTrequest {
    static String model = "gpt-3.5-turbo-instruct";
    //static String model = "gpt-3.5-turbo-1106";
    //static  String model = "gpt-3.5-turbo";
    public static final String OPENAI_API_KEY = "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";
    public static final String OPENAI_API_URL = "https://api.openai.com/v1/engines/"+model+"/completions";

    public static String sendChatRequest(String userInput) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        String s =userInput ;
        int maxTokens = 500;
        double temperature = 0.5;
        String prompt = s;
        JSONObject jsonInputObject = new JSONObject();

        try {
            jsonInputObject.put("prompt", prompt);
            jsonInputObject.put("max_tokens", maxTokens);
            jsonInputObject.put("temperature", temperature);
        }
        catch (org.json.JSONException e){
            e.printStackTrace();
        }
        //String jsonInput = "{\"prompt\": \"" + userInput + "\", \"max_tokens\": 100}";

        String jsonInput = jsonInputObject.toString();
        RequestBody body = RequestBody.create(mediaType, jsonInput);
        String info="";
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                .post(body)
                .build();


        try {
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            // Handle the response data (e.g., display it in your app)
            //System.out.println(responseData);
            info = responseData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonResponse =  info ;
        String parsedText = parseTextFromJSON(jsonResponse);
        return parsedText;
    }
    public static String parseTextFromJSON(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray choicesArray = jsonObject.getJSONArray("choices");

            if (choicesArray.length() > 0) {
                JSONObject choiceObject = choicesArray.getJSONObject(0);
                return choiceObject.getString("text");
            } else {
                return "No text found in choices.";
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return "Error parsing JSON.";
        }
    }
}

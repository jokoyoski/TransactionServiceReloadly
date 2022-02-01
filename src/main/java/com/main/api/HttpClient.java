package com.main.api;

import com.main.model.request.CreateSendEmail;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
@Component
public class HttpClient {


    public void makeApiCall(CreateSendEmail createSendEmail){
        final String uri = "http://195.54.162.186:8082/api/send";

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.postForObject(uri, createSendEmail, String.class);

        try {
           var jsonResponse = new JSONObject(result);    // convert your result into json
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}

package sa57.team01.adproject.services;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sa57.team01.adproject.models.Customer;
import sa57.team01.adproject.models.Preferences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Long> getRecommendationsForNewCustomer(Customer customer) {
        String url = "http://localhost:5000/recommend_NN";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Preferences preferences = customer.getPreferences();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("preferred_town", preferences.getTown());

        // construct the  preferred_flat_type, according to preferences
        // 'preferred_flat_type': {'4 ROOM', '5 ROOM'},
        List<String> preferredFlatTypes = new ArrayList<>();

        if(preferences.isBedroom1()){
            preferredFlatTypes.add("1 ROOM");
        }
        if(preferences.isBedroom2()){
            preferredFlatTypes.add("2 ROOM");
        }
        if(preferences.isBedroom3()){
            preferredFlatTypes.add("3 ROOM");
        }
        if(preferences.isBedroom4()){
            preferredFlatTypes.add("4 ROOM");
        }

        requestMap.put("preferred_flat_type", preferredFlatTypes.toArray(new String[0]));
        //shall not be empty

        //put low price and high price, because the setting is not record the exact price
        //use mock data instead
        if(preferences.isLowPriceRange()){
            requestMap.put("low_price", 200000);
            requestMap.put("high_price", 400000);
        }

        if(preferences.isMidPriceRange()){
            requestMap.put("low_price", 400000);
            requestMap.put("high_price", 600000);
        }

        if(preferences.isHighPriceRange()){
            requestMap.put("low_price", 600000);
            requestMap.put("high_price", 800000);
        }

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestMap, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        Gson gson = new Gson();
        Map<String, List<Long>> resultMap = gson.fromJson(response.getBody(), new TypeToken<Map<String, List<Long>>>(){}.getType());
        return resultMap.get("recommendation");
    }

    public List<Long> getRecommendationsForExistingCustomer(Customer customer) {
        String url = "http://localhost:5000/recommend";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("user_id", customer.getId());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestMap, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        Gson gson = new Gson();
        Map<String, List<Long>> resultMap = gson.fromJson(response.getBody(), new TypeToken<Map<String, List<Long>>>(){}.getType());
        return resultMap.get("recommendation");
    }
}
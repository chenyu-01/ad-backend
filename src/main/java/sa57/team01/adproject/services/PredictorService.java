package sa57.team01.adproject.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sa57.team01.adproject.models.Property;
import sa57.team01.adproject.models.SaleProperty;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PredictorService {
    public List<Double> getPrediction(SaleProperty property) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5000/predict";  // Flask API

        //construct json
        //input_data = {
        //        'town': ,
        //        'floor_area_sqm':,
        //        'flat_type': '',
        //        'year': ,
        //        'resale_price': ,
        //    }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("town", property.getTown());
        requestMap.put("floor_area_sqm", property.getFloorArea());

        switch(property.getFlatType()) {
            case 1:
                requestMap.put("flat_type", "1 ROOM");
                break;
            case 2:
                requestMap.put("flat_type", "2 ROOM");
                break;
            case 3:
                requestMap.put("flat_type", "3 ROOM");
                break;
            case 4:
                requestMap.put("flat_type", "4 ROOM");
                break;
            case 5:
                requestMap.put("flat_type", "5 ROOM");
                break;
            case 6:
                requestMap.put("flat_type", "EXECUTIVE");
                break;
            case 7:
                requestMap.put("flat_type", "MULTI-GENERATION");
                break;
        }

        requestMap.put("year", 2023);
        requestMap.put("resale_price", property.getPrice());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestMap, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        Gson gson = new Gson();
        Map<String, List<Double>> resultMap = gson.fromJson(response.getBody(), new TypeToken<Map<String, List<Double>>>(){}.getType());
        return resultMap.get("prediction");
    }
}

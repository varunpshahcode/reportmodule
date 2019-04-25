package com.adjuster.reportmodule.Util;

import com.adjuster.reportmodule.model.Campaign;
import com.adjuster.reportmodule.model.Creative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Util {


    public static List<Campaign> getCampaigns(RestTemplate restTemplate, String url) {
        ResponseEntity<List<Campaign>> rateResponse =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Campaign>>() {
                        });
        return rateResponse.getBody();
    }

    public static List<Creative> getCreatives(RestTemplate restTemplate, String url) {
        ResponseEntity<List<Creative>> creativeResponse =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Creative>>() {
                        });
        return creativeResponse.getBody();
    }
}

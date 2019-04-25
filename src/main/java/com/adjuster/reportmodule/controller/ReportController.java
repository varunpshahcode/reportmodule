package com.adjuster.reportmodule.controller;

import com.adjuster.reportmodule.Exception.ReportModuleException;
import com.adjuster.reportmodule.Util.Util;
import com.adjuster.reportmodule.model.Campaign;
import com.adjuster.reportmodule.model.Creative;
import com.adjuster.reportmodule.model.SummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @GetMapping(path = "/summary/{id}", produces = "application/json")
    public SummaryResponse getSummary(@PathVariable(value = "id") long campaignId) {

        List<Campaign> campaigns = Util.getCampaigns(restTemplate, env.getProperty("url.campaigns"));
        List<Creative> creatives = Util.getCreatives(restTemplate, env.getProperty("url.creatives"));
        List<Creative> creativesByCampaignId = creatives.stream().filter(creative -> creative.getParentId() == campaignId).collect(Collectors.toList());
        if(creativesByCampaignId.isEmpty())
        {
            throw new ReportModuleException("No Creatives found for campaingId : "+campaignId);
        }
        long clicks = creativesByCampaignId.stream().mapToLong(e -> e.getClicks()).sum();
        long impressions = creativesByCampaignId.stream().mapToLong(e -> e.getImpressions()).sum();

        SummaryResponse response = new SummaryResponse();
        response.setCampaignId(campaignId);
        response.setClicks(clicks);
        response.setImpressions(impressions);

        return response;

    }

    @GetMapping(path = "/campaign/id/{id}", produces = "application/json")
    public Campaign getCampaign(@PathVariable(value = "id") long campaignId) {
        List<Campaign> campaigns = Util.getCampaigns(restTemplate, env.getProperty("url.campaigns"));
        Campaign byCampaignId = campaigns.stream().filter(campaign -> campaign.getId() == campaignId).findAny().orElse(null);
        if(byCampaignId == null)
        {
            throw new ReportModuleException("No Campaign found for campaingId : "+campaignId);
        }
        return byCampaignId;

    }

    @GetMapping(path = "/campaign/name/{name}", produces = "application/json")
    public Campaign getCampaignByName(@PathVariable(value = "name") String campaignName) {
        List<Campaign> campaigns = Util.getCampaigns(restTemplate, env.getProperty("url.campaigns"));
        Campaign byCampaignName = campaigns.stream().filter(campaign -> campaign.getName().equalsIgnoreCase(campaignName)).findAny().orElse(null);
        if(byCampaignName == null)
        {
            throw new ReportModuleException("No Campaign found for campaing name : "+campaignName);
        }
        return byCampaignName;

    }
}

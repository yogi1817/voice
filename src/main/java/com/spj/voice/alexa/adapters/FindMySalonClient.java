package com.spj.voice.alexa.adapters;

import com.spj.voice.alexa.pojo.BarberWaitTimeResponse;
import com.spj.voice.alexa.pojo.CustomerCheckInResponse;
import com.spj.voice.alexa.ports.out.IFindMySalonClient;
import com.spj.voice.config.ServiceConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindMySalonClient implements IFindMySalonClient {

    private final RestTemplate restTemplate;
    private final ServiceConfig serviceConfig;

    @Override
    public BarberWaitTimeResponse findWaittimeForUser(String oauthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(oauthToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity request = new HttpEntity(headers);

        return restTemplate.exchange(serviceConfig.getWaittimeuser(), HttpMethod.GET,
                request, BarberWaitTimeResponse.class, 1).getBody();
    }

    @Override
    public CustomerCheckInResponse checkInCustomer(String oauth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(oauth);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity request = new HttpEntity(headers);

        return restTemplate.exchange(serviceConfig.getCheckIn(), HttpMethod.POST,
                request, CustomerCheckInResponse.class, 1).getBody();
    }
}

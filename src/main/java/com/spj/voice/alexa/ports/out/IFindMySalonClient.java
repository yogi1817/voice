package com.spj.voice.alexa.ports.out;

import com.spj.voice.alexa.pojo.BarberWaitTimeResponse;
import com.spj.voice.alexa.pojo.CustomerCheckInResponse;

public interface IFindMySalonClient {
    public BarberWaitTimeResponse findWaittimeForUser(String oauthToken);
    public CustomerCheckInResponse checkInCustomer(String oauth);
}

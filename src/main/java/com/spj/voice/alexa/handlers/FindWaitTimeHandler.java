package com.spj.voice.alexa.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.spj.voice.alexa.pojo.BarberWaitTimeResponse;
import com.spj.voice.alexa.ports.out.IFindMySalonClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindWaitTimeHandler implements RequestHandler {

    private final IFindMySalonClient findMySalonClient;

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(Predicates.intentName("findSalonWaitTime"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText = getSpeechText(handlerInput);
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("HelloWorld", speechText)
                .build();
    }

    private String getSpeechText(HandlerInput handlerInput) {
        if (handlerInput.getRequestEnvelope() != null
                && handlerInput.getRequestEnvelope().getSession() != null
                && handlerInput.getRequestEnvelope().getSession().getUser() != null
                && !StringUtils.isEmpty(handlerInput.getRequestEnvelope().getSession().getUser().getAccessToken())) {
            BarberWaitTimeResponse barberWaitTimeResponse
                    = findMySalonClient.findWaittimeForUser(handlerInput.getRequestEnvelope()
                    .getSession().getUser().getAccessToken());

            if ("No Favourite Salon Found".equals(barberWaitTimeResponse.getSalonName()))
                return "No Favourite Salon set, please set your favourite salon in app and then try this service again";

            if ("Already checked in".equals(barberWaitTimeResponse.getSalonName()))
                return "You are already checkedin and your wait time is " + barberWaitTimeResponse.getWaitTime();

            return "Your wait time for salon " + barberWaitTimeResponse.getSalonName()
                    + " is " + barberWaitTimeResponse.getWaitTime() + " minutes";
        }
        return "You are not authenticated, please link your find my salon account with alexa";
    }
}

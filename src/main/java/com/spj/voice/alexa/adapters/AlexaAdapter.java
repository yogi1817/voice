package com.spj.voice.alexa.adapters;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ResponseEnvelope;
import com.spj.voice.alexa.handlers.FindWaitTimeHandler;
import com.spj.voice.alexa.ports.in.IAlexaAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlexaAdapter implements IAlexaAdapter {

    private final FindWaitTimeHandler findWaitTimeHandler;

    @Override
    public ResponseEnvelope processAlexaRequest(RequestEnvelope requestEnvelope, Map<String, String> headers) {
        Response response = findWaitTimeHandler.handle(HandlerInput.builder()
                .withRequestEnvelope(requestEnvelope).build()).get();
        return ResponseEnvelope.builder().withResponse(response).build();
    }
}

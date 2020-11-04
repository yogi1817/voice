package com.spj.voice.alexa.adapters;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ResponseEnvelope;
import com.spj.voice.alexa.handlers.CheckInHandler;
import com.spj.voice.alexa.handlers.FindWaitTimeHandler;
import com.spj.voice.alexa.ports.in.IAlexaAdapter;
import com.spj.voice.alexa.ports.out.IFindMySalonClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlexaAdapter implements IAlexaAdapter {

    private final FindWaitTimeHandler findWaitTimeHandler;
    private final CheckInHandler checkInHandler;

    @Override
    public ResponseEnvelope processAlexaRequest(RequestEnvelope requestEnvelope){
        HandlerInput handlerInput
                = HandlerInput.builder().withRequestEnvelope(requestEnvelope).build();
        Response response = null;

        if(findWaitTimeHandler.canHandle(handlerInput)){
            response = findWaitTimeHandler.handle(handlerInput).get();
        }else if(checkInHandler.canHandle(handlerInput)){
            response = checkInHandler.handle(handlerInput).get();
        }

        return ResponseEnvelope.builder().withResponse(response).build();
    }
}

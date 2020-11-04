package com.spj.voice.alexa.endpoints;

import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.model.services.Serializer;
import com.amazon.ask.servlet.ServletConstants;
import com.amazon.ask.servlet.verifiers.ServletRequest;
import com.amazon.ask.servlet.verifiers.SkillRequestSignatureVerifier;
import com.amazon.ask.util.JacksonSerializer;
import com.amazonaws.auth.policy.internal.JsonPolicyWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.spj.voice.alexa.ports.in.IAlexaAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "alexa", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlexaController {

    private final IAlexaAdapter alexaAdapter;

    @PostMapping(value = "voice")
    public ResponseEntity<ResponseEnvelope> findMySalonSkill(@RequestBody RequestEnvelope requestEnvelope,
                                                          HttpServletRequest httpRequest,
                                                          @RequestHeader Map<String, String> headers) {
        try {
            log.debug("inside findMySalonSkill");
            verifyAlexaRequest(httpRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            log.debug("verify complete Json request --> {}"+ objectMapper.writeValueAsString(requestEnvelope));

            //headers.forEach((key, value) -> log.debug(key + ":" + value));
            return ResponseEntity.ok(alexaAdapter.processAlexaRequest(requestEnvelope));
        } catch (Exception e) {
            log.error("Bad Request Exception {}", e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    private void verifyAlexaRequest(HttpServletRequest httpRequest) throws IOException {
        final Serializer serializer = new JacksonSerializer();
        byte[] serializedRequestEnvelope = IOUtils.toByteArray(httpRequest.getInputStream());
        final RequestEnvelope deserializedRequestEnvelope = serializer.deserialize(IOUtils.toString(
                serializedRequestEnvelope, ServletConstants.CHARACTER_ENCODING), RequestEnvelope.class);

        ServletRequest servletRequest = new ServletRequest(httpRequest, serializedRequestEnvelope, deserializedRequestEnvelope);
        SkillRequestSignatureVerifier skillRequestSignatureVerifier = new SkillRequestSignatureVerifier();
        skillRequestSignatureVerifier.verify(servletRequest);
    }

    /*private boolean validSignature(String signatureCertChainUrl) {
        try{
            URL myURL = new URL(signatureCertChainUrl);
            if(myURL.getPort()!=-1 && myURL.getPort()!=443)
                return false;
            if(!"s3.amazonaws.com".equals(myURL.getHost()))
                return false;
            if(!myURL.getPath().startsWith("/echo.api/"))
                return false;
            if(!myURL.getProtocol().equals("https"))
                return false;
        }catch (MalformedURLException mfe){
            return false;
        }
        return true;
    }


    private void getRequestEnvelop(HttpServletRequest httpRequest) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder builder = new StringBuilder();
        try {
            try (BufferedReader in = httpRequest.getReader()) {
                char[] buf = new char[4096];
                for (int len; (len = in.read(buf)) > 0; )
                    builder.append(buf, 0, len);
            }
        } catch (IOException e) {
            log.error("Invalid request {}", e.getMessage());
            //return null;
        }

        //return mapper.readValue(builder.toString(), RequestEnvelope.class);
    }*/
}

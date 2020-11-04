package com.spj.voice.alexa.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarberWaitTimeResponse implements Serializable {
    private String salonName;
    private String waitTime;
}


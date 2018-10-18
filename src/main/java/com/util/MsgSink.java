/*package com.util;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;


@EnableBinding(Sink.class)
public class MsgSink {

    @StreamListener(Sink.INPUT)
    public void process(Message<?> message) {
        System.out.println(message.getPayload());
    }
}
*/
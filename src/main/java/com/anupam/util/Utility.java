package com.anupam.util;

import com.anupam.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class Utility {
    private final static Logger LOG = LoggerFactory.getLogger(Utility.class);
    /***
     * This method convert JSON to Message object
     */
    public Message DBJSONToJava(String DBJson) {
        Message msg = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            msg = objectMapper.readValue(DBJson, Message.class);
        }
        catch (IOException e) {
            LOG.error("CONTACT_CHANGE_EVENT_ERROR: An exception occurred while converting JSON to a Java Object {} ", e.getMessage());
        }
        return msg;
    }
}

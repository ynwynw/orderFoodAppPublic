
package com.xlf.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageUtils {

    public static String getMessage(Integer mesType, String fromName, Object message) {
        try {
            ResultMessage result = new ResultMessage();
            result.setMesType(mesType).setMessage(message);
            if (fromName != null) {
                result.setFromName(fromName);
            }
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writeValueAsString(result));
            return mapper.writeValueAsString(result);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

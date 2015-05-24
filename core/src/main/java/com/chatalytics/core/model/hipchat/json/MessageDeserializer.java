package com.chatalytics.core.model.hipchat.json;

import com.chatalytics.core.model.Message;
import com.chatalytics.core.model.json.JsonChatDeserializer;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Deserializes hipchat {@link Message}s
 *
 * @author giannis
 *
 */
public class MessageDeserializer extends JsonChatDeserializer<Message> {

    private static final DateTimeFormatter MESSAGE_DTF = DateTimeFormat
        .forPattern("YYYY-MM-dd'T'HH:mm:ssZ");

    @Override
    public Message deserialize(JsonParser jp, DeserializationContext context) throws IOException,
            JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        DateTime date = MESSAGE_DTF.parseDateTime(node.get("date").asText());
        JsonNode fromStruct = node.get("from");
        String fromName = fromStruct.get("name").asText();
        String userIdStr = fromStruct.get("user_id").asText();
        String message = node.get("message").asText();

        return new Message(date, fromName, userIdStr, message, null);
    }

}

package com.ako.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ako.data.Authority;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class GrantedAuthorityDeserializer extends JsonDeserializer<Collection<GrantedAuthority>> {

@Override
public Collection<GrantedAuthority> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException, JsonProcessingException {

    JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    if (jsonNode.isArray()) {
        // you may have different serialization logic if you want
        for (JsonNode node : jsonNode) {
            authorities.add(new Authority(node.get("authority").asText()));
        }
    }

    return authorities;
  }
}

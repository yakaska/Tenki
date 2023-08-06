package ru.yakaska.tenki.payload.location.response;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.*;

import java.io.*;

public class WeatherResponseDeserializer extends StdDeserializer<WeatherResponse> {
    protected WeatherResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    public WeatherResponseDeserializer() {
        this(null);
    }

    @Override
    public WeatherResponse deserialize(JsonParser jp, DeserializationContext context) throws IOException, JacksonException {
        JsonNode rn = jp.getCodec().readTree(jp);

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setName(rn.get("name").asText());
        weatherResponse.setWeatherDescription(rn.get("weather").get(0).get("description").asText());
        weatherResponse.setTemperature(rn.get("main").get("temp").asDouble());
        weatherResponse.setLongitude(rn.get("coord").get("lon").asDouble());
        weatherResponse.setLatitude(rn.get("coord").get("lat").asDouble());
        return weatherResponse;
    }
}

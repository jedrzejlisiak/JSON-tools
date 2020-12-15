package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class JSONNegFilter extends JSONDecorator {
    private static final Logger logger = LoggerFactory.getLogger(JSONNegFilter.class);
    private final String fields;
    public JSONNegFilter(JSONComponent comp, String f) {super(comp); fields = f;}

    @Override
    public String decorate() { return decorateNeg(comp.decorate()); }

    public String decorateNeg(String json) {
        JsonFactory factory = new JsonFactory();
        JsonParser parser;
        try {
            parser = factory.createParser(json);
        } catch (Throwable e) {
            logger.debug(e.toString());
            return "{\"error\": \"Unable to parse JSON\"}";
        }
        String[] f = fields.split(",");
        boolean found = false; // variable used for determining if field to be excluded was found
        boolean inside = false; // to check if inside ignored array/object
        String result = "";
        JsonToken last = null;
        while(!parser.isClosed()){
            JsonToken token = null;
            try {
                token = parser.nextToken();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(JsonToken.START_ARRAY.equals(token)) {
                if(!found) {
                    result += "[";
                }
                else {
                    inside = true;
                }
            }
            else if(JsonToken.START_OBJECT.equals(token)){
                if(!found) {
                    result += "{";
                }
                else {
                    inside = true;
                }
            }
            else if(JsonToken.END_ARRAY.equals(token)) {
                if(!found) {
                    result += "]";
                }
                else {
                    found = false;
                    inside = false;
                }
            }
            else if(JsonToken.END_OBJECT.equals(token)) {
                if(!found) {
                    result += "}";
                }
                else {
                    found = false;
                    inside = false;
                }
            }
            else if(JsonToken.FIELD_NAME.equals(token)) {
                if(!found) {
                    if(JsonToken.VALUE_STRING.equals(last) || JsonToken.VALUE_TRUE.equals(last)
                            || JsonToken.VALUE_FALSE.equals(last) || JsonToken.VALUE_NUMBER_FLOAT.equals(last) ||
                            JsonToken.VALUE_NUMBER_INT.equals(last) || JsonToken.END_OBJECT.equals(last) ||
                    JsonToken.END_ARRAY.equals(last)){
                        result += ",";
                    }
                    String fieldName = null;
                    try {
                        fieldName = parser.getCurrentName();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < f.length; i++) {
                        if (Arrays.asList(f).contains(fieldName)) {
                            found = true;
                            break;
                        }
                    }


                    if(!found) {
                        result += "\"" + fieldName + "\":";
                    }
                }
            }
            else if(JsonToken.VALUE_STRING.equals(token) || JsonToken.VALUE_TRUE.equals(token)
            || JsonToken.VALUE_FALSE.equals(token) || JsonToken.VALUE_NUMBER_FLOAT.equals(token) ||
            JsonToken.VALUE_NUMBER_INT.equals(token)) {
                if(!found) {
                    String fieldName;
                    try {
                        fieldName = parser.getValueAsString();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "Error";
                    }

                    result += "\"" +fieldName;
                }
                else if(!inside) {
                        found = false;
                    }
                }

            last = token;
            }
        return result;
    }
}

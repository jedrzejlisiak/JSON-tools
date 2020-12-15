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
        String result = "";
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
            }
            else if(JsonToken.START_OBJECT.equals(token)){
                if(!found) {
                    result += "{";
                }
            }
            else if(JsonToken.END_ARRAY.equals(token)) {
                if(!found) {
                    result += "]";
                }
                else {
                    found = false;
                }
            }
            else if(JsonToken.END_OBJECT.equals(token)) {
                if(!found) {
                    result += "}";
                }
                else {
                    found = false;
                }
            }
            else if(JsonToken.FIELD_NAME.equals(token)) {
                if(!found) {
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
            else {

            }
        }

        return result;
    }
}

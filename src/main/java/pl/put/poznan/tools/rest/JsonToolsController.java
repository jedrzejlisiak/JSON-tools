package pl.put.poznan.tools.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.tools.logic.JsonTools;

import java.util.Arrays;


@RestController
@RequestMapping("/json")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@RequestBody JsonNode payload,
                      @RequestParam(value="transforms", defaultValue="upper,escape") String[] transforms) {

        // log the parameters
        logger.debug(payload.asText());
        logger.debug(Arrays.toString(transforms));

        // perform the transformation, you should run your logic here, below is just a silly example
        JsonTools transformer = new JsonTools(transforms);
        return payload.asText();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@RequestBody String payload,
                      @RequestParam(value="transforms", defaultValue="minify") String[] transforms) {
        // parse JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json;
        try {
            json = objectMapper.readTree(payload);

        } catch (JsonProcessingException e) {
            logger.debug(e.toString());
            return "Wrong JSON file";
        }
        // log the parameters
        logger.debug(payload);
        logger.debug(Arrays.toString(transforms));

        // perform the transformation, you should run your logic here, below is just a silly example
        JsonTools transformer = new JsonTools(transforms);
        return payload;
    }



}



package pl.put.poznan.tools.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.tools.logic.JSONComponent;
import pl.put.poznan.tools.logic.JSONComponentImp;
import pl.put.poznan.tools.logic.JSONDeminification;
import pl.put.poznan.tools.logic.JsonTools;

import java.util.Arrays;


@RestController
@RequestMapping("/json")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);
    private JsonNode json = null, json2 = null;
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@RequestParam(value="transforms", defaultValue="minify") String transforms,
                      @RequestParam(value="no", defaultValue="1") String no) {

        logger.debug("Download file number: "+no);
        logger.debug("Options: "+transforms);

        JsonTools tool = new JsonTools(json, json2);
        return tool.transform(transforms, no);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json")
    public String delete(@RequestParam(value="no", defaultValue="1") String no) {

        logger.debug("Deleting: "+ no);
        if(no.equals("1")){
            json = null;
            return "Deleted: "+no;
        }
        else if(no.equals("2")){
            json2 = null;
            return "Deleted: "+no;
        }
        else{
            logger.debug("Wrong request");
            return "Wrong request!";
        }

    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@RequestBody String payload,
                      @RequestParam(value="no", defaultValue="1") String no) {
        // parse JSON
        logger.debug(no);
        logger.debug(payload);
        if (no.equals("1")){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                json = objectMapper.readTree(payload);
                return "File read!";

            } catch (JsonProcessingException e) {
                logger.debug(e.toString());
                return "Wrong JSON file";
            }
        }
        else if(no.equals("2")){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                json2 = objectMapper.readTree(payload);
                return "File read!";

            } catch (JsonProcessingException e) {
                logger.debug(e.toString());
                return "Wrong JSON file";
            }
        }
        else{
            return "Wrong format";
        }
    }



}



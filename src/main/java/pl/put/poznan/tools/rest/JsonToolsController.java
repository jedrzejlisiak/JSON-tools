package main.java.pl.put.poznan.tools.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import main.java.pl.put.poznan.tools.logic.JsonTools;


@RestController
@RequestMapping("/json")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);
    private String json = null, json2 = null;
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@RequestParam(value="transforms", defaultValue="minify") String transforms,
                      @RequestParam(value="no", defaultValue="1") String no,
                      @RequestParam(value = "fields", defaultValue = "") String fields) {

        logger.debug("Download file number: "+no);
        logger.debug("Options: "+transforms);
        logger.debug("Chosen fields: "+fields);

        JsonTools tool = new JsonTools(json, json2);
        return tool.transform(transforms, no, fields);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json")
    public String delete(@RequestParam(value="no", defaultValue="1") String no) {

        logger.debug("Deleting: "+ no);
        if(no.equals("1")){
            json = null;
            return "{ \"status\" : 201, \"developerMessage\" : \"OK\", \"userMessage\" : \"File deleted.\"}";
        }
        else if(no.equals("2")){
            json2 = null;
            return "{ \"status\" : 201, \"developerMessage\" : \"OK\", \"userMessage\" : \"File deleted.\"}";
        }
        else{
            logger.debug("Wrong request");
            return "{ \"status\" : 404,\n" +
                    "\"developerMessage\" : \"Try accessing different file.\", \"userMessage\" : \"This file does not exist.\"}";
        }

    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@RequestBody String payload,
                      @RequestParam(value="no", defaultValue="1") String no) throws IOException {
        // parse JSON
        logger.debug(no);
        logger.debug(payload);
        if (no.equals("1")){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.readTree(payload);
                json = payload;
                return "{ \"status\" : 201, \"developerMessage\" : \"OK\", \"userMessage\" : \"File read.\"}";
            } catch (JsonProcessingException e) {
                logger.debug(e.toString());
                return "{ \"status\" : 415,\n" +
                        "\"developerMessage\" : \"Try file with JSON format.\", \"userMessage\" : \"This file is not JSON.\"}";
            }
        }
        else if(no.equals("2")){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.readTree(payload);
                json2 = payload;
                return "{ \"status\" : 201, \"developerMessage\" : \"OK\", \"userMessage\" : \"Filre read.\"}";

            } catch (JsonProcessingException e) {
                logger.debug(e.toString());
                return "{ \"status\" : 415,\n" +
                        "\"developerMessage\" : \"Try file with JSON format.\", \"userMessage\" : \"This file is not JSON.\"}";
            }
        }
        else{
            return "Wrong format";
        }
    }



}



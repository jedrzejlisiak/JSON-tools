package pl.put.poznan.tools.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import pl.put.poznan.tools.logic.JsonTools;


@RestController
@RequestMapping("/json")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);
    private final Hashtable<Integer, String> json = new Hashtable<Integer, String>();

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@RequestParam(value = "transforms", defaultValue = "minify") String transforms,
                      @RequestParam(value = "no", defaultValue = "1") String no,
                      @RequestParam(value = "fields", defaultValue = "") String fields) {

        logger.debug("Download file number: " + no);
        logger.debug("Options: " + transforms);
        logger.debug("Chosen fields: " + fields);

        String[] tmp = no.split(",");

        if(tmp.length > 2 || no.isEmpty()) {
            return "{ \"status\" : 400,\n" +
                    "\"developerMessage\" : \"You can process at most two files at a time.\",\n" +
                    "\"userMessage\" : \"Invalid no parameter.\"\n}";
        }

        int[] indexes = new int[2];
        indexes[0] = Integer.parseInt(tmp[0]);
        if(tmp.length == 2) {
            indexes[1] = Integer.parseInt(tmp[1]);
        }
        else {
            indexes[1] = indexes[0];
        }

        JsonTools tools = null;


        Enumeration<Integer> keys = json.keys();
        String j1 = null, j2 = null;

        while(keys.hasMoreElements()) {
            int k = keys.nextElement();
            if(k == indexes[0]) {
                j1 = json.get(indexes[0]);
            }
            if(k == indexes[1]) {
                j2 = json.get(indexes[1]);
            }
        }

        if(j1 != null && j2 != null) {
            tools = new JsonTools(j1, j2);
        } else {
            return "{ \"status\" : 400,\n" +
                    "\"developerMessage\" : \"No files found under sent no parameter.\",\n" +
                    "\"userMessage\" : \"Invalid no parameter.\"\n}";
        }

        return tools.transform(transforms, no, fields);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json")
    public String delete(@RequestParam(value = "no", defaultValue = "1") String no) {

        logger.debug("Deleting: " + no);

        if (no.split(",").length > 1) {
            return "{ \"status\" : 400,\n" +
                    "\"developerMessage\" : \"You can delete one file at a time.\",\n" +
                    "\"userMessage\" : \"Invalid no, it should be single integer\"\n}";
        }

        int ind = Integer.parseInt(no);
        Enumeration<Integer> keys = json.keys();

        while(keys.hasMoreElements()) {
            if(keys.nextElement().equals(ind)) {
                json.put(ind, "");
                return "{ \"status\" : 201,\n\"developerMessage\" : \"OK\",\n" +
                        "\"userMessage\" : \"File deleted.\"}";
            }
        }

        return "{ \"status\" : 400,\n" +
                "\"developerMessage\" : \"No such file.\",\n" +
                "\"userMessage\" : \"Invalid no, no file found under such no\"\n}";

    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@RequestBody String payload,
                       @RequestParam(value = "no", defaultValue = "1") String no) throws IOException {
        // parse JSON
        logger.debug(no);
        logger.debug(payload);

        if (no.split(",").length > 1) {
            return "{ \"status\" : 400,\n" +
                    "\"developerMessage\" : \"You can post one file at a time.\",\n" +
                    "\"userMessage\" : \"Invalid no, it should be single integer\"\n}";
        }

        int ind = Integer.parseInt(no);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readTree(payload);
            json.put(ind, payload);
            return "{ \"status\" : 201,\n\"developerMessage\" : \"OK\",\n" +
                    "\"userMessage\" : \"File read. It can be accessed at no " + no + "\"}";
        } catch (JsonProcessingException e) {
            logger.debug(e.toString());
            return "{ \"status\" : 415,\n" +
                    "\"developerMessage\" : \"Try file with JSON format.\",\n" +
                    "\"userMessage\" : \"This file is not JSON.\"\n}";
        }
    }
}



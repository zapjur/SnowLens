package Data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StorageHandler {
    private static final Logger logger = LogManager.getLogger(StorageHandler.class);
    public static void saveSetToFile(Map<String, Resort> map, String filename){
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Start of saving favorite resorts map to file: " + filename);
        try {
            mapper.writeValue(new File(filename), map);
            logger.info("Success saving favorite resorts map to file: " + filename);
        } catch (IOException e) {
            logger.error("Error while saving favorite resorts map to file: " + filename);
        }
    }

    public static Map<String, Resort> loadSetFromFile(String filename){
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Start of loading favorite resorts map from file: " + filename);
        Map<String, Resort> map = new HashMap<>();
        try {
            map = mapper.readValue(new File(filename), new TypeReference<Map<String, Resort>>() {});
            logger.info("Success loading favorite resorts map from file: " + filename);
        } catch (IOException e) {
            logger.error("Error while loading favorite resorts map from file: " + filename);
        }
        return map;
    }
}

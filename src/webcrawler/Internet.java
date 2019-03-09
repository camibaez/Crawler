/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The representation of the internet in order to retrieve the information inside pages.
 * @author User
 */
public class Internet {
    private  Map<String, List<String>> data = new HashMap<>();
    
    private Internet() {
        try {
            loadData();
        } catch (IOException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Internet getInstance() {
        return InternetHolder.INSTANCE;
    }
    
    private static class InternetHolder {

        private static final Internet INSTANCE = new Internet();
    }
    
    private void loadData() throws IOException{
        String jsonContent = new String(Files.readAllBytes(Paths.get("data/data.json")), StandardCharsets.UTF_8);
        JsonObject pageObject = new JsonParser().parse(jsonContent).getAsJsonObject();
        pageObject.getAsJsonArray("pages").forEach(element -> {
            JsonObject page = element.getAsJsonObject();
            String address = page.get("address").getAsString();
            
            ArrayList<String> links = new ArrayList<>();
            page.get("links").getAsJsonArray().forEach(link -> {
                links.add(link.getAsString());
            });
            
            data.put(address, links);
        
        });
    }
    
    public List<String> retriveContent(String address) throws Exception{
        List<String> content = data.get(address);
        if(content == null)
            throw new Exception("404 Error");
        return content;
    }
}

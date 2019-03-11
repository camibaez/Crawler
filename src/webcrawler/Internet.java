/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The representation of the Internet in order to retrieve the information inside pages.
 * @author User
 */
public class Internet {
    private static Internet INSTANCE;
    /**
     * Representation of the data contained in the json files.
     */
    private  Map<String, List<String>> data = new HashMap<>();
    
    /**
     * The first link in the links list. Only used in order to meet the project requirements.
     */
    private String homeAddress;
    
    private Internet(String dataAddress) {
        try {
            loadData(dataAddress);
        } catch (IOException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadInstance(String dataAddress){
        if(INSTANCE == null)
            INSTANCE = new Internet(dataAddress);
    }
    
    public static Internet getInstance() {
        if(INSTANCE == null){
            throw new RuntimeException("Internet must be loaded first.");
        }
        return INSTANCE;
    }
    
    /**
     * Loads the data contained in the json file.
     * @throws IOException 
     */
    private void loadData(String dataAddress) throws IOException{
        String jsonContent = new String(Files.readAllBytes(Paths.get(dataAddress)), StandardCharsets.UTF_8);
        JsonObject pageObject = new JsonParser().parse(jsonContent).getAsJsonObject();
        pageObject.getAsJsonArray("pages").forEach(element -> {
            JsonObject page = element.getAsJsonObject();
            String address = page.get("address").getAsString();
            
            ArrayList<String> links = new ArrayList<>();
            page.get("links").getAsJsonArray().forEach(link -> {
                links.add(link.getAsString());
            });
            
            data.put(address, links);
            
            if(homeAddress == null)
                homeAddress = address;
        
        });
    }
    
    public String getHomeAddress(){
        return homeAddress;
    }
    
    /**
     * Simulates an http request to a web server.
     * @param address
     * @return
     * @throws Exception 
     */
    public List<String> retriveContent(String address) throws Exception{
        List<String> content = data.get(address);
        if(content == null)
            throw new Exception("404 Error");
        return content;
    }
    
}

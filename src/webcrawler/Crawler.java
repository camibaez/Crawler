/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Crawler extends Thread{
    private boolean isAlive = true;
   
    protected CrawlingCentral cc = CrawlingCentral.getInstance();
    
    public void requestAddress(){
        String address = cc.retrievePendingPage();
        if(address == null){
            isAlive = false;
            return;
        }
            
        crawl(address);
    }
    public void crawl(String address){
        
        List<String> links;
        try {
            links = Internet.getInstance().retriveContent(address);
        } catch (Exception ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
            Logs.getInstance().getErrors().add(address);
            return;
        }
        
        for (String link : links) {
            if(!cc.getPagesVisited().contains(link))
                cc.getPagesPending().add(link);
            else
                //LOG purpose
                Logs.getInstance().getSkipped().add(link);
        }
        
        cc.getPagesVisited().add(address);
        //LOG purpose
        Logs.getInstance().getSucccess().add(address);
        
    }

    @Override
    public void run() {
       while(isAlive){
           requestAddress();
       }
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Crawler extends Thread{
    protected boolean isAlive = true;
    protected String id;
   
    protected CrawlingCentral cc;

    public Crawler(CrawlingCentral crawlingCentral) {
        cc = crawlingCentral;
        id = this.toString();
    }
    
    
    
    public void requestAddress(){
        System.out.println(id + ": requesting pending page to central...");
        
        String address = cc.retrievePendingPage();
        if(address == null){
            System.out.println(id + ": Not pending page available. :(");
            
            cc.requestTermination(this);
            isAlive = false;
            return;
        }
            
        System.out.printf("%s: Pending page (%s) obtained. \nReady to crawl. :)\n", id, address);
        crawl(address);
    }
    protected void crawl(String address){
        cc.getPagesVisited().add(address);
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
        
        
        //LOG purpose
        Logs.getInstance().getSucccess().add(address);
        
    }

    @Override
    public void run() {
       while(isAlive){
           try {
               Thread.sleep(500);
           } catch (InterruptedException ex) {
               Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
           }
           requestAddress();
       }
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

/**
 *
 * @author User
 */
public class WebCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*Internet.loadInstance("data/internet1.json");
        
        new CrawlingCentral().start();*/
        
        Internet.loadInstance("data/internet2.json");
        
        new CrawlingCentral().start();
        
    }
    
}

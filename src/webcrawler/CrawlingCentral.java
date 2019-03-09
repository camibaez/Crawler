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
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author User
 */
public class CrawlingCentral {
    private static CrawlingCentral INSTANCE;
    
    protected Set<Crawler> activeCrawlers = new HashSet<>();
    
    private SortedSet<String> pagesPending = new TreeSet<String>();
    private Set<String> pagesVisited= new HashSet<String>();
    
    public CrawlingCentral(int crawlersCount) {
        
        
        
        for(int i = 0; i < crawlersCount; i++){
            activeCrawlers.add(new Crawler());
        }

    }
    
    public static CrawlingCentral getInstance() {
        if(INSTANCE == null){
            INSTANCE = new CrawlingCentral(3);
        }
        return INSTANCE;
    }

    
    public synchronized String retrievePendingPage(){
        String page = pagesPending.first();
        pagesPending.remove(page);
        return page;
    }
    /**
     * @return the pagesVisited
     */
    public Set<String> getPagesVisited() {
        return pagesVisited;
    }

    /**
     * @return the pagesPending
     */
    public Set<String> getPagesPending() {
        
        return pagesPending;
    }
    
    private static class CrawlingCentralHolder {

        
    }
    
    
    protected void initCrawler(){
        Crawler crawler = new Crawler();
    }
}

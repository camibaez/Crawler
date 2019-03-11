/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import com.sun.webkit.ThemeClient;
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
public class CrawlingCentral extends Thread {

    protected Set<Crawler> activeCrawlers = new HashSet<>();

    private SortedSet<String> pagesPending = new TreeSet<String>();
    private Set<String> pagesVisited = new HashSet<String>();

    public CrawlingCentral() {
        pagesPending.add(Internet.getInstance().generateRandomSeedAddress());

    }

    protected Crawler createCrawler() {
        Crawler crawler = new Crawler(this);
        activeCrawlers.add(crawler);
        crawler.start();
        return crawler;
    }

    @Override
    public void run() {
        while (true) {
            if (pagesPending.isEmpty() && activeCrawlers.isEmpty()) {
                System.out.println("-----------------------");
                System.out.println("Crawling done! Showing Results:");
                Logs.getInstance().showResults();
                break;
            }
            checkCrawlersDistribution();
        }
        
        
    }

    protected void checkCrawlersDistribution() {
        int difference = (int) (Math.ceil(pagesPending.size() / 2.0) - activeCrawlers.size());
        if (difference > 0) {
            for (int i = 0; i < difference; i++) {
                createCrawler();
            }
        }
    }

    public synchronized void requestTermination(Crawler crawler) {
        activeCrawlers.remove(crawler);

    }

    public synchronized String retrievePendingPage() {
        if (pagesPending.isEmpty()) {
            return null;
        } else {
            String page = pagesPending.first();
            pagesPending.remove(page);
            return page;
        }

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
    public  Set<String> getPagesPending() {

        return pagesPending;
    }

}

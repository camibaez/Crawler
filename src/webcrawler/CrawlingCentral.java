package webcrawler;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class have the duty to coordinate the different crawlers.
 * @author Camilo Baez
 */
public class CrawlingCentral {

    protected Set<Crawler> activeCrawlers = new HashSet<>();

    private SortedSet<String> pagesPending = new TreeSet<String>();
    private Set<String> pagesVisited = new HashSet<String>();

    public CrawlingCentral() {
        pagesPending.add(Internet.getInstance().getHomeAddress());
    }

    protected Crawler createCrawler() {
        Crawler crawler = new Crawler(this);
        activeCrawlers.add(crawler);
        crawler.start();
        return crawler;
    }

    
    public void run() {
        System.out.println("Initializing Crawling...");
        while (true) {
            //The central ends once all the crawlers have finished their pending tasks.
            if (pagesPending.isEmpty() && activeCrawlers.isEmpty()) {
                
                break;
            }
            checkCrawlersDistribution();
        }
    }

    /**
     * Loads the crawlers depending on the amount of pending pages to visit using a defined algorithm.
     * 
     */
    protected void checkCrawlersDistribution() {
        int difference = (int) (Math.ceil(pagesPending.size() / 2.0) - activeCrawlers.size());
        if (difference > 0) {
            for (int i = 0; i < difference; i++) {
                createCrawler();
            }
        }
    }

    /**
     * A requests from a crawler to the central once its tasks its done.
     * @param crawler 
     */
    public synchronized void requestTermination(Crawler crawler) {
        activeCrawlers.remove(crawler);

    }

    /**
     * Retrieve the first pending page. Simulates a Queue.
     * @return 
     */
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
    public Set<String> getPagesPending() {

        return pagesPending;
    }

}

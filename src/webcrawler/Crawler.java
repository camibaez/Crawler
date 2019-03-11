package webcrawler;

import java.util.List;

/**
 * This class represents the crawler.
 * @author Camilo Baez
 */
public class Crawler extends Thread {
    protected boolean isAlive = true;
    protected String id;

    protected CrawlingCentral cc;

    public Crawler(CrawlingCentral crawlingCentral) {
        cc = crawlingCentral;
        id = this.toString();
    }

    /**
     * Request a pending address to the central in order to crawl the content. 
     */
    public void requestAddress() {
        String address = cc.retrievePendingPage();
        
        if (address == null) {
            cc.requestTermination(this);
            isAlive = false;
            return;
        }

        if (cc.getPagesVisited().contains(address)) {
            Logs.getInstance().getSkipped().add(address);
            return;
        }
        crawl(address);
    }

    /**
     * Encapsulates the crawling process.
     * @param address 
     */
    protected void crawl(String address) {
        cc.getPagesVisited().add(address);
        List<String> links;
        try {
            links = Internet.getInstance().retriveContent(address);
        } catch (Exception ex) {
            Logs.getInstance().getErrors().add(address);
            return;
        }

        links.forEach((link) -> {
            if (!cc.getPagesVisited().contains(link)) 
            {
                cc.getPagesPending().add(link);
            } else {
                //LOG purpose
                Logs.getInstance().getSkipped().add(link);
            }
        });

        //LOG purpose
        Logs.getInstance().getSucccess().add(address);

    }

    @Override
    public void run() {
        while (isAlive) {
            requestAddress();
        }
    }

}

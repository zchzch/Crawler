package com.xyz.quant.guba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Created by Think on 2016/7/31.
 */
public class QuantController {
  private static final Logger logger = LoggerFactory.getLogger(QuantController.class);
  private static final String crawlerStorageFlod = "D:/crawl/Data";
  private static  int numberOfCrawler = 5;
  public static void main(String[] args) throws Exception{
    CrawlConfig crawlConfig = new CrawlConfig();
    crawlConfig.setCrawlStorageFolder(crawlerStorageFlod);
    crawlConfig.setPolitenessDelay(200);
    crawlConfig.setMaxDepthOfCrawling(2);
    crawlConfig.setMaxPagesToFetch(1000);
    crawlConfig.setIncludeBinaryContentInCrawling(false);
    crawlConfig.setResumableCrawling(false);
    PageFetcher pageFetcher = new PageFetcher(crawlConfig);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller = new CrawlController(crawlConfig, pageFetcher, robotstxtServer);

    controller.addSeed("http://guba.eastmoney.com");
    //controller.addSeed("http://iguba.eastmoney.com/action.aspx?callback=jQuery18303825072920477828_1469962616842&action=opopstock&code=600780&_=1469962616900");
    controller.start(QuantCrawler.class, numberOfCrawler);
  }
}

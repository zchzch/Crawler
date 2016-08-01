package com.xyz.quant.guba;

import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.Header;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.select.Elements;


/**
 * Created by zch on 2016/7/31.
 */
public class QuantCrawler extends WebCrawler {
  private static final Pattern IMAGE_EXTENSTIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");

  @Override
  public boolean shouldVisit(Page referringPage, WebURL url){
    String href = url.getURL().toLowerCase();

    //图片不爬
    if(this.IMAGE_EXTENSTIONS.matcher(href).matches()){
      return false;
    }
    //只爬股吧股票首页
    return href.startsWith("http://guba.eastmoney.com/list,");
//    return href.startsWith("http://iguba.eastmoney.com");
  }
  @Override
  public  void visit(Page page){
    int docid = page.getWebURL().getDocid();
    String url = page.getWebURL().getURL();
    String domain = page.getWebURL().getDomain();
    String path = page.getWebURL().getPath();
    String subDomain = page.getWebURL().getSubDomain();
    String parentUrl = page.getWebURL().getParentUrl();
    String anchor = page.getWebURL().getAnchor();

    logger.debug("Docid: {}", docid);
    logger.info("URL: {}", url);
    logger.debug("Domain: '{}'", domain);
    logger.debug("Sub-domain: '{}'", subDomain);
    logger.debug("Path: '{}'", path);
    logger.debug("Parent page: {}", parentUrl);
    logger.debug("Anchor text: {}", anchor);
    //System.out.println(url);
//    System.out.println(page.getContentData());
    if (page.getParseData() instanceof HtmlParseData) {
      HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
      String text = htmlParseData.getText();
      String html = htmlParseData.getHtml();
      Set<WebURL> links = htmlParseData.getOutgoingUrls();

      logger.debug("Text length: {}", text.length());
      logger.debug("Html length: {}", html.length());
      logger.debug("Number of outgoing links: {}", links.size());
      //System.out.println(html);
      Document document = Jsoup.parse(html);
     // Element element = document.getElementById("articlelistnew");
     // System.out.println(element);
     // System.out.println(document.select("div.articleh"));
      System.out.println(document.getElementsByTag("a").attr("title"));
      //http://iguba.eastmoney.com/action.aspx?callback=jQuery18303825072920477828_1469962616842&action=opopstock&code=600780&_=1469962616900
      //jQuery18303825072920477828_1469962616842({"data":{"gn":"通宝能源","following":"526946","sfgzgg":false,"sfscgg":false,"sfgd":false,"sczs":526946,"hyyh":[]}})
    }

    Header[] responseHeaders = page.getFetchResponseHeaders();
    if (responseHeaders != null) {
      logger.debug("Response headers:");
      for (Header header : responseHeaders) {
        logger.debug("\t{}: {}", header.getName(), header.getValue());
      }
    }

    logger.debug("=============");

  }


}

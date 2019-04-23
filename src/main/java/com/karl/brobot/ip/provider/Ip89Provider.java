package com.karl.brobot.ip.provider;

import com.karl.brobot.ip.IpInfo;
import com.karl.brobot.ip.IpProvider;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author karl
 * @version 2019-04-23
 */
@Slf4j
public class Ip89Provider implements IpProvider {

    @Override
    public String name() {
        return "89ip";
    }

    @Override
    public String url() {
        return "http://www.89ip.cn/";
    }

    @Override
    public String id() {
        return name();
    }

    @Override
    public List<IpInfo> provide() throws Exception {
        int page = getPage();
        final String url = getUrl(page);
        final Document document = Jsoup.connect(url).get();
        final Elements tbody = document.getElementsByTag("tbody");
        if (tbody == null || tbody.size() == 0) {
            log.warn("找不到数据");
            return null;
        }
        final Element element = tbody.get(0);
        List<IpInfo> ipInfos = new ArrayList<>();

        final Elements trs = element.getElementsByTag("tr");
        for (Element tr : trs) {
            final Elements td = tr.getElementsByTag("td");
            if (td.size() < 4) {
                continue;
            }
            String host = td.get(0).text().trim();
            String port = td.get(1).text().trim();
            String address = td.get(2).text().trim();
            String isp = td.get(3).text().trim();
            long time = System.currentTimeMillis();
            ipInfos.add(new IpInfo()
                    .setHost(host)
                    .setPort(Integer.parseInt(port))
                    .setAddress(address)
                    .setIsp(isp)
                    .setProvider(isp)
                    .setCreateTime(time));
        }

        return ipInfos;
    }

    /**
     * 随机访问1~20页数据
     * @return
     */
    private int getPage() {
        return (int) (1 + Math.random() * (20 - 1 + 1));
    }

    /**
     * 获取访问url
     *
     * @param page
     * @return
     */
    private String getUrl(int page) {
        return String.format(url() + "index_%s.html", page);
    }
}

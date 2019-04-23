package com.karl.brobot.ip.provider;

import com.karl.brobot.ip.IpInfo;
import com.karl.brobot.ip.IpProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 国内高匿代理-xicidaili
 *
 * @author karl
 * @version 2019-04-23
 */
public class XicidailiIpProvider implements IpProvider {
    @Override
    public String name() {
        return "国内高匿代理-xicidaili";
    }

    @Override
    public String url() {
        return "https://www.xicidaili.com/nn/";
    }

    @Override
    public String id() {
        return "xicidaili";
    }

    @Override
    public List<IpInfo> provide() throws Exception {
        int page = getPage();
        final String url = getUrl(page);
        final Document document = Jsoup.connect(url).get();
        final Element table = document.getElementById("ip_list");
        final Elements trs = table.getElementsByTag("tr");
        if (trs.size() <= 1) {
            return null;
        }
        int idx = 0;
        List<IpInfo> list = new ArrayList<>();
        for (Element tr : trs) {
            idx++;
            if (idx == 1) {
                continue;
            }

            final Elements tds = tr.getElementsByTag("td");
            if (tds.size() <= 9) {
                continue;
            }
            final String host = tds.get(1).text().trim();
            final Integer port = Integer.parseInt(tds.get(2).text().trim());
            final String address = tds.get(3).getElementsByTag("a").get(0).text().trim();
            final String provider = tds.get(5).text().trim();
            final String time = tds.get(8).text().trim();
            if (!time.contains("天")) {
                continue;
            }
            list.add(new IpInfo().setHost(host).setPort(port).setAddress(address).setProvider(provider));
        }
        return list;
    }

    /**
     * 随机访问1~20页数据
     *
     * @return
     */
    private int getPage() {
        return (int) (1 + Math.random() * (60 - 1 + 1));
    }

    /**
     * 获取访问url
     *
     * @param page
     * @return
     */
    private String getUrl(int page) {
        return String.format(url() + "%s", page);
    }
}

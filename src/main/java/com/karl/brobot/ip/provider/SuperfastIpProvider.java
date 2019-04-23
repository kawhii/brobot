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
 * 极速代理-superfastip
 *
 * @author karl
 * @version 2019-04-23
 */
public class SuperfastIpProvider implements IpProvider {
    @Override
    public String name() {
        return "极速代理-superfastip";
    }

    @Override
    public String url() {
        return "http://www.superfastip.com/welcome/freeip";
    }

    @Override
    public String id() {
        return "superfastip";
    }

    @Override
    public List<IpInfo> provide() throws Exception {
        int page = getPage();
        final String url = getUrl(page);
        final Document document = Jsoup.connect(url).get();
        final Elements tbody = document.getElementsByTag("tbody");
        if (tbody.size() < 2) {
            return null;
        }
        final Element element = tbody.get(1);
        final Elements trs = element.getElementsByTag("tr");
        List<IpInfo> list = new ArrayList<>();

        for (Element tr : trs) {
            final Elements td = tr.getElementsByTag("td");
            if(td.size() <= 6) {
                continue;
            }
            final String host = td.get(0).text().trim();
            final Integer port = Integer.parseInt(td.get(1).text().trim());
            final String provider = td.get(3).text().trim();
            final String address = td.get(4).text().trim();
            list.add(new IpInfo().setHost(host).setPort(port).setProvider(provider).setAddress(address));
        }
        return list;
    }

    /**
     * 获取访问url
     *
     * @param page
     * @return
     */
    private String getUrl(int page) {
        return String.format(url() + "/%s", page);
    }

    /**
     * 随机访问1~20页数据
     *
     * @return
     */
    private int getPage() {
        return (int) (1 + Math.random() * (3 - 1 + 1));
    }
}

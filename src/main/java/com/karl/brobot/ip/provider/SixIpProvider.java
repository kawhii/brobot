package com.karl.brobot.ip.provider;

import com.karl.brobot.ip.IpInfo;
import com.karl.brobot.ip.IpProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author karl
 * @version 2019-04-23
 */
@Component
public class SixIpProvider implements IpProvider {
    @Override
    public String name() {
        return "66免费代理网";
    }

    @Override
    public String url() {
        return "http://www.66ip.cn";
    }

    @Override
    public String id() {
        return "66ip";
    }

    @Override
    public List<IpInfo> provide() throws Exception {
        String url = url() + "/areaindex_35/index.html";
        final Document document = Jsoup.connect(url).get();
        final Elements table = document.getElementsByTag("table");
        if (table.size() < 3) {
            return null;
        }
        final Element dataEl = table.get(2);
        final Elements trs = dataEl.getElementsByTag("tr");
        int index = 0;
        List<IpInfo> list = new ArrayList<>();

        for (Element tr : trs) {
            index++;
            if(index == 1) {
                continue;
            }
            if(index == 80) {
                break;
            }
            final Elements td = tr.getElementsByTag("td");
            if(td.size() <= 4) {
                continue;
            }
            final String host = td.get(0).text().trim();
            final Integer port = Integer.parseInt(td.get(1).text().trim());
            final String address = td.get(2).text().trim();
            list.add(new IpInfo().setHost(host).setPort(port).setAddress(address));
        }

        return list;
    }
}

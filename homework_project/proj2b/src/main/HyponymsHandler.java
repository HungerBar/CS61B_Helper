package main;

import HelperClass.DealWithTxt;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;


import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    public DealWithTxt g;
    public HyponymsHandler(DealWithTxt graph) {
        g = graph;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        String[] p = new String[words.size()];
        for (int i = 0; i < words.size(); i++) {
            p[i] = words.get(i);
        }
        String response = "";
        List<String> response_list = g.orderBothhyponyms(p);

        response = response_list.toString();
        return response;
    }
}

package main;

import HelperClass.DealWithTxt;
import browser.NgordnetQueryHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        DealWithTxt graph = new DealWithTxt(synsetFile, hyponymFile);
        return new HyponymsHandler(graph);
    }
}

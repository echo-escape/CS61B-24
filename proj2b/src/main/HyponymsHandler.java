package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import graph.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    private DataHandler dataHandler;

    public HyponymsHandler(String synsetFilePath, String hyponymsFilePath) {
        dataHandler = new DataHandler(synsetFilePath, hyponymsFilePath);
        dataHandler.read();
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> queryWords = q.words();
        List<String> commonHyponyms = new ArrayList<>();
        for (String queryWord : queryWords) {
            List<String> currentHyponymsForCurrent = dataHandler.search(queryWord);
            if (commonHyponyms.isEmpty()) {
                commonHyponyms.addAll(currentHyponymsForCurrent);
            }
            else {
                commonHyponyms.retainAll(currentHyponymsForCurrent);
            }
        }
        Collections.sort(commonHyponyms);
        return commonHyponyms.toString();
    }
}

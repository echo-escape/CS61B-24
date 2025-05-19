package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import graph.*;
import ngrams.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final DataHandler dataHandler;
    private final NGramMap ngramMap;

    public HyponymsHandler(String wordsFilePath, String countsFilePath,String synsetFilePath, String hyponymsFilePath) {
        dataHandler = new DataHandler(synsetFilePath, hyponymsFilePath);
        dataHandler.read();
        ngramMap = new NGramMap(wordsFilePath, countsFilePath);
    }

    @Override
    public String handle(NgordnetQuery q) {
        int k = q.k();
        int endYear = q.endYear();
        int startYear = q.startYear();
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
        TotalCountsSort sort = new TotalCountsSort(commonHyponyms, ngramMap, k, endYear, startYear);
        List<String> results = sort.sort();
        Collections.sort(results);
        return results.toString();
    }
}

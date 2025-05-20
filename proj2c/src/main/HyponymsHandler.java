package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import browser.NgordnetQueryType;
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
        NgordnetQueryType type = q.ngordnetQueryType();
        int k = q.k();
        int endYear = q.endYear();
        int startYear = q.startYear();
        List<String> queryWords = q.words();
        List<String> results = new ArrayList<>();
        if (type == NgordnetQueryType.HYPONYMS) {
           results = dealCommonHyponyms(queryWords, k, endYear, startYear);
        }
        else if (type == NgordnetQueryType.ANCESTORS) {
            results = dealCommonAncestors(queryWords, k, endYear, startYear);
        }
        Collections.sort(results);
        return results.toString();
    }

    private List<String> dealCommonHyponyms(List<String> queryWords, int k, int endYear, int startYear) {
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
        return sort.sort();
    }

    private List<String> dealCommonAncestors(List<String> queryWords, int k, int endYear, int startYear) {
        List<String> commonAncestors = new ArrayList<>();
        for (String queryWord : queryWords) {
            List<String> commonAncestorsForCurrent = dataHandler.searchAncestors(queryWord);
            if (commonAncestors.isEmpty()) {
                commonAncestors.addAll(commonAncestorsForCurrent);
            }
            else {
                commonAncestors.retainAll(commonAncestorsForCurrent);
            }
        }
        return commonAncestors;
    }
}

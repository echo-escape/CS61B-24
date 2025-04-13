package main;


import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.*;
import java.util.*;



public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap ngramMap;

    public HistoryTextHandler(NGramMap map) {
        ngramMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";
        for (String word : words) {
            response += word + ": {";
            TimeSeries result = ngramMap.weightHistory(word, startYear, endYear);
            if (result == null) {
                throw new IllegalArgumentException("No such word: " + word);
            }
            for (int i = startYear; i < endYear; i++) {
                if (result.containsKey(i)) {
                    Double temp = result.get(i);
                    if (temp == null) {
                        temp = 0.0;
                    }
                    response += i + "=" + temp + ", ";
                }
            }
            Double temp = result.get(endYear);
            if (temp == null) {
                temp = 0.0;
            }
            response += endYear + "=" + temp + "}\n";
        }
        return response;
    }
}

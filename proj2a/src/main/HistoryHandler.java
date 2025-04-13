package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChartBuilder;
import plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;


public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngramMap;

    public HistoryHandler(NGramMap map) {
        ngramMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<TimeSeries> series = new ArrayList<>();
        int startYear = q.startYear();
        int endYear = q.endYear();

        for (String word : q.words()) {
            words.add(word);
        }

        for (String word : words) {
            series.add(ngramMap.weightHistory(word, startYear, endYear));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(words, series);
        String encodeImage = Plotter.encodeChartAsString(chart);

        return encodeImage;
    }
}


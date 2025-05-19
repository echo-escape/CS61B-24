package ngrams;

import java.util.*;

public class TotalCountsSort {
    private final List<String> result;
    private final Map<Integer, String> map;
    private Map<Integer, Integer> counts;
    private final List<Integer> countsSorted;
    private final int k;
    private final NGramMap ngramMap;
    private final int endYear;
    private final int startYear;

    public TotalCountsSort(List<String> words, NGramMap ngramMap, int k, int endYear, int startYear) {
        this.endYear = endYear;
        this.startYear = startYear;
        this.k = k;
        int n = words.size();
        result = new ArrayList<String>();
        map = new HashMap<Integer, String>();
        for (int i = 0; i < n; i++) {
            map.put(i, words.get(i));
        }
        countsSorted = new ArrayList<>();
        this.ngramMap = ngramMap;
    }

    private void sortCountedSorted() {
        read();
        for (int i = 0; i < countsSorted.size(); i++) {
            for (int j = i; j < countsSorted.size() - 1; j++) {
                Integer valAtIndexJ = countsSorted.get(j);
                Integer valAtIndexJPlus1 = countsSorted.get(j + 1);
                if (counts.get(valAtIndexJ) < counts.get(valAtIndexJPlus1)) { // 降序排序
                    countsSorted.set(j, valAtIndexJPlus1);
                    countsSorted.set(j + 1, valAtIndexJ);
                }
            }
        }
    }

    private void read() {
        counts = new HashMap<>();
        for (Integer i : map.keySet()) {
            TimeSeries ts = ngramMap.countHistory(map.get(i), startYear, endYear);
            if (!ts.isEmpty()) {
                List<Double> data = ts.data();
                int sum = 0;
                for (Double datum : data) {
                    sum += datum;
                }
                counts.put(i, sum);
                countsSorted.add(i);
            }
        }
    }


    public List<String> sort() {
        sortCountedSorted();
        int count = 0;
        if (k == 0) {
            return result;
        }
        for (int j : countsSorted) {
            if (count == k) {
                break;
            }
            result.add(map.get(j));
            count++;
        }
        return result;
    }


}

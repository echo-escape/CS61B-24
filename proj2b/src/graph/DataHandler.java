package graph;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class DataHandler {
    private final In synsetsAddress;
    private final In hyponymsAddress;
    private Map<Integer, Set<String>> synsetMap;
    private MyDirectedGraph<Set> graph;
    private Set<String> result;

    public DataHandler(String synsetsAddress, String hyponymsAddress) {
        this.synsetsAddress = new In(synsetsAddress);
        this.hyponymsAddress = new In(hyponymsAddress);
    }

    public void read() {
        while (!synsetsAddress.isEmpty()) {
            synsetMap = new TreeMap<>();
            String nextLine = synsetsAddress.readLine();
            String[] words = nextLine.split(",");
            int wordId = Integer.parseInt(words[0]);
            String[] wordsUnsplit = words[1].split(" ");
            Set<String> wordsSet = new HashSet<>(Arrays.asList(wordsUnsplit));
            synsetMap.put(wordId, wordsSet);
        }

        while (!hyponymsAddress.isEmpty()) {
            graph = new MyDirectedGraph();
            String nextLine = hyponymsAddress.readLine();
            String[] wordsId = nextLine.split(",");
            Set<String> firstWord = synsetMap.get(Integer.parseInt(wordsId[0]));
            graph.addVertex(firstWord);
            for (int i = 1; i < wordsId.length; i++) {
                Set<String> nextWord = synsetMap.get(Integer.parseInt(wordsId[i]));
                graph.addEdge(firstWord, nextWord);
            }
        }
    }

    public List<String> search(String target) {
        result = new TreeSet<>();

        Queue<Set<String>> queue = new LinkedList<>();
        Set<Set<String>> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Set<String> words = queue.poll();
            if (words.contains(target)) {
                Set<String> temp = graphTraversal(words);
                result.addAll(temp);
            }
        }

        List<String> newResult = new ArrayList<>(result);
        Collections.sort(newResult);
        return newResult;

    }

    private Set<String> graphTraversal(Set<String> words) {

    }

}

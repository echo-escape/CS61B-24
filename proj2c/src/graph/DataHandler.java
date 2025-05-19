package graph;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class DataHandler {
    private final In synsetsAddress;
    private final In hyponymsAddress;
    private Map<Integer, Set<String>> synsetMap;
    private MyDirectedGraph<Integer> graph;
    private Set<String> result;
    private Set<Integer> visitedSet;

    public DataHandler(String synsetsAddress, String hyponymsAddress) {
        this.synsetsAddress = new In(synsetsAddress);
        this.hyponymsAddress = new In(hyponymsAddress);
    }

    public void read() {
        synsetMap = new TreeMap<>();
        while (!synsetsAddress.isEmpty()) {
            String nextLine = synsetsAddress.readLine();
            String[] words = nextLine.split(",");
            int wordId = Integer.parseInt(words[0]);
            String[] wordsUnsplit = words[1].split(" ");
            Set<String> wordsSet = new HashSet<>(Arrays.asList(wordsUnsplit));
            synsetMap.put(wordId, wordsSet);
        }

        graph = new MyDirectedGraph<Integer>();
        for (Integer wordId : synsetMap.keySet()) {
            graph.addVertex(wordId);
        }
        while (!hyponymsAddress.isEmpty()) {
            String nextLine = hyponymsAddress.readLine();
            String[] wordsId = nextLine.split(",");
            Integer parentId = Integer.parseInt(wordsId[0]);
            for (int i = 1; i < wordsId.length; i++) {
                Integer childId = Integer.parseInt(wordsId[i]);
                graph.addEdge(parentId, childId);
            }
        }
    }

    public List<String> search(String target) {
        visitedSet = new HashSet<>();
        result = new TreeSet<>();

        Set<Integer> allNodes = graph.getVertices();
        for (Integer node : allNodes) {
            Set<String> nodeSet = synsetMap.get(node);
            if (!visitedSet.contains(node) && nodeSet.contains(target)) {
                graphTraversal(node);
            }
        }

        return new ArrayList<>(result);

    }

    private void graphTraversal(Integer wordId) {
        Queue<Integer> fringe = new LinkedList<>();

        fringe.add(wordId);
        visitedSet.add(wordId);

        while (!fringe.isEmpty()) {
            Integer node = fringe.poll();
            result.addAll(synsetMap.get(node));
            for (Integer neighbor : graph.getNeighbors(node)) {
                if (!visitedSet.contains(neighbor)) {
                    fringe.add(neighbor);
                    visitedSet.add(neighbor);
                }
            }
        }
    }

}

package graph;

import java.util.*;
public class MyDirectedGraph<T> {
    private final Map<T, List<T>> adjacencyList;

    public MyDirectedGraph() {
        adjacencyList = new HashMap<>();
    }

    public void addVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
        }
    }

    public void addEdge(T vertex1, T vertex2) {
        addVertex(vertex1);
        addVertex(vertex2);

        List<T> neighbors = adjacencyList.get(vertex1);
        if (neighbors == null) {
            neighbors = new ArrayList<>();
        }
        neighbors.add(vertex2);
    }

    private List<T> getParents(T vertex) {
        List<T> parents = new ArrayList<>();
        for (T parent : getVertices()) {
            if (adjacencyList.get(parent).contains(vertex)) {
                parents.add(parent);
            }
        }
        return parents;
    }

    public List<T> getAllAncestors(T vertex) {
        Set<T> allAncestors = new HashSet<>();
        allAncestors.addAll(getAllAncestorsRecursively(vertex, allAncestors));
        return new ArrayList<>(allAncestors);

    }

    private Set<T> getAllAncestorsRecursively(T vertex, Set<T> allAncestors) {
        List<T> parents = getParents(vertex);
        if (parents.isEmpty()) {
            return allAncestors;
        }
        for (T parent : parents) {
            if (allAncestors.add(parent)) {
                getAllAncestorsRecursively(parent, allAncestors);
            }

        }
        return allAncestors;
    }

    public Set<T> getVertices() {
        return adjacencyList.keySet();
    }

    Iterable<T> getNeighbors(T vertex) {
        return adjacencyList.get(vertex);
    }

    public void bfs(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            return;
        }

        Queue<T> fringe = new LinkedList<>();
        Set<T> visited = new HashSet<>();

        fringe.add(vertex);
        visited.add(vertex);

        while (!fringe.isEmpty()) {
            T currentNode = fringe.poll();
            System.out.print(currentNode + " ");
            for (T neighbor : getNeighbors(currentNode)) {
                if (!visited.contains(neighbor)) {
                    fringe.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }

    public void dfs(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            return;
        }
        Set<T> visited = new HashSet<>();

        dfsRecursive(vertex, visited);
        System.out.println();
    }

    private void dfsRecursive(T currentNode, Set<T> visited) {
        visited.add(currentNode);
        System.out.print(currentNode + " ");
        for (T neighbor : getNeighbors(currentNode)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited);
                System.out.println();
            }
        }
    }


}

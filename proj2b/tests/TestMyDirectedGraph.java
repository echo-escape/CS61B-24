import java.util.*;
import graph.*;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class TestMyDirectedGraph {
    @Test
    public void test() {
        MyDirectedGraph<String> graph = new MyDirectedGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("A", "D");
        graph.addEdge("B", "C");
        graph.addEdge("B", "D");
        graph.bfs("A");
    }
}

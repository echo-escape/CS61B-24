public class UnionFind {
    // TODO: Instance variables
    int[] parent;
    private int total;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        total = N;
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        int root = find(v);
        int size = parent[root];
        return -size;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        return find(v);
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if (parent[v1] < 0 || parent[v2] < 0) {
            return false;
        }
        int root1 = find(v1);
        int root2 = find(v2);
        return root1 == root2;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if (v < 0 || v >= total) {
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }
        int root = v;
        while (parent[root] >= 0) {
            root = parent[root];
        }
        while (v != root) { // 如果v就是root,那么永远不可能，第二次就会变为-1
            int newParent = parent[v];
            parent[v] = root;
            v = newParent;
        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if (connected(v1, v2) || v1 == v2) {
            return;
        }
        int root1 = find(v1);
        int weight1 = sizeOf(root1);
        int root2 = find(v2);
        int weight2 = sizeOf(root2);
        if (weight1 < weight2) {
            weight2 += weight1;
            parent[root2] = -weight2;
            parent[root1] = root2;
        }
        else if (weight2 < weight1) {
            weight1 += weight2;
            parent[root1] = -weight1;
            parent[root2] = root1;
        }
        else { // 平局比较元素大小，小元素成为根节点
            if (root1 < root2) {
                weight1 += weight2;
                parent[root1] = -weight1;
                parent[root2] = root1;
            }
            else {
                weight2 += weight1;
                parent[root2] = -weight2;
                parent[root1] = root2;
            }
        }
    }
}

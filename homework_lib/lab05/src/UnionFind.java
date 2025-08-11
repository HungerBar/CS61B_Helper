public class UnionFind {
    int[] array;
    int size;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        size = N;
        array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        if(v < 0 || v >= size) {
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }
        return find(v);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if(v < 0 || v >= size) {
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }
        return array[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1)==find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        int p,root;
        p = v;
        if(v < 0 || v >= size) {
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }
        while (array[v] > 0){
            v = array[v];
        }
        root = v;
        while(array[p] > 0){
            array[p] = root;
            p = array[p];
        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2) {
            return;
        }if (array[root1] >= array[root2]) {
            int size1 = -array[root1];
            array[root1] = root2;
            array[root2] = array[root2] - size1;
        }else{
            int size2 = -array[root2];
            array[root2] = root1;
            array[root1] = array[root1] - size2;
        }
    }

}

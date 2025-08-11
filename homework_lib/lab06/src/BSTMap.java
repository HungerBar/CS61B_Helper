import java.util.*;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {



    private class Node {
        K key;
        V value;
        Node  left, right;

        public Node(K k, V v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size = 0;

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     */
    @Override
    public void put(K key, V value) {
        Node i = new Node(key, value);
        if (root == null) {
            root = i;
            size++;
            return;
        }
        Node p = searchKeyHelper(key, root);
        if (p.key.compareTo(key) == 0) {
            p.value = value;
        } else if (p.key.compareTo(key) < 0) {
            p.right = i;
            size++;
        } else if (p.key.compareTo(key) > 0) {
            p.left = i;
            size++;
        }
    }

    //返回key待插入的father节点或者key所在的节点
    private Node searchKeyHelper(K key, Node r) {
        K cmd = r.key;
        Node p;
        if (cmd.compareTo(key) == 0) {
            return r;
        } else if (cmd.compareTo(key) < 0) {
            p = r.right;
        } else {
            p = r.left;
        }
        while (p != null) {
            cmd = p.key;
            if (cmd.compareTo(key) == 0) {
                return p;
            } else if (cmd.compareTo(key) > 0) {
                r = p;
                p = p.left;
            } else {
                r = p;
                p = p.right;
            }
        }
        return r;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        Node s = getandcontainKeyHelper(key);
        return s == null ? null : s.value;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(K key) {
        Node s = getandcontainKeyHelper(key);
        return s != null;
    }

    private Node getandcontainKeyHelper(K key) {
        if (root == null) {
            return null;
        }
        Node p = searchKeyHelper(key, root);
        if (p.key.compareTo(key) == 0) {
            return p;
        } else return null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        return keySetHelper(root);
    }

    private Set<K> keySetHelper(Node r) {
        Set<K> s = new LinkedHashSet<K>();
        if (r == null) {
            return s;
        }
        s.addAll(keySetHelper(r.left));
        s.add(r.key);
        s.addAll(keySetHelper(r.right));

        return s;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        if (size() == 0) {
            return null;
        }
        Node n = removeHelper(root, key);
        if (n.key.compareTo(key) == 0) {
            if (n.left == null) {
                root = n.right;
            } else if (n.right == null) {
                root = n.left;
            } else {
                Node i = n.right;
                root = n.right;
                while (i.left != null) {
                    i = i.left;
                }
                i.left = n.left;
            }
            size--;
            return n.value;
        }
        Node less = n.left;
        Node more = n.right;
        Node p;
        if (less != null) {
            if (less.key.compareTo(key) == 0) {
                p = less;
                if (p.right == null) {
                    n.left = p.left;
                } else {
                    n.left = p.right;
                    Node i = p.right;
                    while (i.left != null) {
                        i = i.left;
                    }
                    i.left = p.left;
                }
                size--;
                return p.value;
            }

        } else if (more != null) {
            if (more.key.compareTo(key) == 0) {
                p = more;
                if (p.left == null) {
                    n.right = p.right;
                } else {
                    n.right = p.left;
                    Node i = p.left;
                    while (i.right != null) {
                        i = i.right;
                    }
                    i.right = p.right;
                }
                size--;
                return p.value;
            }
        }
        return null;

    }

    private Node removeHelper(Node r, K key) {
        K cmd = r.key;
        Node p;
        if (size() == 1) {
            return r;
        } else if (r.key.compareTo(key) > 0) {
            p = r.left;
        } else if (r.key.compareTo(key) < 0) {
            p = r.right;
        } else {
            return r;
        }
        while (p != null) {
            cmd = p.key;
            if (cmd.compareTo(key) == 0) {
                return r;
            } else if (cmd.compareTo(key) > 0) {
                r = p;
                p = p.left;
            } else {
                r = p;
                p = p.right;
            }
        }
        return r;

    }

    @Override
    public Iterator<K> iterator() {
        return BSTIterator();
    }
    public Iterator<K> BSTIterator() {
        List<K> list = new ArrayList<>(); // 改用集合避免泛型数组问题
        inorderTraversal(root, list);     // 中序遍历填充数据
        return list.iterator();           // 返回列表迭代器
    }

    private void inorderTraversal(Node node, List<K> list) {
        if (node == null) return;
        inorderTraversal(node.left, list);
        list.add(node.key);
        inorderTraversal(node.right, list);
    }



}

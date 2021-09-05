package node;

public class StandardBinaryTreeNode<K, V> implements Comparable<StandardBinaryTreeNode<K, V>> {
    private K key = null;
    private V value = null;
    private StandardBinaryTreeNode<K, V> parent = null;
    private StandardBinaryTreeNode<K, V> left = null;
    private StandardBinaryTreeNode<K, V> right = null;


    public StandardBinaryTreeNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public StandardBinaryTreeNode<K, V> getParent() {
        return parent;
    }

    public void setParent(StandardBinaryTreeNode<K, V> parent) {
        this.parent = parent;
    }

    public StandardBinaryTreeNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(StandardBinaryTreeNode<K, V> left) {
        this.left = left;
    }

    public StandardBinaryTreeNode<K, V> getRight() {
        return right;
    }

    public void setRight(StandardBinaryTreeNode<K, V> right) {
        this.right = right;
    }

    /**
     * 支持6种基本类型数据（除了boolean）以及String的类型的值的比较
     *
     * @param o 待比较的节点
     * @return 比较的结果
     */
    @Override
    public int compareTo(StandardBinaryTreeNode<K, V> o) {
        // int
        if (key instanceof Integer) {
            return ((Integer) key).compareTo((Integer) o.getValue());
        }
        // String
        else if (key instanceof String) {
            return ((String) key).compareTo((String) o.getValue());
        }
        // double
        else if (key instanceof Double) {
            return ((Double) key).compareTo((Double) o.getValue());
        }
        // long
        else if (key instanceof Long) {
            return ((Long) key).compareTo((Long) o.getValue());
        }
        // byte
        if (key instanceof Byte) {
            return ((Byte) key).compareTo((Byte) o.getValue());
        }
        // short
        if (key instanceof Short) {
            return ((Short) key).compareTo((Short) o.getValue());
        }
        // float
        else if (key instanceof Float) {
            return ((Float) key).compareTo((Float) o.getValue());
        }
        // other
        else {
            return 0;
        }
    }
}

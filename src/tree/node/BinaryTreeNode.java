package tree.node;

public class BinaryTreeNode<K, V> implements Comparable<BinaryTreeNode<K, V>> {
    private K key;
    private V value;
    private BinaryTreeNode<K, V> left;
    private BinaryTreeNode<K, V> right;

    public BinaryTreeNode(K key, V value) {
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

    public BinaryTreeNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<K, V> left) {
        this.left = left;
    }

    public BinaryTreeNode<K, V> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<K, V> right) {
        this.right = right;
    }

    /**
     * 暂时只支持Integer，Double以及String的类型的值的比较
     *
     * @param o 待比较的节点
     * @return 比较的结果
     */
    @Override
    public int compareTo(BinaryTreeNode<K, V> o) {
        if (value instanceof Integer) {
            return ((Integer) value).compareTo((Integer) o.getValue());
        } else if (value instanceof Double) {
            return ((Double) value).compareTo((Double) o.getValue());
        } else if (value instanceof String) {
            return ((String) value).compareTo((String) o.getValue());
        } else {
            return 0;
        }
    }
}

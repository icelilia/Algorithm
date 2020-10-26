package tree;

public class Node<Key, Value> {
    private Key key;
    private Value value;
    private Node<?, ?> left;
    private Node<?, ?> right;

    public Node(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Node<?, ?> getLeft() {
        return left;
    }

    public void setLeft(Node<?, ?> left) {
        this.left = left;
    }

    public Node<?, ?> getRight() {
        return right;
    }

    public void setRight(Node<?, ?> right) {
        this.right = right;
    }
}

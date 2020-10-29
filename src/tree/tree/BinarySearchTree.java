package tree.tree;

import tree.node.BinaryTreeNode;

public class BinarySearchTree<K, V> {
    private final BinaryTreeNode<K, V> rootNode;

    public BinarySearchTree(K key, V value) {
        rootNode = new BinaryTreeNode<>(key, value);
    }

    public BinarySearchTree(BinaryTreeNode<K, V> binaryTreeNode) {
        if (binaryTreeNode == null) {
            System.err.println("rootNode can't be null");
            System.exit(-1);
        }
        rootNode = binaryTreeNode;
    }

    public BinaryTreeNode<K, V> getRootNode() {
        return rootNode;
    }


    public void addNode(BinaryTreeNode<K, V> binaryTreeNode) {
        BinaryTreeNode<K, V> temp = rootNode;
        do {
            // 新节点比当前节点小
            if (binaryTreeNode.compareTo(temp) <= 0) {
                if (temp.getLeft() == null) {
                    temp.setLeft(binaryTreeNode);
                    break;
                } else {
                    temp = temp.getLeft();
                }
            }
            // 新节点比当前节点大
            else if (binaryTreeNode.compareTo(temp) > 0) {
                if (temp.getRight() == null) {
                    temp.setRight(binaryTreeNode);
                    break;
                } else {
                    temp = temp.getRight();
                }
            }
        } while (true);
    }

    public void traversal(BinaryTreeNode<K, V> rootNode, int order) {
        if (rootNode == null) {
            return;
        }
        switch (order) {
            case -1:
                System.out.println(rootNode.getKey() + "\t" + rootNode.getValue());
                traversal(rootNode.getLeft(), -1);
                traversal(rootNode.getRight(), -1);
                return;
            case 0:
                traversal(rootNode.getLeft(), 0);
                System.out.println(rootNode.getKey() + "\t" + rootNode.getValue());
                traversal(rootNode.getRight(), 0);
                return;
            case 1:
                traversal(rootNode.getLeft(), 1);
                traversal(rootNode.getRight(), 1);
                System.out.println(rootNode.getKey() + "\t" + rootNode.getValue());
                return;
        }
        System.out.println("need order in {-1, 0, 1}");
    }
}

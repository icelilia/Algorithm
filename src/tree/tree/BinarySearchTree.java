package tree.tree;

import tree.node.BinaryTreeNode;

public class BinarySearchTree {
    private final BinaryTreeNode rootNode;

    public BinarySearchTree(String key, int value) {
        rootNode = new BinaryTreeNode(key, value);
    }

    public BinarySearchTree(BinaryTreeNode binaryTreeNode) {
        rootNode = binaryTreeNode;
    }

    public void addNode(BinaryTreeNode binaryTreeNode) {
        BinaryTreeNode temp = rootNode;
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

    public void deleteNode(String key){
        
    }

    public void deleteNode(int value){

    }


}

package main;

import tree.node.BinaryTreeNode;
import tree.tree.BinarySearchTree;

public class Main {

    public static void main(String[] args) {
        BinarySearchTree<String, Integer> binarySearchTree;
        binarySearchTree = new BinarySearchTree<>("root", 50);

        BinaryTreeNode<String, Integer> node;
        node = new BinaryTreeNode<>("1", 25);
        binarySearchTree.addNode(node);
        node = new BinaryTreeNode<>("2", 75);
        binarySearchTree.addNode(node);
        node = new BinaryTreeNode<>("3", 10);
        binarySearchTree.addNode(node);
        node = new BinaryTreeNode<>("4", 100);
        binarySearchTree.addNode(node);
        node = new BinaryTreeNode<>("5", 40);
        binarySearchTree.addNode(node);
        node = new BinaryTreeNode<>("6", 60);
        binarySearchTree.addNode(node);

        binarySearchTree.traversal(binarySearchTree.getRootNode(), 0);
    }
}

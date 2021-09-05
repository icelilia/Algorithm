package tree;

import node.StandardBinaryTreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<K, V> {
    private final StandardBinaryTreeNode<K, V> rootNode;

    /**
     * 根节点为null。
     */
    public BinarySearchTree() {
        rootNode = null;
    }

    /**
     * 新建一颗二叉查找树。
     *
     * @param key   根节点的key。
     * @param value 根节点的value。
     */
    public BinarySearchTree(K key, V value) {
        rootNode = new StandardBinaryTreeNode<>(key, value);
    }

    /**
     * 以一个已经存在的节点，生成一颗二叉查找树。不关心这个节点是否有子节点。
     *
     * @param node 已经存在的节点。
     */
    public BinarySearchTree(StandardBinaryTreeNode<K, V> node) {
        if (node == null) {
            System.err.println("rootNode can't be null");
            System.exit(-1);
        }
        rootNode = node;
    }

    /**
     * 获取根节点。
     *
     * @return 根节点。
     */
    public StandardBinaryTreeNode<K, V> getRootNode() {
        return rootNode;
    }

    /**
     * 遍历。
     *
     * @param rootNode 根节点。
     * @param order    遍历顺序：-1前序；0中序；1后序；2层序。
     * @return 遍历结果。
     */
    public ArrayList<StandardBinaryTreeNode<K, V>> ergodicity(StandardBinaryTreeNode<K, V> rootNode, int order) {
        ArrayList<StandardBinaryTreeNode<K, V>> ergodicityList = new ArrayList<>();
        if (order == -1 || order == 0 || order == 1) {
            basicErgodicity(rootNode, order, ergodicityList);
        } else {
            layerErgodicity(rootNode, ergodicityList);
        }
        return ergodicityList;
    }

    /**
     * 基础遍历。
     *
     * @param rootNode       根节点。
     * @param order          遍历顺序：-1前序；0中序；1后序。
     * @param ergodicityList 遍历结果List。
     */
    private void basicErgodicity(StandardBinaryTreeNode<K, V> rootNode, int order, ArrayList<StandardBinaryTreeNode<K, V>> ergodicityList) {
        if (rootNode == null) {
            return;
        }
        switch (order) {
            case -1:
                System.out.println(rootNode.getKey() + "\t" + rootNode.getValue());
                ergodicityList.add(rootNode);
                basicErgodicity(rootNode.getLeft(), -1, ergodicityList);
                basicErgodicity(rootNode.getRight(), -1, ergodicityList);
                break;
            case 0:
                basicErgodicity(rootNode.getLeft(), 0, ergodicityList);
                System.out.println(rootNode.getKey() + "\t" + rootNode.getValue());
                ergodicityList.add(rootNode);
                basicErgodicity(rootNode.getRight(), 0, ergodicityList);
                break;
            case 1:
                basicErgodicity(rootNode.getLeft(), 1, ergodicityList);
                basicErgodicity(rootNode.getRight(), 1, ergodicityList);
                System.out.println(rootNode.getKey() + "\t" + rootNode.getValue());
                ergodicityList.add(rootNode);
                break;
        }
    }

    /**
     * 层序遍历
     *
     * @param rootNode       根节点。
     * @param ergodicityList 遍历结果List。
     */
    private void layerErgodicity(StandardBinaryTreeNode<K, V> rootNode, ArrayList<StandardBinaryTreeNode<K, V>> ergodicityList) {
        Queue<StandardBinaryTreeNode<K, V>> queue = new LinkedList<>();
        queue.offer(rootNode);
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                StandardBinaryTreeNode<K, V> node = queue.poll();
                if (node == null) {
                    return;
                }
                ergodicityList.add(node);
                if (node.getLeft() != null) {
                    queue.offer(node.getLeft());
                }
                if (node.getRight() != null) {
                    queue.offer(node.getRight());
                }
            }
        }
    }

    /**
     * 添加节点。不允许添加拥有相同key的节点。
     *
     * @param node 待添加的节点。
     * @return 是否添加了该节点：true添加成功；false添加失败（和原先树中的某个节点拥有相同的key）。
     */
    public boolean addNode(StandardBinaryTreeNode<K, V> node) {
        StandardBinaryTreeNode<K, V> p = rootNode;
        do {
            int compareResult = node.compareTo(p);
            // 添加的节点比当前节点小
            if (compareResult < 0) {
                if (p.getLeft() == null) {
                    p.setLeft(node);
                    node.setParent(p);
                    return true;
                } else {
                    p = p.getLeft();
                }
            }
            // 添加的节点比当前节点大
            else if (compareResult > 0) {
                if (p.getRight() == null) {
                    p.setRight(node);
                    node.setParent(p);
                    return true;
                } else {
                    p = p.getRight();
                }
            }
            // 添加的节点和当前树中某个节点拥有相同的key
            else {
                return false;
            }
        } while (true);
    }

    /**
     * 删除节点。
     *
     * @param node 待删除的节点
     * @return 是否删除掉了指定节点：true已删除；false未删除（二叉查找树种并未发现该节点）。
     */
    public boolean deleteNode(StandardBinaryTreeNode<K, V> node) {
        StandardBinaryTreeNode<K, V> p = rootNode;
        do {
            int compareResult = node.compareTo(p);
            // 删除的节点比当前节点小
            if (compareResult < 0) {
                if (p.getLeft() == null) {
                    return false;
                } else {
                    p = p.getLeft();
                }
            }
            // 删除的节点比当前节点大
            else if (compareResult > 0) {
                if (p.getRight() == null) {
                    return false;
                } else {
                    p = p.getRight();
                }
            } else {
                // 上面是Key相同了，现在Value也相同
                if (node.getValue().equals(p.getValue())) {
                    StandardBinaryTreeNode<K, V> parent = p.getParent();
                    StandardBinaryTreeNode<K, V> left = p.getLeft();
                    StandardBinaryTreeNode<K, V> right = p.getRight();

                    // 判断待删除节点p是其父节点的左子节点还是右子节点
                    boolean isLeft = parent.getLeft() == p;

                    // 无论如何，都要先找到右子树的最小的那个节点
                    StandardBinaryTreeNode<K, V> temp = right;
                    while (temp.getLeft() != null) {
                        temp = temp.getLeft();
                    }

                    // 如果该节点就是右子树的根节点
                    if (temp == right) {
                        if (isLeft) {
                            parent.setLeft(right);
                        } else {
                            parent.setRight(right);
                        }
                        right.setParent(parent);
                        right.setLeft(left);
                        left.setParent(right);
                    } else {
                        if (isLeft) {
                            parent.setLeft(temp);
                        } else {
                            parent.setRight(temp);
                        }
                        temp.setParent(parent);
                        temp.setLeft(left);
                        left.setParent(temp);
                        temp.setRight(right);
                        right.setParent(temp);
                    }
                    return true;
                } else {
                    if (p.getLeft() == null) {
                        return false;
                    } else {
                        p = p.getLeft();
                    }
                }
            }
        } while (true);
    }

    /**
     * 更新节点。将key为指定值的节点的value更改为指定值。
     *
     * @param key   指定的key。
     * @param value 指定的value。
     * @return 是否更改成功；true更改成功；false更改失败（树中并未发现该key值）。
     */
    public boolean updateNode(K key, V value) {
        StandardBinaryTreeNode<K, V> p = rootNode;
        StandardBinaryTreeNode<K, V> node = new StandardBinaryTreeNode<>(key, value);
        do {
            int compareResult = node.compareTo(p);
            // 更改的节点比当前节点小
            if (compareResult < 0) {
                if (p.getLeft() == null) {
                    return false;
                } else {
                    p = p.getLeft();
                }
            }
            // 更改的节点比当前节点大
            else if (compareResult > 0) {
                if (p.getRight() == null) {
                    return false;
                } else {
                    p = p.getRight();
                }
            } else {
                // key相同了，更改value
                p.setValue(value);
                return true;
            }
        } while (true);
    }

    /**
     * 获取树的深度。
     *
     * @return 树的深度。
     */
    public int getDepth() {
        return getDepth(rootNode);
    }

    /**
     * 获取深度的实际递归调用。
     *
     * @param node 当前节点。
     * @return 以当前节点为根节点的树的深度。
     */
    private int getDepth(StandardBinaryTreeNode<K, V> node) {
        StandardBinaryTreeNode<K, V> left = node.getLeft();
        StandardBinaryTreeNode<K, V> right = node.getRight();
        int x = 0;
        int y = 0;
        if (left != null) {
            x = getDepth(left);
        }
        if (right != null) {
            y = getDepth(right);
        }
        return Math.max(x, y) + 1;
    }

}

package org.ccit.com;

public class BPlusTree<K extends Comparable<K>, V> {

    //根节点
    private BPlusNode<K, V> root;

    //阶数，m值
    private int order;

    //叶子节点的链表头
    private BPlusNode<K, V> head;

    //树高
    private int height;

    public BPlusTree(int order) {
        if (order < 3) {
            System.out.println("order 必须大于2");
        }
        this.order = order;
        root = new BPlusNode<K, V>(false);
    }

    public BPlusNode<K, V> getRoot() {
        return root;
    }

    public void setRoot(BPlusNode<K, V> root) {
        this.root = root;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public BPlusNode<K, V> getHead() {
        return head;
    }

    public void setHead(BPlusNode<K, V> head) {
        this.head = head;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

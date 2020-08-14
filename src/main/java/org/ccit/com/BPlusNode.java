package org.ccit.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BPlusNode<K extends Comparable<K>, V> {

    //是否为叶节点
    private boolean isLeaf;

    //是否为根节点
    private boolean isRoot;

    //父节点
    private BPlusNode<K, V> parent;

    //叶节点的前节点
    private BPlusNode<K, V> previous;

    //叶节点的后节点
    private BPlusNode<K, V> next;

    //节点的关键字
    private List<Map.Entry<K, V>> entries;

    //子节点
    private List<BPlusNode<K, V>> children;

    public BPlusNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        entries = new ArrayList<Map.Entry<K, V>>();

        if (!isLeaf) {
            children = new ArrayList<BPlusNode<K, V>>();
        }
    }

    public BPlusNode(boolean isLeaf, boolean isRoot) {
        this.isLeaf = isLeaf;
        this.isRoot = isRoot;
    }

    public V get(K key) {
        //如果是叶子节点
        if (isLeaf) {
            int low = 0, high = entries.size() - 1, mid;
            int comp;
            while (low <= high) {
                mid = (low + high) / 2;
                comp = entries.get(mid).getKey().compareTo(key);
                if (comp == 0) {
                    return entries.get(mid).getValue();
                } else if (comp < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return null;
        }

        //如果不是叶子节点
        //如果key小于节点最左边的key，沿第一个子节点继续搜索
        if (key.compareTo(entries.get(0).getKey()) < 0) {
            return children.get(0).get(key);
        }
        //如果key大于等于节点最右边的key，沿最后一个子节点继续搜索
        else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
            return children.get(entries.size() - 1).get(key);
        }
        //否则沿比key大的前一个节点继续搜索
        else {
            int low = 0, high = entries.size() - 1, mid;
            int comp;
            while (low <= high) {
                mid = (low + high) / 2;
                comp = entries.get(mid).getKey().compareTo(key);
                if (comp == 0) {
                    return children.get(mid + 1).get(key);
                } else if (comp < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return children.get(low).get(key);
        }
    }

    public void insertOrUpdate(K key, V value, BPlusTree<K, V> tree) {
        //如果是叶子节点
        if (isLeaf) {
            //不需要分裂，直接插入或更新
            if (contains(key) != -1 || entries.size() < tree.getOrder()) {
//                insertOrUpdate();
                if (tree.getHeight() == 0) {
                    tree.setHeight(1);
                }
                return;
            }

            //需要分裂，分裂成左右两个节点
            BPlusNode<K, V> left = new BPlusNode<>(true);
            BPlusNode<K, V> right = new BPlusNode<>(true);
            if (previous != null) {
                previous.next = left;
                left.previous = previous;
            }
            if (next != null) {
                next.previous = right;
                right.next = next;
            }
            if (previous == null) {
                tree.setHead(left);
            }
            left.next = right;
            right.previous = left;
            previous = null;
            next = null;

            //复制原节点的关键字到分裂出来的新节点

            //如果不是根节点
            if (parent != null) {
                int index = parent.children.indexOf(this);
                parent.children.remove(this);
                left.parent = parent;
                right.parent = parent;
                parent.children.add(index, left);
                parent.children.add(index + 1, right);
                parent.entries.add(index, right.entries.get(0));
                //删除当前节点的关键字信息
                entries = null;
                //删除当前节点的孩子节点引用
                children = null;

//                parent.updateInsert(tree);
            }
        }
    }

    public int contains(K key) {
        int low = 0, high = entries.size() - 1, mid;
        int comp;
        while (low <= high) {
            mid = (low + high) / 2;
            comp = entries.get(mid).getKey().compareTo(key);
            if (comp == 0) {
                return mid;
            } else if (comp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }


}

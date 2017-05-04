public class AVLTreeMap <K extends Comparable, V> {

    private Node<K, V> root;

    private class Node<K, V>{
        protected K key;
        protected V value;
        protected int height;
        protected Node<K, V> left_child;
        protected Node<K, V> right_child;

        Node(K key, V value){
            this.key = key;
            this.value = value;
            this.left_child = null;
            this.right_child = null;
        }

        public boolean is_balanced(){
            int difference = 0;
            boolean b = true;
            if (left_child != null && right_child != null)
                difference = left_child.height - right_child.height;
            else if(left_child != null && right_child == null)
                difference = left_child.height;
            else if(left_child == null && right_child != null)
                difference = right_child.height;
            if(difference == 2)
                b = false;
            return b;
        }
    }

    private Node<K, V> add_to_tree(K key, V value, Node<K, V> node){
        if(node == null) {
            node = new Node<>(key, value);
        }
        else if(node.key.compareTo(key) == 1){
            node.right_child = add_to_tree(key, value, node.right_child);
            if(!node.is_balanced()){
                if (node.right_child.key.compareTo(key) == 1){
                    node = left_rotation(node);
                } else{
                    node = right_left_rotation(node);
                }
            }
        }
        else if(node.key.compareTo(key) == -1){
            node.left_child = add_to_tree(key, value, node.left_child);
            if(!node.is_balanced()){
                if(node.left_child.key.compareTo(key) == -1){
                    node = right_rotation(node);
                } else{
                    node = left_right_rotation(node);
                }
            }
        }
        else{
            node.value = value;
        }

        node.height = maxHeight(node.left_child, node.right_child) + 1;
        return node;
    }

    private int maxHeight(Node<K, V> left_child, Node<K, V> right_child){
        int height_of_left = 0;
        int height_of_right = 0;
        if(left_child != null)
            height_of_left = left_child.height;
        if(right_child != null)
            height_of_right = right_child.height;
        return Math.max(height_of_left, height_of_right);
    }

    private V search(K key, Node<K, V> node){
        if(node == null)
            return null;
        else if(node.key.compareTo(key) == 0)
            return node.value;
        else if(node.key.compareTo(key) == -1)
            return search(key, node.left_child);
        else
            return search(key, node.right_child);
    }

    public void add(K key, V value){
        root = add_to_tree(key, value, root);
    }

    public boolean containsKey(K key){
        if(search(key, root) == null)
            return false;
        else
            return true;
    }

    public V get(K key){
        return search(key, root);
    }

    private Node<K, V> left_rotation(Node<K, V> node){
        Node<K, V> right_child = node.right_child; // save reference to the right child
        node.right_child = right_child.left_child;
        right_child.left_child = node;
        node.height = maxHeight(node.left_child, node.right_child) + 1;
        right_child.height = maxHeight(node, right_child.right_child) + 1;
        return right_child;
    }

    private Node<K, V> right_rotation(Node<K, V> node) {
        Node<K, V> left_child = node.left_child; // save reference to the left child
        node.left_child = left_child.right_child;
        left_child.right_child = node;
        node.height = maxHeight(node.right_child, node.left_child) + 1;
        left_child.height = maxHeight(node, left_child.left_child) + 1;
        return left_child;
    }

    private Node<K, V> left_right_rotation(Node<K, V> node){
        node.left_child = left_rotation(node.left_child);
        node = right_rotation(node);
        return node;
    }

    private Node<K, V> right_left_rotation(Node<K, V> node){
        node.right_child = right_rotation(node.right_child);
        node = left_rotation(node);
        return node;
    }
}

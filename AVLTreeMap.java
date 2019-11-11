public class AVLTreeMap implements Map {

    public class Node {
    String key;
    String value;
    Node left;
    Node right;
    int height;
 

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
        left = null;
        right = null;
        height = 0;
        }

    public int findBalance(){
        return height(this.left) - height(this.right);
        }
    }

    private int size = 0;
    private Node root;

    public int height(Node node){
        if (node == null){
            return -1;
            }
        return node.height;
        }

    public AVLTreeMap() {
        root = null;
    }

    public int size() {
        return size;
        }

    public Node get(Node node, String key){
        if (node != null) {
            int compare = key.compareTo(node.key);
            if (compare < 0) {
                return get(node.left, key);
            } else if (compare > 0){
                return get(node.right, key);
            } else {
                return node;
            }
        }
        return null;
    }

    public String get(String key){
        if (key == null){
            return null;
        }
        Node node = get(root, key);
        if (node == null){
            return null;
        }
    return node.value;
    }

    public Node put(String key, String value, Node node) {
        if (node == null) {
            Node curr = new Node(key, value);
            size ++;
            return curr;
        }
        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = put(key, value, node.left);
        } else if (comparison > 0){
            node.right = put(key, value, node.right);
        } else {
            node.value = value;
            return node;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    public boolean put(String key, String value){  
        boolean yes;
        if (get(key) != null) {
            yes = true;
        } else {
            yes = false; 
        }
        this.root = put(key, value, root);
        return yes;
    }

    public Node rotateRight(Node subTree){
        Node leftNode = subTree.left;
        leftNode = subTree.left;
        subTree.left = leftNode.right;
        leftNode.right = subTree;
        subTree.height = 1 + Math.max(height(subTree.left), height(subTree.right));
        leftNode.height = 1 + Math.max(height(leftNode.left), height(leftNode.right));
        return leftNode;
    }

    public Node rotateLeftRight(Node subTree){
        subTree.left = rotateLeft(subTree.left);
        return rotateRight(subTree);
    }

    public Node rotateLeft(Node subTree){
        Node rightNode    = subTree.right;
        rightNode = subTree.right;
        subTree.right = rightNode.left;
        rightNode.left = subTree;
        subTree.height = 1 + Math.max(height(subTree.left), height(subTree.right));
        rightNode.height = 1 + Math.max(height(rightNode.left), height(rightNode.right));
        return rightNode;
    }   

    public Node rotateRightLeft(Node subTree){
        subTree.right = rotateRight(subTree.right);
        return rotateLeft(subTree);
    }

    public Node balance(Node node) {
        if (node.findBalance() < -1){
            node = rotateLeft(node);
        }
        if (node.findBalance() > 1){
            node = rotateRight(node);
        }
        if (node.findBalance() < -1 && node.key.compareTo(node.right.key) < 0){
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
        }
        if (node.findBalance() > 1 && node.key.compareTo(node.left.key) < 0) {
            node.left = rotateLeft(node.left);
            node = rotateRight(node);
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return node; 
    }

    public void clear() {
        this.root = null;
        this.size = 0;
    }

    public void print() {
        this.print(this.root, "", 0);
    }

    private void print(Node node, String prefix, int depth) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        if (!prefix.equals("")) {
            System.out.print(prefix);
            System.out.print(":");
        }
        System.out.print(node.key);
        System.out.print(" (");
        System.out.print("H:");
        System.out.print(node.height);
        System.out.println(")");
        this.print(node.left, "L", depth + 1);
        this.print(node.right, "R", depth + 1);
    }

    public String preorderString() {
        //
        return this.preorderString(this.root);
    }

    private String preorderString(Node node) {
        //
        if (node == null) {
            return "()";
        }
        return "(" + node.key + " "
                + this.preorderString(node.left) + " "
                + this.preorderString(node.right) + ")";
    }

    // test cases:
    public static void main(String[] args) {
    
        AVLTreeMap map = new AVLTreeMap();
        //String[] keys = {"1", "2", "3"};//, "2", "1"};
        //String[] values = {"one", "two", "three"};//, "two", "one"};
        String[] keys = {"7", "9", "6", "0", "4", "2", "1"};
        String[] values = {"seven", "nine", "six", "zero", "four", "two", "one"};

        // insert all keys
        for (int i = 0; i < keys.length; i++) {
            boolean exists = map.put(keys[i], values[i]);
            if (exists) {
                System.out.println("Failed to insert key " + keys[i] + " and value " + values[i]);
                return;
            }
        }

        // check size
        if (map.size() != keys.length) {
            System.out.println(
                    "Map should have size() = " + Integer.toString(keys.length) + " after insertion of numbers "
                            + "but had size " + Integer.toString(map.size()) + " instead"
            );
            return;
        }
        map.print();

        // retrieve all keys and check their values
        for (int i = 0; i < keys.length; i++) {
            String value = map.get(keys[i]);
            System.out.println(value);
            if (!value.equals(values[i])) {
                System.out.println(
                        "Expected " + values[i] + " from retrieve key " + keys[i] + " "
                                + "got " + value + " instead"
                );
            }
        }

        map.clear();

        // check size
        if (map.size() != 0) {
            System.out.println(
                    "Map should have size() = 0 after clear() "
                            + "but had size " + Integer.toString(map.size()) + " instead"
            );
            return;
        }

        map.put("doe", "A deer, a female deer.");
        map.put("ray", "A drop of golden sun.");
        map.put("me", "A name I call myself.");
        map.put("far", "A long long way to run.");

        // check size
        if (map.size() != 4) {
            System.out.println(
                    "Map should have size() = 4 after insertion of musical keys "
                            + "but had size " + Integer.toString(map.size()) + " instead"
            );
            return;
        }

        if (!map.get("ray").equals("A drop of golden sun.")) {
            System.out.println(
                    "Expected \"A drop of golden sun.\" from retrieve key \"ray\" "
                            + "got \"" + map.get("ray") + "\" instead"
            );
            return;
        }

        return;
    }
}

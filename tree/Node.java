package tree;

public class Node {
    int key;
    int height;
    Node left;
    Node right;

    Node(int d) {
        key = d;
        height = 1;
    }
}

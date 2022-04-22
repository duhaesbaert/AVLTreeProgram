package tree;

public class Node {
    int value;
    int height;
    Node left;
    Node right;

    Node(int d) {
        value = d;
        height = 1;
    }
}

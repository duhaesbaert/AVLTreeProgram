package tree;

public class Node {
    int keyValue;
    int height;
    Node left;
    Node right;

    Node(int d) {
        keyValue = d;
        height = 1;
    }
}

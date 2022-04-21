package tree;

public class AVLTree {
 
    public Node root;

    public Node Insert(Node node, int value) {
        if (node == null) {
            System.out.println("Valor " + value + " inserido na arvore.");
            return (new Node(value));
        }

        if (value < node.keyValue) {
            node.left = Insert(node.left, value);
        } else if (value > node.keyValue) {
            node.right = Insert(node.right, value);
        } else {
            System.out.println("Valor atualmente contido na arvore.");
            return node;
        }

        if (treeHeight(node.left) > treeHeight(node.right)){
            node.height = treeHeight(node.left);
        } else {
            node.height = treeHeight(node.right);
        }
        node.height++;

        int currentBal = currentBalance(node);

        if ((currentBal > 1) && (value < node.left.keyValue)) {
            return rotateRight(node);
        }

        if ((currentBal < -1) && value > (node.right.keyValue)) {
            return rotateLeft(node);
        }

        if ((currentBal > 1) && (value > node.left.keyValue)) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if ((currentBal < -1) && (value < node.right.keyValue)) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public boolean Search(Node root, int value) {
        if (searchRec(root, value) == 0){
            return false;
        }
        return true;
    }

    private int searchRec(Node node, int value) {
        if (node == null)
            return 0;

        System.out.println("Nodo consultado: " + node.keyValue);
        if (value < node.keyValue) {
            return searchRec(node.left, value);
        } else if (value > node.keyValue) {
            return searchRec(node.right, value);
        } else {
            return node.keyValue;
        }
    }

    public Node Delete(Node root, int value) {
        if (root == null) {
            System.out.println("Arvore vazia.");
            return root;
        }

        if (value < root.keyValue) {
            root.left = Delete(root.left, value);
        } else if (value > root.keyValue) {
            root.right = Delete(root.right, value);
        } else {
            if ((root.left == null) || (root.right == null)) {
                Node nodeTmp = null;

                if (nodeTmp == root.left) {
                    nodeTmp = root.right;
                }else {
                    nodeTmp = root.left;
                }

                if (nodeTmp == null) {
                    nodeTmp = root;
                    root = null;
                } else {
                    root = nodeTmp;
                }
            } else {
                Node nodeTmp = findMinValNode(root.right);
                root.keyValue = nodeTmp.keyValue;
                root.right = Delete(root.right, nodeTmp.keyValue);
            }
        }

        if (root == null) {
            return root;
        }

        if (treeHeight(root.left) > treeHeight(root.right)){
            root.height = treeHeight(root.left);
        } else {
            root.height = treeHeight(root.right);
        }
        root.height++;

        int currentBal = currentBalance(root);

        if ((currentBal > 1) && (currentBalance(root.left) >= 0)) {
            return rotateRight(root);
        }

        if ((currentBal > 1) && (currentBalance(root.left) < 0)) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        if ((currentBal < -1) && (currentBalance(root.right) <= 0)) {
            return rotateLeft(root);
        }

        if ((currentBal < -1) && (currentBalance(root.right) > 0)) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    public void PreOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.keyValue + " ");
        PreOrder(node.left);
        PreOrder(node.right);
    }

    public void InOrder(Node node) {
        if (node == null) {
            return;
        }
        InOrder(node.left);
        System.out.print(node.keyValue + " ");
        InOrder(node.right);
    }

    public void PostOrder(Node node) {
        if (node == null) {
            return;
        }
        PostOrder(node.left);
        PostOrder(node.right);
        System.out.print(node.keyValue + " ");
    }

    private int treeHeight(Node node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }
 
    private Node rotateRight(Node nodeA) {
        Node nodeB = nodeA.left;
        Node nodeTmp = nodeB.right;

        nodeB.right = nodeA;
        nodeA.left = nodeTmp;

        if (treeHeight(nodeB.left) > treeHeight(nodeB.right)){
            nodeB.height = treeHeight(nodeB.left) + 1;
        } else {
            nodeB.height = treeHeight(nodeB.right) + 1;
        }
        nodeB.height++;
 
        return nodeB;
    }

    private Node rotateLeft(Node nodeA) {
        Node nodeB = nodeA.right;
        Node nodeTmp = nodeB.left;

        nodeB.left = nodeA;
        nodeA.right = nodeTmp;

        if (treeHeight(nodeB.left) > treeHeight(nodeB.right)){
            nodeB.height = treeHeight(nodeB.left) + 1;
        } else {
            nodeB.height = treeHeight(nodeB.right) + 1;
        }
        nodeB.height++;

        return nodeB;
    }

    private int currentBalance(Node node) {
        if (node == null) {
            return 0;
        }

        return (treeHeight(node.left) - treeHeight(node.right));
    }

    private Node findMinValNode(Node node) {
        Node currentNode = node;

        while (currentNode.left != null) {
            currentNode = currentNode.left;
        }

        return currentNode;
    }
}
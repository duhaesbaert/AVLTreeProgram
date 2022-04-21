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

        return balanceTreeByValue(node, value);
    }

    private Node balanceTreeByValue(Node node, int value) {
        int currentBal = currentBalance(node);

        if ((currentBal > 1) && (value < node.left.keyValue)) {
            return rotateRight(node);
        }

        if ((currentBal < -1) && (value > node.right.keyValue)) {
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
        if (searchRec(root, value) == null){
            return false;
        }
        return true;
    }

    private java.lang.Integer searchRec(Node node, int value) {
        if (node == null) {
            return null;
        }

        System.out.println("Nodo consultado: " + node.keyValue);
        if (value < node.keyValue) {
            return searchRec(node.left, value);
        } else if (value > node.keyValue) {
            return searchRec(node.right, value);
        } else {
            return node.keyValue;
        }
    }

    public Node Delete(Node node, int value) {
        if (node == null) {
            System.out.println("Arvore vazia.");
            return node;
        }

        if (value < node.keyValue) {
            node.left = Delete(node.left, value);
        } else if (value > node.keyValue) {
            node.right = Delete(node.right, value);
        } else {
            if ((node.left == null) || (node.right == null)) {
                Node nodeTmp = null;

                if (nodeTmp == node.left) {
                    nodeTmp = node.right;
                }else {
                    nodeTmp = node.left;
                }

                if (nodeTmp == null) {
                    node = null;
                } else {
                    node = nodeTmp;
                }
            } else {
                Node nodeTmp = findMinValNode(node.right);
                node.keyValue = nodeTmp.keyValue;
                node.right = Delete(node.right, nodeTmp.keyValue);
            }
        }

        if (node == null) {
            return node;
        }

        if (treeHeight(node.left) > treeHeight(node.right)){
            node.height = treeHeight(node.left);
        } else {
            node.height = treeHeight(node.right);
        }
        node.height++;

        return balanceTreeByHeight(node);
    }

    private Node balanceTreeByHeight(Node node) {
        int currentBal = currentBalance(node);

        if ((currentBal > 1) && (currentBalance(node.left) >= 0)) {
            return rotateRight(node);
        }

        if ((currentBal > 1) && (currentBalance(node.left) < 0)) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if ((currentBal < -1) && (currentBalance(node.right) <= 0)) {
            return rotateLeft(node);
        }

        if ((currentBal < -1) && (currentBalance(node.right) > 0)) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
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
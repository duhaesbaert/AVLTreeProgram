package tree;

public class AVLTree {
 
    public Node root;

    public Node Insert(Node node, int key) {
        if (node == null) {
            System.out.println("Valor " + key + " inserido na arvore.");
            return (new Node(key));
        }

        if (key < node.key) {
            node.left = Insert(node.left, key);
        } else if (key > node.key) {
            node.right = Insert(node.right, key);
        } else {
            System.out.println("Valor atualmente contido na arvore.");
            return node;
        }

        node.height = 1 + maxHeight(treeHeight(node.left), treeHeight(node.right));

        int balance = getBalance(node);

        if (balance > 1 && key < node.left.key) {
            return rotateRight(node);
        }

        if (balance < -1 && key > node.right.key) {
            return rotateLeft(node);
        }

        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public boolean Search(Node root, int key) {
        if (searchRec(root, key) == 0){
            return false;
        }
        return true;
    }

    private int searchRec(Node node, int key) {
        if (node == null)
            return 0;

        System.out.println("Nodo consultado: " + node.key);
        if (key < node.key) {
            return searchRec(node.left, key);
        } else if (key > node.key) {
            return searchRec(node.right, key);
        } else {
            return node.key;
        }
    }

    public Node Delete(Node root, int key) {
        if (root == null) {
            System.out.println("Arvore vazia.");
            return root;
        }

        if (key < root.key) {
            root.left = Delete(root.left, key);
        } else if (key > root.key) {
            root.right = Delete(root.right, key);
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
                Node nodeTmp = minValueNode(root.right);
                root.key = nodeTmp.key;
                root.right = Delete(root.right, nodeTmp.key);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = maxHeight(treeHeight(root.left), treeHeight(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            return rotateRight(root);
        }

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) {
            return rotateLeft(root);
        }

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    public void PreOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.key + " ");
        PreOrder(node.left);
        PreOrder(node.right);
    }

    public void InOrder(Node node) {
        if (node == null) {
            return;
        }
        InOrder(node.left);
        System.out.print(node.key + " ");
        InOrder(node.right);
    }

    public void PostOrder(Node node) {
        if (node == null) {
            return;
        }
        PostOrder(node.left);
        PostOrder(node.right);
        System.out.print(node.key + " ");
    }

    int treeHeight(Node node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }
 
    int maxHeight(int heightA, int heightB) {
        if (heightA > heightB) {
            return heightA;
        } else {
            return heightB;
        }
    }
 
    Node rotateRight(Node nodeA) {
        Node nodeB = nodeA.left;
        Node nodeTmp = nodeB.right;

        nodeB.right = nodeA;
        nodeA.left = nodeTmp;

        nodeA.height = maxHeight(treeHeight(nodeA.left), treeHeight(nodeA.right)) + 1;
        nodeB.height = maxHeight(treeHeight(nodeB.left), treeHeight(nodeB.right)) + 1;
 
        return nodeB;
    }
 
    Node rotateLeft(Node nodeA) {
        Node nodeB = nodeA.right;
        Node nodeTmp = nodeB.left;

        nodeB.left = nodeA;
        nodeA.right = nodeTmp;

        nodeA.height = maxHeight(treeHeight(nodeA.left), treeHeight(nodeA.right)) + 1;
        nodeB.height = maxHeight(treeHeight(nodeB.left), treeHeight(nodeB.right)) + 1;

        return nodeB;
    }
 
    int getBalance(Node node) {
        if (node == null) {
            return 0;
        }

        return (treeHeight(node.left) - treeHeight(node.right));
    }



    Node minValueNode(Node node) {
        Node current = node;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }
}
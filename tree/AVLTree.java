// Desenvolvido por Eduardo Haesbaert

package tree;

public class AVLTree {
 
    public Node root;

    // Realiza uma varredura na arvore para encontrar onde o valor deverá ser inserido na arvore.
    // Uma vez que encontrado, insere o nodo e realiza rotações para esquerda/direita conforme necessário para balancear
    // a arvore.
    public Node Insert(Node node, long value, Person.PersonInfo person) {
        if (node == null) {
            System.out.println("Chave " + value + " inserido na arvore.");
            return (new Node(value, person));
        }

        if (value < node.value) {
            node.left = Insert(node.left, value, person);
        } else if (value > node.value) {
            node.right = Insert(node.right, value, person);
        } else {
            System.out.println("Valor atualmente contido na arvore.");
            return node;
        }

        node.height = getHighestBranchPlusOne(node);

        return balanceTreeByValue(node, value);
    }

    // Utilizado pelo método Insert, realizando balanceamento dos nodos de acordo com seus valores.
    private Node balanceTreeByValue(Node node, long value) {
        int currentBal = currentBalance(node);

        if ((currentBal > 1) && (value < node.left.value)) {
            return rotateRight(node);
        }

        if ((currentBal < -1) && (value > node.right.value)) {
            return rotateLeft(node);
        }

        if ((currentBal > 1) && (value > node.left.value)) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if ((currentBal < -1) && (value < node.right.value)) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Realiza busca dentro da arvore por um valor especifico completo.
    public boolean Search(Node root, long value, boolean print) {
        return searchRec(root, value, print) != null;
    }

    public Node SearchRange(Node root, long value, long value2) {
        return searchRec(root, value, false);
    }

    private Node searchRec(Node node, long value, boolean print) {
        if (node == null) {
            return null;
        }

        if (print) {
            System.out.println(node.person.dateOfBirth);
        }
        if (value < node.value) {
            return searchRec(node.left, value, print);
        } else if (value > node.value) {
            return searchRec(node.right, value, print);
        } else {
            return node;
        }
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

        nodeB.height = getHighestBranchPlusOne(nodeB);
 
        return nodeB;
    }

    private Node rotateLeft(Node nodeA) {
        Node nodeB = nodeA.right;
        Node nodeTmp = nodeB.left;

        nodeB.left = nodeA;
        nodeA.right = nodeTmp;

        nodeB.height = getHighestBranchPlusOne(nodeB);

        return nodeB;
    }

    private int getHighestBranchPlusOne(Node node) {
        if (treeHeight(node.left) > treeHeight(node.right)){
            node.height = treeHeight(node.left);
        } else {
            node.height = treeHeight(node.right);
        }
        return node.height + 1;
    }

    private int currentBalance(Node node) {
        if (node == null) {
            return 0;
        }

        return (treeHeight(node.left) - treeHeight(node.right));
    }

    // TODO: REMOVE FROM CODE
    public Node Delete(Node node, long value) {
        if (node == null) {
            System.out.println("Valor inserido não contido na árvore.");
            return null;
        }

        if (value < node.value) {
            node.left = Delete(node.left, value);
        } else if (value > node.value) {
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
                Node nodeTmp = findMinValLeaf(node.right);
                node.value = nodeTmp.value;
                node.right = Delete(node.right, nodeTmp.value);
            }
        }

        if (node == null) {
            return null;
        }

        node.height = getHighestBranchPlusOne(node);

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
        System.out.print(node.value + " ");
        PreOrder(node.left);
        PreOrder(node.right);
    }

    public void InOrder(Node node) {
        if (node == null) {
            return;
        }
        InOrder(node.left);
        System.out.print(node.value + " ");
        InOrder(node.right);
    }

    public void PostOrder(Node node) {
        if (node == null) {
            return;
        }
        PostOrder(node.left);
        PostOrder(node.right);
        System.out.print(node.value + " ");
    }
    private Node findMinValLeaf(Node node) {
        Node currentNode = node;

        while (currentNode.left != null) {
            currentNode = currentNode.left;
        }

        return currentNode;
    }
}
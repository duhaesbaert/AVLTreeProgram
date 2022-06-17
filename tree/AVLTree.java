// Desenvolvido por Eduardo Haesbaert

package tree;

import Person.PersonInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AVLTree {
 
    public Node root;

    // Realiza uma varredura na arvore para encontrar onde o valor deverá ser inserido na arvore.
    // Uma vez que encontrado, insere o nodo e realiza rotações para esquerda/direita conforme necessário para balancear
    // a arvore.
    public Node Insert(Node node, long value, Person.PersonInfo[] person, boolean updatable) {
        if (node == null) {
            return (new Node(value, person));
        }

        if (value < node.value) {
            node.left = Insert(node.left, value, person, updatable);
        } else if (value > node.value) {
            node.right = Insert(node.right, value, person, updatable);
        } else {
            if (updatable) {
                node.person = concatArrays(node.person, person);
            }
            return node;
        }

        node.height = getHighestBranchPlusOne(node);

        return balanceTreeByValue(node, value);
    }

    // Concatena dois arrays já existnetes de PersonInfo.
    private Person.PersonInfo[] concatArrays(Person.PersonInfo[] current, Person.PersonInfo[] newVal) {
        Person.PersonInfo[] nArr = new Person.PersonInfo[current.length+newVal.length];
        int nArrIndex = 0;

        for (int i = 0; i <= current.length-1; i++) {
            nArr[nArrIndex] = current[i];
            nArrIndex++;
        }

        for (int i = 0; i <= newVal.length-1; i++) {
            nArr[nArrIndex] = newVal[i];
            nArrIndex++;
        }

        return nArr;
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

    // Realiza busca dentro da arvore por um valor especifico. Recebe como
    // argumento o root node, valor pelo qual estará buscando e também um valor booleano
    // indicando se os valores visitados devem ou não ser exibidos.
    // Retorna um booleano informando se o valor foi ou não encontrado.
    public Node Search(Node root, long value, boolean print, List<Person.PersonInfo> list) {
        return searchRec(root, value, print, list);
    }

    private Node searchRec(Node node, long value, boolean print, List<Person.PersonInfo> list) {
        if (node == null) {
            return null;
        }

        if (print) {
            printAllData(node.person[0]);
            list.add(node.person[0]);
        }
        if (value < node.value) {
            return searchRec(node.left, value, print, list);
        } else if (value > node.value) {
            return searchRec(node.right, value, print, list);
        } else {
            return node;
        }
    }

    // Realiza a busca dentor da arvore por um valor especifico. Caso
    // o valor seja encontrado, retorna o Node onde foi encontrado.
    public Node SearchRange(Node root, long value, long value2, List<Person.PersonInfo> list) {
        Node ret = searchRec(root, value, false, list);

        if (ret == null) {
            ret = findNextInRange(root, value, value2, false, list);
        }

        return ret;
    }

    private Node findNextInRange(Node node, long value, long value2, boolean print, List<Person.PersonInfo> list) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            return searchRec(node.right, value2, true, list);
        } else if (value > node.value) {
            return findNextInRange(node.right, value, value2, print, list);
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

    // Recebe por argumento um PersonInfo e realiza um print no terminal com as informações do objeto formatadas apropriadamente.
    private static void printAllData(Person.PersonInfo person) {
        System.out.println("CPF: " + person.cpf + ", RG: " + person.rg + ", Nome: " + person.name + ", Data de Nascimento(DD/MM/AAAA): " + person.dateOfBirth + ", Cidate de Nascimento: " + person.cityOfBirth);
    }
}
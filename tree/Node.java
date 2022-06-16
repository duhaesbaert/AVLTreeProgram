package tree;

public class Node {
    long value;

    Person.PersonInfo person;
    int height;
    Node left;
    Node right;

    // Instancia um novo nodo populando os respectivos valores de node
    // utlizando o valor recebidos como argumento. Define que a altura
    // como 1.
    public Node(long d, Person.PersonInfo p) {
        value = d;
        person = p;
        height = 1;
    }
}

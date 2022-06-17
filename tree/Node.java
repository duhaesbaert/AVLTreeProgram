// Desenvolvido por Eduardo Haesbaert

package tree;

public class Node {
    public long value;
    public Person.PersonInfo[] person;
    public int height;
    public Node left;
    public Node right;

    // Instancia um novo nodo populando os respectivos valores de node
    // utlizando o valor recebidos como argumento. Define que a altura
    // como 1.
    public Node(long d, Person.PersonInfo[] p) {
        value = d;
        person = p;
        height = 1;
    }
}

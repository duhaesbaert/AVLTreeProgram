import tree.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        AVLTree tree = new AVLTree();
        initProg(tree,true);
    }

    public static void initProg(AVLTree tree, boolean printH) throws IOException {
        if (printH) {
            printHelp();
            printH = false;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Insira o comando> ");
        String input = reader.readLine();
        String command = input.substring(0,1);

        switch (command) {
            case "i":
                tree.root = insertInput(tree, Integer.parseInt(input.substring(2, input.length())));
                tree.PreOrder(tree.root);
                System.out.println("");
                break;
            case "b":
                searchInput(tree, Integer.parseInt(input.substring(2, input.length())));
                break;
            case "r":
                tree.root = removeInput(tree, Integer.parseInt(input.substring(2, input.length())));
                tree.PreOrder(tree.root);
                System.out.println("");
                break;
            case "e":
                System.out.println("Finalizando programa.");
                System.exit(0);
                break;
            case "h":
                printH = true;
                break;
            case "p":
                String value = input.substring(2, input.length());
                if (value.equals("pre")) {
                    tree.PreOrder(tree.root);
                    System.out.println("");
                } else if (value.equals("post")) {
                    tree.PostOrder(tree.root);
                    System.out.println("");
                } else if (value.equals("in")) {
                    tree.InOrder(tree.root);
                    System.out.println("");
                } else {
                    System.out.println("Comando inválido. Por favor, insira um comando válido, ou pressione h para exibir os comandos.");
                }
                break;
            default:
                System.out.println("Comando inválido. Por favor, insira um comando válido, ou pressione h para exibir os comandos.");
        }

        initProg(tree, printH);
    }

    private static void printHelp() {
        System.out.println("Para realizar uma operação, digite um dos comandos seguido de um número inteiro, separados por um espaço, e aperte enter:");
        System.out.println("Inserir(i): i <valor>");
        System.out.println("Buscar(b): b <valor>");
        System.out.println("Remover(r): r <valor>");
        System.out.println("");
        System.out.println("Print(p):");
        System.out.println("Pré-ordem: p pre");
        System.out.println("Em ordem: p in");
        System.out.println("Pós-ordem: p post");
        System.out.println("");
        System.out.println("Para sair, digite e");
    }

    private static Node insertInput(AVLTree tree, int value) {
        return tree.Insert(tree.root, value);
    }

    private static void searchInput(AVLTree tree, int value) {
        if (tree.Search(tree.root, value)) {
            System.out.println("Valor " + value + " encontrado.");
        } else {
            System.out.println("Valor " + value + " não encontrado.");
        }
    }

    private static Node removeInput(AVLTree tree, int value) {
        return tree.Delete(tree.root, value);
    }

}

import Converter.KeyStringConverter;
import Person.PersonInfo;
import tree.*;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static AVLTree treeCPF = new AVLTree();
    public static AVLTree treeName = new AVLTree();
    public static AVLTree treeDOB = new AVLTree();

    public static void main(String[] args) throws IOException {
        //System.out.println(Converter.KeyStringConverter.ConvertStringToKey("eduardo"));
        //System.out.println(Converter.KeyStringConverter.ConvertStringDateToKey("25031994"));

        ReadCSVFile("C:\\Users\\Eduardo Haesbaert\\Documents\\GitHub\\AVLTreeProgram\\CSVFiles\\personinfo.csv");

        //initProg(treeCPF, treeName, treeDOB,true);
    }

    public static void initProg(AVLTree tree, AVLTree name, AVLTree dob, boolean printH) throws IOException {
        if (printH) {
            printHelp();
            printH = false;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Insira o comando> ");
        String input = reader.readLine();
        String command = input.substring(0,1);

        Person.PersonInfo person = new PersonInfo(0,0, "eduardo", "25/03/1994", "Sao Leopoldo");

        switch (command) {
            //case "i":
           //     tree.root = insertInput(tree, Long.parseLong(input.substring(2, input.length())), person);
            //    tree.PreOrder(tree.root);
             //   System.out.println("");
             //   break;
            case "b":
                searchInput(tree, Integer.parseInt(input.substring(2, input.length())));
                break;
            //case "r":
            //    tree.root = removeInput(tree, Integer.parseInt(input.substring(2, input.length())));
            //    tree.PreOrder(tree.root);
            //    System.out.println("");
            //    break;
            case "e":
                System.out.println("Finalizando programa.");
                System.exit(0);
                break;
            case "h":
                printH = true;
                break;
            case "p":
                printTree(tree, input.substring(2, input.length()));
                break;
            default:
                System.out.println("Comando inválido. Por favor, insira um comando válido, ou pressione h para exibir os comandos.");
                break;
        }

        initProg(tree, name, dob, printH);
    }

    private static void printHelp() {
        System.out.println("Para realizar uma operação, digite um dos comandos seguido de um número inteiro, separados por um espaço, e aperte enter:");
        //System.out.println("Inserir(i): i <valor>");
        System.out.println("Buscar(b): b <valor>");
        //System.out.println("Remover(r): r <valor>");
        System.out.println("");
        //System.out.println("Print(p):");
        //System.out.println("Pré-ordem: p pre");
        //System.out.println("Em ordem: p in");
        //System.out.println("Pós-ordem: p post");
        //System.out.println("");
        System.out.println("Para sair, digite e");
    }

    private static void insertPerson(AVLTree tree, long value, Person.PersonInfo person) {
        tree.Insert(tree.root, value, person);
    }

    private static Node insertInput(AVLTree tree, long value, Person.PersonInfo person) {
        return tree.Insert(tree.root, value, person);
    }

    private static void searchInput(AVLTree tree, long value) {
        if (tree.Search(tree.root, value)) {
            System.out.println("Valor " + value + " encontrado.");
        } else {
            System.out.println("Valor " + value + " não encontrado.");
        }
    }

    private static Node removeInput(AVLTree tree, int value) {
        return tree.Delete(tree.root, value);
    }

    private static void printTree(AVLTree tree, String value) {
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
    }

    // ReadCSVFile recebe o caminho de um arquivo para ser lido, lê o respectivo arquivo, adicionando os respectivos
    // valores a um objeto Person, que será indexado em três instâncias de arvores AVL: CPF, Nome e Data de Nascimento.
    private static void ReadCSVFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line = "";
        while((line = br.readLine()) != null) {
            System.out.println(line);
            String[] personValues = line.split(",");

            // Cria um objeto PersonInfo com as informações obtidas do CSV.
            Person.PersonInfo p = new Person.PersonInfo(Long.parseLong(personValues[0]), Long.parseLong(personValues[1]), personValues[2], personValues[3], personValues[4]);

            // Indexa os valores nas respectivas arvores de busca.
            insertPerson(treeCPF, p.cpf, p);
            insertPerson(treeName, Converter.KeyStringConverter.ConvertStringToKey(p.name), p);
            insertPerson(treeDOB, Converter.KeyStringConverter.ConvertStringDateToKey(p.dateOfBirth.replace("/", "")), p);
        }
    }
}

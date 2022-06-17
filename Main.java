// Desenvolvido por Eduardo Haesbaert

import Converter.KeyStringConverter;
import Person.PersonInfo;
import tree.*;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static AVLTree treeCPF = new AVLTree();
    public static AVLTree treeName = new AVLTree();
    public static AVLTree treeNameGroup = new AVLTree();
    public static AVLTree treeDOB = new AVLTree();

    public static void main(String[] args) throws IOException {
        ReadCSVFile("C:\\Users\\Eduardo Haesbaert\\Documents\\GitHub\\AVLTreeProgram\\CSVFiles\\personinfo.csv");

        searchNameByKey(treeName, "edu");
        searchNameByKey(treeName, "ren");
        searchNameByKey(treeName, "jul");
        searchNameByKey(treeName, "arm");
        /*
        searchCPF(treeCPF, 38787549069L);
        searchCPF(treeCPF, 36469186084L);
        searchCPF(treeCPF, 40618933000L);

        searchName(treeName, "eduardo");
        searchName(treeName, "renato");
        searchName(treeName, "julia");
        searchName(treeName, "juliana");

        searchNameByKey(treeName, "edu");
        searchNameByKey(treeName, "ren");
        searchNameByKey(treeName, "jul");
        searchNameByKey(treeName, "arm");

        searchDate(treeDOB, "20/02/1992","25/03/1994");
        System.out.println("");
        searchDate(treeDOB, "20/03/1992","29/03/2000");
        System.out.println("");
        searchDate(treeDOB, "20/02/1992","25/09/1999");

        initProg(treeCPF, treeName, treeDOB,true);
        */
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
            case "b":
                //searchInput(tree, Integer.parseInt(input.substring(2, input.length())));
                break;
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

    // Recebe por argumento uma ArvoreAVL, a chave para indexação, e os valores do objeto PersonInfo.
    private static void insertPerson(AVLTree tree, long value, Person.PersonInfo[] person, boolean updatable) {
        tree.root = tree.Insert(tree.root, value, person, updatable);
    }

    // searchCPF efetua uma busca na arvore buscando pelo valor especifico recebido por argumento
    // e retorna uma mensagem de confirmação para o usuário.
    private static void searchCPF(AVLTree tree, long value) {
        if (tree.Search(tree.root, value, false) != null){
            System.out.println("Valor " + value + " encontrado.");
        } else {
            System.out.println("Valor " + value + " não encontrado.");
        }
    }

    // searchName efetua uma busca pelo name de PersonInfo e retorna para o usuário
    // uma mensagem de confirmação.
    private static void searchName(AVLTree tree, String name) {
        long key = Converter.KeyStringConverter.ConvertStringToKey(name);

        if (tree.Search(tree.root, key, false) != null) {
            System.out.println("Valor " + name + " encontrado.");
        } else {
            System.out.println("Valor " + name + " não encontrado.");
        }
    }

    // searchNameByKey efetua uma busca por uma chave de 3 characteres na arvore e
    // retorna para uma lista de PersonInfo que estão relacionadas ao parametro de busca.
    private static void searchNameByKey(AVLTree tree, String nameKey) {
        long key = Converter.KeyStringConverter.ConvertStringToShortKey(nameKey);
        Node fNode = tree.Search(tree.root, key, false);

        if (fNode != null) {
            System.out.println("Registros encontrados para \"" + nameKey + "\": ");
            for (int i = 0; i<= fNode.person.length-1; i++) {
                printAllData(fNode.person[i]);
            }
        } else {
            System.out.println("Nenhum registro encontrado iniciando com \"" + nameKey + "\"");
        }
    }

    // searchDate efetua uma busca dentro da arvore por um range de datas, entre data1 e data2
    // seguind a ordem estabelecida pela arvore. Busca pela primeira data, ao encontra-la efetua uma segunda
    // busca, partindo de date1, exibindo todas as datas encontradas até date2.
    private static void searchDate(AVLTree tree, String date1, String date2) {
        long key1 = Converter.KeyStringConverter.ConvertStringDateToKey(date1);
        long key2 = Converter.KeyStringConverter.ConvertStringDateToKey(date2);

        if (key1 > key2) {
            System.out.println("Erro: Data inicial maior do que data final. Por favor insira uma data inicial maior do que a data final");
        } else {
            Node searchFrom = tree.SearchRange(tree.root, key1, key2);
            tree.Search(searchFrom, key2, true);
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
            Person.PersonInfo[] pArr = new Person.PersonInfo[1];
            Person.PersonInfo p = new Person.PersonInfo(Long.parseLong(personValues[0]), Long.parseLong(personValues[1]), personValues[2], personValues[3], personValues[4]);
            pArr[0] = p;

            // Indexa os valores nas respectivas arvores de busca.
            insertPerson(treeCPF, p.cpf, pArr, false);
            //insertPerson(treeName, Converter.KeyStringConverter.ConvertStringToKey(p.name), pArr, false);
            insertPerson(treeName, Converter.KeyStringConverter.ConvertStringToShortKey(p.name), pArr, true);
            insertPerson(treeDOB, Converter.KeyStringConverter.ConvertStringDateToKey(p.dateOfBirth), pArr, false);
        }
    }

    private static void printAllData(Person.PersonInfo person) {
        System.out.println("CPF: " + person.cpf + ", RG: " + person.rg + ", Nome: " + person.name + ", Data de Nascimento(DD/MM/AAAA): " + person.dateOfBirth + ", Cidate de Nascimento: " + person.cityOfBirth);
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
}

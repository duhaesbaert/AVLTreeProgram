// Desenvolvido por Eduardo Haesbaert

import Converter.KeyStringConverter;
import Person.PersonInfo;
import tree.*;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static AVLTree treeCPF = new AVLTree();
    public static AVLTree treeName = new AVLTree();
    public static AVLTree treeDOB = new AVLTree();


    public static void main(String[] args) throws IOException {
        initProg(true);
    }

    public static void initProg(boolean printH) throws IOException {
        if (printH) {
            printHelp();
            printH = false;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Insira o comando> ");
        String input = reader.readLine();
        String command = input.substring(0,1);

        switch (command) {
            case "f":
                startMyForm();
                break;
            case "c":
                startCLI(true);;
                break;
            case "e":
                System.out.println("Finalizando programa.");
                System.exit(0);
                break;
            case "h":
                printH = true;
                break;
            default:
                System.out.println("Comando inválido. Por favor, insira um comando válido, ou pressione h para exibir os comandos.");
                printH = true;
                break;
        }

        initProg(printH);
    }

    private static void startMyForm() {
        JFrame frame = new JFrame("StartForm");
        frame.setContentPane(new StartForm().tabbedPane1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static void startCLI(boolean printH) throws IOException {
        if (printH) {
            printHelpCLI();
            printH = false;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("");
        System.out.print("Insira o comando> ");
        String input = reader.readLine();
        String command = input.substring(0,1);

        switch (command) {
            case "i":
                initProg(true);
                break;
            case "c":
                ReadCSVFile(input.substring(2, input.length()));
                printH = true;
                break;
            case "r":
                runAutomatedTests();
                printH = true;
                break;
            case "e":
                System.out.println("Finalizando programa.");
                System.exit(0);
                break;
            case "b":
                searchCPF(treeCPF, Long.parseLong(input.substring(2, input.length())));
                break;
            case "n":
                searchNameByKey(treeName, input.substring(2, input.length()));
                break;
            case "d":
                String[] argArr = input.substring(2, input.length()).split(" ");
                searchDate(treeDOB, argArr[0], argArr[1]);
                break;
            case "p":
                String args = input.substring(2, input.length());
                if (args == "nome" || args == "nomes") {
                    printTree(treeName);
                } else if(args == "data" || args == "datas") {
                    printTree(treeDOB);
                } else {
                    printTree(treeCPF);
                }
                break;
            default:
                System.out.println("Comando inválido. Por favor, insira um comando válido, ou pressione h para exibir os comandos.");
                break;
        }

        startCLI(printH);
    }

    // Recebe por argumento uma ArvoreAVL, a chave para indexação, e os valores do objeto PersonInfo.
    private static void insertPerson(AVLTree tree, long value, Person.PersonInfo[] person, boolean updatable) {
        tree.root = tree.Insert(tree.root, value, person, updatable);
    }

    // searchCPF efetua uma busca na arvore buscando pelo valor especifico recebido por argumento
    // e retorna uma mensagem de confirmação para o usuário.
    private static void searchCPF(AVLTree tree, long value) {
        Node fNode = tree.Search(tree.root, value, false);
        if (fNode != null){
            System.out.println("Registro encontrado para " + value);
            printAllData(fNode.person[0]);
        } else {
            System.out.println("Nenhum registro encontrado com CPF " + value);
        }
    }

    // searchNameByKey efetua uma busca por uma chave de 3 characteres na arvore e
    // retorna para uma lista de PersonInfo que estão relacionadas ao parametro de busca.
    private static void searchNameByKey(AVLTree tree, String nameKey) {
        if (nameKey.length() < 3) {
            System.out.println("Por favor, digite ao menos 3 caracteres para realizar a busca.");
        } else {
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
            System.out.println("Registros encontrados entre " + date1 + " e " + date2);
            Node searchFrom = tree.SearchRange(tree.root, key1, key2);
            tree.Search(searchFrom, key2, true);
        }
    }

    // ReadCSVFile recebe o caminho de um arquivo para ser lido, lê o respectivo arquivo, adicionando os respectivos
    // valores a um objeto Person, que será indexado em três instâncias de arvores AVL: CPF, Nome e Data de Nascimento.
    public static int ReadCSVFile(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = "";
            int opsCount = 0;
            while((line = br.readLine()) != null) {
                String[] personValues = line.split(",");

                // Cria um objeto PersonInfo com as informações obtidas do CSV.
                Person.PersonInfo[] pArr = new Person.PersonInfo[1];
                Person.PersonInfo p = new Person.PersonInfo(Long.parseLong(personValues[0]), Long.parseLong(personValues[1]), personValues[2], personValues[3], personValues[4]);
                pArr[0] = p;

                // Indexa os valores nas respectivas arvores de busca.
                insertPerson(treeCPF, p.cpf, pArr, false);
                insertPerson(treeName, Converter.KeyStringConverter.ConvertStringToShortKey(p.name), pArr, true);
                insertPerson(treeDOB, Converter.KeyStringConverter.ConvertStringDateToKey(p.dateOfBirth), pArr, false);
                opsCount++;
            }

            if (opsCount > 0) {
                System.out.println("Arquivo importado com sucesso. " + opsCount + " registros indexados.");
            } else {
                System.out.println(opsCount + " registros indexados. Verifique o conteúdo do arquivo.");
            }

            return opsCount;
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado.");
            return 0;
        }

    }

    // Recebe por argumento um PersonInfo e realiza um print no terminal com as informações do objeto formatadas apropriadamente.
    private static void printAllData(Person.PersonInfo person) {
        System.out.println("CPF: " + person.cpf + ", RG: " + person.rg + ", Nome: " + person.name + ", Data de Nascimento(DD/MM/AAAA): " + person.dateOfBirth + ", Cidate de Nascimento: " + person.cityOfBirth);
    }

    private static void printHelp() {
        System.out.println("Digite o comando de acordo com a operação que deseja executar e pressione enter:");
        System.out.println("Abrir UI(f)");
        System.out.println("Utilizar CLI(c)");
        System.out.println("Para sair, digite e");
        //https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram-in-java
    }

    private static void printHelpCLI() {
        System.out.println("Bem vindo a CLI do programa. Digite o comando desejado e pressione enter:");
        System.out.println("Voltar para o inicio(i)");
        System.out.println("Importar valores de CSV(c): c caminho_do_arquivo");
        System.out.println("Buscar por CPF(b): b numero_de_cpf");
        System.out.println("Buscar por inicio do nome(n): b tres_primeiras_letras");
        System.out.println("Buscar por intervalo de datas(d): d data_inicio data_fim");
        System.out.println("Exibir Estado atual dos indexes(p cpf, p nome ou p data)");
        System.out.println("Executar testes automatizados(r)");
        System.out.println("Para sair, digite e");
    }

    private static void printTree(AVLTree tree) {
            tree.InOrder(tree.root);
            System.out.println("");
    }

    private static void runAutomatedTests() throws IOException {
        Path p = Paths.get("personinfo.csv");
        int opsCount = ReadCSVFile(p.toAbsolutePath().toString());
        if (opsCount > 0) {
            searchCPF(treeCPF, 38787549069L);
            searchCPF(treeCPF, 36469186084L);
            searchCPF(treeCPF, 40618933000L);
            searchCPF(treeCPF, 40239840284L);

            System.out.println("");

            searchNameByKey(treeName, "edu");
            searchNameByKey(treeName, "ren");
            searchNameByKey(treeName, "jul");
            searchNameByKey(treeName, "arm");

            System.out.println("");

            searchDate(treeDOB, "20/02/1992","25/03/1994");
            System.out.println("");
            searchDate(treeDOB, "20/03/1992","29/03/2000");
            System.out.println("");
            searchDate(treeDOB, "20/02/1992","25/09/1999");
        } else {
            System.out.println("Erro ao localizar arquivo de testes a ser importado.");
        }

    }

}

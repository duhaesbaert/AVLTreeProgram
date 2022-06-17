// Desenvolvido por Eduardo Haesbaert

import Converter.KeyStringConverter;
import Person.PersonInfo;
import tree.*;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static AVLTree treeCPF = new AVLTree();
    public static AVLTree treeName = new AVLTree();
    public static AVLTree treeDOB = new AVLTree();
    public static String viewContent = "";

    public static void main(String[] args) throws IOException {
        initProg(true);
    }

    // Inicia o programa e provê um CLI para o usuário selecionar
    // se deseja utilizar CLI ou GUI.
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

    // Instancia e inicia o formulário GUI.
    private static void startMyForm() {
        JFrame frame = new JFrame("StartForm");
        frame.setContentPane(new StartForm().tabbedPane1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Função de controle para as ações possíveis na CLI.
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
                String arg = input.substring(2, input.length());
                if (arg.equals("sample")) {
                    arg = useSampleFile();
                }

                ReadCSVFile(arg);
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
                Person.PersonInfo p = searchCPF(treeCPF, Long.parseLong(input.substring(2, input.length())));
                if (p != null) {
                    printAllData(p);
                }
                break;
            case "n":
                Person.PersonInfo[] ps = searchNameByKey(treeName, input.substring(2, input.length()));
                if (ps != null) {
                    for (int i = 0; i<= ps.length-1; i++) {
                        printAllData(ps[i]);
                    }
                }
                break;
            case "d":
                String[] argArr = input.substring(2, input.length()).split(" ");
                List<Person.PersonInfo> listP = searchDate(treeDOB, argArr[0], argArr[1]);
                break;
            case "v":
                String args = input.substring(2, input.length());
                if (args.equals("nome") || args.equals("nomes")) {
                    prettyPrintTree(treeName.root, 0, "name");
                } else if(args.equals("data") || args.equals("datas")) {
                    prettyPrintTree(treeDOB.root, 0, "date");
                } else {
                    prettyPrintTree(treeCPF.root, 0, "cpf");
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
    public static Person.PersonInfo searchCPF(AVLTree tree, long value) {
        Node fNode = tree.Search(tree.root, value, false, null);
        if (fNode != null){
            System.out.println("Registro encontrado para " + value);
            return fNode.person[0];
        } else {
            System.out.println("Nenhum registro encontrado com CPF " + value);
            return null;
        }
    }

    // searchNameByKey efetua uma busca por uma chave de 3 characteres na arvore e
    // retorna para uma lista de PersonInfo que estão relacionadas ao parametro de busca.
    public static Person.PersonInfo[] searchNameByKey(AVLTree tree, String nameKey) {
        if (nameKey.length() < 3) {
            System.out.println("Por favor, digite ao menos 3 caracteres para realizar a busca.");
        } else {
            long key = Converter.KeyStringConverter.ConvertStringToShortKey(nameKey);
            Node fNode = tree.Search(tree.root, key, false, null);

            if (fNode != null) {
                System.out.println("Registros encontrados para \"" + nameKey + "\": ");
                return fNode.person;
            } else {
                System.out.println("Nenhum registro encontrado iniciando com \"" + nameKey + "\"");
            }
        }
        return null;
    }

    // searchDate efetua uma busca dentro da arvore por um range de datas, entre data1 e data2
    // seguind a ordem estabelecida pela arvore. Busca pela primeira data, ao encontra-la efetua uma segunda
    // busca, partindo de date1, exibindo todas as datas encontradas até date2.
    public static List<Person.PersonInfo> searchDate(AVLTree tree, String date1, String date2) {
        long key1 = Converter.KeyStringConverter.ConvertStringDateToKey(date1);
        long key2 = Converter.KeyStringConverter.ConvertStringDateToKey(date2);

        if (key1 > key2) {
            System.out.println("Erro: Data inicial maior do que data final. Por favor insira uma data inicial maior do que a data final");
            return null;
        } else {
            List<Person.PersonInfo> personas = new ArrayList<Person.PersonInfo>();

            System.out.println("Registros entre " + date1 + " e " + date2);
            Node searchFrom = tree.SearchRange(tree.root, key1, key2, personas);
            tree.Search(searchFrom, key2, true, personas);

            return personas;
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

    // Exibe uma mensagem de ajuda com os comandos a serem utilizados na CLI
    private static void printHelp() {
        System.out.println("Digite o comando de acordo com a operação que deseja executar e pressione enter:");
        System.out.println("Abrir UI(f)");
        System.out.println("Utilizar CLI(c)");
        System.out.println("Para sair, digite e");
        //https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram-in-java
    }

    // Exibe uma mensagem de ajuda com os comandos a serem utilizados na CLI
    private static void printHelpCLI() {
        System.out.println("Bem vindo a CLI do programa. Digite o comando desejado e pressione enter:");
        System.out.println("Voltar para o inicio(i)");
        System.out.println("Importar valores de CSV(c): c caminho_do_arquivo");
        System.out.println("Buscar por CPF(b): b numero_de_cpf");
        System.out.println("Buscar por inicio do nome(n): b tres_primeiras_letras");
        System.out.println("Buscar por intervalo de datas(d): d data_inicio data_fim");
        System.out.println("Visualizar indexes(v cpf, v nome ou v data)");
        System.out.println("Executar testes automatizados(r)");
        System.out.println("Para sair, digite e");
    }

    // Cria uma arvore para ser exibida em CLI com uma visulização amigavel,
    // exibindo a arvore da esquerda para a direita
    public static void prettyPrintTree(Node root, int space, String field) {
        if (root == null) {
            return;
        }

        space += 5;
        prettyPrintTree(root.right, space, field);
        System.out.println();
        viewContent += "\n";

        for (int i = 5; i < space; i++) {
            System.out.print(" ");
            viewContent += " ";
        }

        if (field.equals("name")) {
            for (int i = 0; i <= root.person.length-1; i++) {
                System.out.print(root.person[i].name + ";");
                viewContent += root.person[i].name + ";";
            }
        } else if (field.equals("date")) {
            System.out.print(root.person[0].dateOfBirth);
            viewContent += root.person[0].dateOfBirth;
        } else {
            System.out.print(root.person[0].cpf);
            viewContent += root.person[0].cpf;
        }

        System.out.println();
        viewContent += "\n";
        prettyPrintTree(root.left, space, field);
    }

    // Retorna o caminho para o arquivo CSV de exemplo criado.
    public static String useSampleFile() {
        Path p = Paths.get("personinfo.csv");
        return p.toAbsolutePath().toString();
    }

    // Executa testes automatizados importando um arquivo de exemplo CSV,
    // e efetua testes de busca exibindo os resultados na tela.
    private static void runAutomatedTests() throws IOException {
        int opsCount = ReadCSVFile(useSampleFile());
        if (opsCount > 0) {
            Person.PersonInfo p = searchCPF(treeCPF, 38787549069L);
            if (p != null) {
                printAllData(p);
            }
            p = searchCPF(treeCPF, 36469186084L);
            if (p != null) {
                printAllData(p);
            }
            p = searchCPF(treeCPF, 40618933000L);
            if (p != null) {
                printAllData(p);
            }
            p = searchCPF(treeCPF, 40239840284L);
            if (p != null) {
                printAllData(p);
            }
            System.out.println("");

            Person.PersonInfo[] ps = searchNameByKey(treeName, "edu");
            if (ps != null) {
                for (int i = 0; i<= ps.length-1; i++) {
                    printAllData(ps[i]);
                }
            }
            ps = searchNameByKey(treeName, "ren");
            if (ps != null) {
                for (int i = 0; i<= ps.length-1; i++) {
                    printAllData(ps[i]);
                }
            }
            ps = searchNameByKey(treeName, "jul");
            if (ps != null) {
                for (int i = 0; i<= ps.length-1; i++) {
                    printAllData(ps[i]);
                }
            }
            ps = searchNameByKey(treeName, "arm");
            if (ps != null) {
                for (int i = 0; i<= ps.length-1; i++) {
                    printAllData(ps[i]);
                }
            }
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

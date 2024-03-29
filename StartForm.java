import Person.PersonInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartForm {
    public JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextArea sejaBemVindoATextArea;
    private JButton button1;
    private JTextField textPath;
    private JPanel inicioPanel;
    private JPanel uploadPanel;
    private JPanel searchPanel;
    private JTextField textCPF;
    private JButton buscarCPFButton;
    private JTextArea textAreaCPF;
    private JButton importarArquivoExemploButton;
    private JTextField textNome;
    private JButton buscarNomeButton;
    private JTextArea textAreaNome;
    private JTextField textFieldIniDate;
    private JButton dateButton;
    private JTextField textFieldEndDate;
    private JTextArea textAreaDate;
    private JButton btnViewCPF;
    private JButton btnViewName;
    private JButton btnViewDate;
    private JTextArea textAreaView;

    public StartForm() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opsCount = Main.ReadCSVFile(textPath.getText());
                if (opsCount > 0) {
                    JOptionPane.showMessageDialog(null, "CSV importado com successo. " + opsCount + " registros indexados.");
                    textPath.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "0 registros indexados, por favor verifique o caminho e o conteúdo do arquivo.");
                }
            }
        });
        buscarCPFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person.PersonInfo person = Main.searchCPF(Main.treeCPF, Long.parseLong(textCPF.getText()));

                String txContent = "";

                if (person != null) {
                    txContent = "CPF: " + person.cpf + ", RG: " + person.rg + ", Nome: " + person.name + ", Data de Nascimento(DD/MM/AAAA): " + person.dateOfBirth + ", Cidate de Nascimento: " + person.cityOfBirth;
                } else {
                    txContent = "Nenhum registro encontrado para o CPF informado.";
                }

                textAreaCPF.setText(txContent);
            }
        });
        importarArquivoExemploButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opsCount = Main.ReadCSVFile(Main.useSampleFile());
                if (opsCount > 0) {
                    JOptionPane.showMessageDialog(null, "CSV importado com successo. " + opsCount + " registros indexados.");
                    textPath.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "0 registros indexados, por favor verifique o caminho e o conteúdo do arquivo.");
                }
            }
        });
        buscarNomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txContent = "";
                String nameKey = textNome.getText();
                if (nameKey.length() < 3) {
                    System.out.println("Por favor, digite ao menos 3 caracteres para realizar a busca.");
                } else {
                    Person.PersonInfo[] ps = Main.searchNameByKey(Main.treeName, nameKey);
                    if (ps != null) {
                        for (int i = 0; i<= ps.length-1; i++) {
                            Person.PersonInfo person = ps[i];
                            txContent += "CPF: " + person.cpf + ", RG: " + person.rg + ", Nome: " + person.name + ", Data de Nascimento(DD/MM/AAAA): " + person.dateOfBirth + ", Cidate de Nascimento: " + person.cityOfBirth + "\n";
                        }
                    } else {
                        txContent = "Nenhum registro encontrado para o criterio inserido. Lembre-se que a busca considera somente as tres primeiras letras";
                    }
                }
                textAreaNome.setText(txContent);
            }
        });
        dateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txContent = "";
                String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher1 = pattern.matcher(textFieldIniDate.getText());
                Matcher matcher2 = pattern.matcher(textFieldEndDate.getText());

                if (matcher1.matches() && matcher2.matches()) {
                    List<PersonInfo> list = Main.searchDate(Main.treeDOB, textFieldIniDate.getText(), textFieldEndDate.getText());
                    if (list != null) {
                        for (int i = 0; i <= list.size()-1; i++) {
                            Person.PersonInfo person = list.get(i);
                            txContent += "CPF: " + person.cpf + ", RG: " + person.rg + ", Nome: " + person.name + ", Data de Nascimento(DD/MM/AAAA): " + person.dateOfBirth + ", Cidate de Nascimento: " + person.cityOfBirth + "\n";
                        }
                    } else {
                        txContent = "Nenhum registro encontrado para o criterio inserido";
                    }
                } else {
                    txContent = "Datas não estão de acordo com o formato DD/MM/YYYY.";
                }
                textAreaDate.setText(txContent);
            }
        });
        btnViewCPF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.viewContent = "";
                Main.prettyPrintTree(Main.treeCPF.root, 0, "cpf");

                textAreaView.setText(Main.viewContent);
            }
        });
        btnViewName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.viewContent = "";
                Main.prettyPrintTree(Main.treeName.root, 0, "name");

                textAreaView.setText(Main.viewContent);
            }
        });
        btnViewDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.viewContent = "";
                Main.prettyPrintTree(Main.treeDOB.root, 0, "date");

                textAreaView.setText(Main.viewContent);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

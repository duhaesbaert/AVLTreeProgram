import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartForm {
    public JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextArea sejaBemVindoATextArea;
    private JButton button1;
    private JTextField textPath;
    private JPanel inicioPanel;
    private JPanel uploadPanel;
    private JPanel searchPanel;

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
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

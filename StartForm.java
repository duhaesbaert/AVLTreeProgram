import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

                JOptionPane.showMessageDialog(null, "CSV importado com successo!");
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

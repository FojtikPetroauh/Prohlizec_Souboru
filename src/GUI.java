import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class GUI extends JFrame {

    private JTextArea txtArea;
    private JPanel panel1;
    private JButton saveBtn;
    private JButton saveAsBtn;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu file = new JMenu("File");
    private JMenuItem open = new JMenuItem("Otevřít");

    public GUI(){
        initMenu();
        setContentPane(panel1);
        open.addActionListener(e -> otevrit());
    }

    private void initMenu(){
        menuBar.add(file);
        file.add(open);
        setJMenuBar(menuBar);

        txtArea.setEditable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Notepad");
        pack();

    }

    public void otevrit() {
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new FileNameExtensionFilter("Textové soubory", "txt"));
        int vysledek = fc.showOpenDialog(this);
        if (vysledek == JFileChooser.APPROVE_OPTION) {
            File zvolenySoubor = fc.getSelectedFile();
            StringBuilder content = new StringBuilder();
            try (Scanner sc = new Scanner(new BufferedReader(new FileReader(zvolenySoubor)))) {
                while (sc.hasNextLine()) {
                    content.append(sc.nextLine()).append("\n");
                }
                txtArea.setText(String.valueOf(content));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

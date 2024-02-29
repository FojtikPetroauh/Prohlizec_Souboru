import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class GUI extends JFrame {

    private JTextArea txtArea;
    private JPanel panel1;
    private JButton saveBtn;
    private JButton saveAsBtn;
    private JCheckBox editCB;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu file = new JMenu("File");
    private JMenuItem open = new JMenuItem("Otevřít");
    private File vybranySoubor;

    public GUI(){
        initMenu();
        setContentPane(panel1);
        open.addActionListener(e -> otevrit());
        saveBtn.addActionListener(e -> uloz(vybranySoubor));
        saveAsBtn.addActionListener(e -> ulozJako());
    }

    private void initMenu(){
        menuBar.add(file);
        file.add(open);
        setJMenuBar(menuBar);
        txtArea.setEditable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Notepad");

        editCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editCB.isSelected()){
                    txtArea.setEditable(true);
                } else {
                    txtArea.setEditable(false);
                }
            }
        });

        pack();

    }



    public void otevrit() {
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new FileNameExtensionFilter("Textové soubory", "txt"));
        int vysledek = fc.showOpenDialog(this);
        if (vysledek == JFileChooser.APPROVE_OPTION) {
            vybranySoubor = fc.getSelectedFile();
            StringBuilder content = new StringBuilder();
            try (Scanner sc = new Scanner(new BufferedReader(new FileReader(vybranySoubor)))) {
                while (sc.hasNextLine()) {
                    content.append(sc.nextLine()).append("\n");
                }
                txtArea.setText(String.valueOf(content));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }



    public void uloz(File file){
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            pw.write(txtArea.getText());
            JOptionPane.showMessageDialog(this, "Uloženo");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Nastal problém: " + e.getLocalizedMessage());
        }
    }

    public void ulozJako(){
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new FileNameExtensionFilter("Text", "txt"));
        int vysledek = fc.showSaveDialog(this);
        if(vysledek == JFileChooser.APPROVE_OPTION){
            vybranySoubor = fc.getSelectedFile();
            vybranySoubor = new File(vybranySoubor.getAbsolutePath() + ".txt");
            uloz(vybranySoubor);
        }
    }

}

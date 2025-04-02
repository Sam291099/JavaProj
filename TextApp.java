import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.jar.JarFile;

public class TextApp extends JFrame implements ActionListener, ChangeListener {
    JTextArea area;
    JScrollPane pane;
    JSpinner spin;
    JButton btn;
    JColorChooser jcc;
    JComboBox jcb;
    JMenuBar jb;
    JMenu menu;
    JMenuItem save,open,exit;
    String[] font=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    public TextApp(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900,500);
        this.setLayout(new FlowLayout());
        area=new JTextArea();
        area.setPreferredSize(new Dimension(700,300));
        spin=new JSpinner();
        spin.setPreferredSize(new Dimension(50,25));
        spin.addChangeListener(this);
      //  jcc=new JColorChooser();
        jb=new JMenuBar();
        menu=new JMenu("File");
        save=new JMenuItem("Save");
        open=new JMenuItem("Open");
        exit=new JMenuItem("Exit");
        menu.add(save);
        menu.add(open);
        menu.add(exit);
        jb.add(menu);
        save.addActionListener(this);
        open.addActionListener(this);
        exit.addActionListener(this);
        this.setJMenuBar(jb);
        btn=new JButton("Color");
        jcb=new JComboBox(font);
        pane=new JScrollPane(area);
        jcb.addActionListener(this);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(pane);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        btn.addActionListener(this);
        this.add(spin);
        this.add(btn);
        this.add(jcb);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btn){
            jcc=new JColorChooser();
            Color clr=JColorChooser.showDialog(null,"Select a color",Color.black);
            area.setForeground(clr);
        }
        if (e.getSource()==jcb){
            area.setFont(new Font((String)jcb.getSelectedItem(),Font.PLAIN,(int)spin.getValue()));
        }
        if (e.getSource()==exit){
            System.exit(0);
        }
        if(e.getSource()==save){
            JFileChooser jf=new JFileChooser();
            jf.setCurrentDirectory(new File("."));
            int res=jf.showSaveDialog(null);
            PrintWriter write=null;
            try {
                if (res == JFileChooser.APPROVE_OPTION){
                    File file=new File(jf.getSelectedFile().getAbsolutePath());
                    write=new PrintWriter(file);
                    write.println(area.getText());
                    JOptionPane.showMessageDialog(null,"File Saved");
                }
            }catch (Exception k){
                JOptionPane.showMessageDialog(null,"Some Exception Occured");
            }
            finally{
                    write.close();
            }

        }
        if (e.getSource()==open){
            JFileChooser jf=new JFileChooser();
            jf.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter=new FileNameExtensionFilter("Text Files",".txt");
            jf.setFileFilter(filter);
            File file;
            int res=jf.showOpenDialog(null);
            if (res==JFileChooser.APPROVE_OPTION){
                Scanner fin=null;
                file=new File(jf.getSelectedFile().getAbsolutePath());
                try{
                    fin=new Scanner(file);
                    if (file.isFile()){
                        while (fin.hasNextLine()){
                            String line=fin.nextLine()+"\n";
                            area.append(line);
                            area.setFont(new Font((String)jcb.getSelectedItem(),Font.PLAIN,20));
                        }
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    fin.close();
                }
            }

        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        area.setFont(new Font(area.getFont().getFamily(),Font.PLAIN,(int)spin.getValue()));
    }
}

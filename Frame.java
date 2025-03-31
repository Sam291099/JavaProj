import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.mysql.cj.xdevapi.SqlStatement;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Frame extends JFrame implements ActionListener {
    static Connection con = null;
    JButton b1, b2, b3,b4;
    static JTextArea a1, a2;
    JScrollPane p1, p2;
    JComboBox c1;
    JLabel l1, l2;

    public Frame() {
        this.setSize(1920, 1080);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        b1 = new JButton("Connect");
        b1.setBounds(200, 800, 150, 30);
        this.add(b1);
        l1 = new JLabel("Your Query:");
        l1.setBounds(5, 25, 75, 30);
        l1.setFont(new Font("Ink - Free", Font.BOLD, 12));
        this.add(l1);
        l2 = new JLabel("Result:");
        l2.setBounds(600, 25, 75, 30);
        this.add(l2);
        a1 = new JTextArea();
        a1.setFont(new Font("Ink-Free", Font.BOLD, 15));
        a1.setBorder(BorderFactory.createBevelBorder(1));
        a1.setOpaque(true);
        a1.setLineWrap(true);
        a1.setWrapStyleWord(true);
        b1.addActionListener(e -> getConnect());
        p1 = new JScrollPane(a1);
        p1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        p1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        p1.setBounds(125, 25, 450, 150);
        this.add(p1);
        b2 = new JButton("Export File");
        b2.setBounds(25, 800, 150, 30);
        this.add(b2);
        a2 = new JTextArea();
        a2.setLineWrap(true);
        a2.setWrapStyleWord(true);
        p2 = new JScrollPane(a2);
        p2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        p2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        p2.setBounds(680, 25, 600, 500);
        this.add(p2);
        b3 = new JButton("Clear");
        b3.setBounds(380, 800, 150, 30);
        this.add(b3);
        b2.addActionListener(e -> newFile());
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b3) {
                    a1.setText("");
                    a2.setText("");
                }
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeCon();
            }
        });
        b4=new JButton("Run Query");
        b4.setBounds(535,800,150,30);
        this.add(b4);
        b4.addActionListener(e-> getResult());
        this.setVisible(true);
    }

    public static Connection getConnect() {
        if (con != null) {
            JOptionPane.showMessageDialog(null, "Already Connected");
            return con;
        } else {
            try {
                String driver = "com.mysql.cj.jdbc.Driver", url = "jdbc:mysql://localhost:3306/employee", user = "root", pass = "admin";
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pass);
                Statement sta=con.createStatement();
                String dbs=String.valueOf(sta.executeQuery("show databases"));

                JOptionPane.showMessageDialog(null, "Connection Established");
                return con;

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error " + e + " Occured during connection");
            }
            return null;
        }
    }

    public static void closeCon() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error occured during disconnection");
            }
        }
    }

    public void actionPerformed(ActionEvent e) {

    }

    public static void newFile() {
        String directory = "C:\\Users\\shrik\\OneDrive\\Desktop\\Swing Files";
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = directory + "\\File_" + timestamp + ".txt";
        File file = new File(filename);
        try {
            if (file.createNewFile()) {
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(a2.getText());
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Errror while writing file "+e);
                }
                JOptionPane.showMessageDialog(null, "File Saved To Location " + directory);
            } else {
                JOptionPane.showMessageDialog(null, "Error");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return;
    }

    public static void getResult() {
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Connect to DB First");
        } else {
            try {
                String query = a1.getText();
                Statement stat = getConnect().createStatement();
                ResultSet rs = stat.executeQuery(query);
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int ColumnCount = metaData.getColumnCount();
                for (int i = 1; i <= ColumnCount; i++) {
                    a2.append(metaData.getColumnName(i) + "\t");
                }
                a2.append("\n");

                while (rs.next()) {
                    for (int i = 1; i <= ColumnCount; i++) {
                        a2.append(rs.getString(i) + " "+"\t");
                    }
                    a2.append("\n");
                }
                stat.close();
                rs.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
}

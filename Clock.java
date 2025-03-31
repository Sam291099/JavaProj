import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clock{
    public static void main(String[] args) {
        Time t=new Time();
    }
}
class Time extends JFrame implements ActionListener {
    JLabel l,l1,l2;
    String time,d,m;
    SimpleDateFormat date,day,month;
    JButton b;


    public Time() {
        this.setSize(350, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        date = new SimpleDateFormat("hh:mm:ss a");
        time = date.format(Calendar.getInstance().getTime());
        day=new SimpleDateFormat("EEEE");
        month=new SimpleDateFormat("MM DD,yyyy");
        l=new JLabel();
        l1=new JLabel();
        l2=new JLabel();
        this.add(l);
        this.add(l2);
        this.add(l1);
        l1.setBounds(35,40,200,40);
        l2.setBounds(35,80,200,40);
        d=day.format(Calendar.getInstance().getTime());
        m=month.format(Calendar.getInstance().getTime());
        l1.setText(d);
        l2.setText(m);
        l1.setForeground(Color.white);
        l2.setForeground(Color.white);
        l1.setOpaque(true);
        l2.setOpaque(true);
        l1.setBackground(Color.black);
        l2.setBackground(Color.black);
        this.setLayout(null);
        l.setBounds(35,5,200,40);
        l.setFont(new Font("Verdana",Font.BOLD,20));
        l.setText(time);
        b=new JButton("Color");
        b.setBounds(210,170,100,30);
        l1.setFont(new Font("Verdana",Font.BOLD,20));
        l2.setFont(new Font("Verdana",Font.BOLD,20));
        this.add(b);
        b.addActionListener(this);
        l.setOpaque(true);
        l.setBackground(Color.BLACK);
        l.setForeground(Color.white);
        this.setVisible(true);
        updateTime();
    }

    public void updateTime() {
        while (true) {
            time = date.format(Calendar.getInstance().getTime());
            l.setText(time);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception Occured");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Color c=JColorChooser.showDialog(null,"Choose a colour",Color.black);
        l.setForeground(c);
        l1.setForeground(c);
        l2.setForeground(c);
    }

}
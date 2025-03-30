import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.TimeZone;

public class Digital {
    public static void main(String[] args) {
        Clock c=new Clock();
    }
}
class Clock extends JFrame implements ActionListener{
    String zone;
    ZoneId id;
    ZonedDateTime ztime;
    String [] times= TimeZone.getAvailableIDs();
    JMenuBar bar;
    JMenu menu;
    JMenuItem i1,i2,i3;
    int lapse=0,sec=0,min=0,hr=0,ms=0,hr1,min1,sec1;
    String hour=String.format("%02d",hr);
    String minute=String.format("%02d",min);
    String second=String.format("%02d",sec);
    String milisec=String.format("%02d",ms);
    /* Null Point Exception Here*/
    String hour1=String.format("%02d",hr1);
    String minute1=String.format("%02d",min1);
    String second1=String.format("%02d",sec1);
    /* ends*/
    JPanel p1,p2,p3;
    JLabel l1,l2,l3;
    CardLayout layout;
    Boolean started=false;
    JButton b1,b2,b3,b4;
    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("hh:mm:ss a");
    SimpleDateFormat format=new SimpleDateFormat("hh:mm:ss");
    String t;
    JComboBox cb;
    Timer time=new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lapse=lapse+10;
            hr=(lapse/3600000);
            min=(lapse/60000)%60;
            sec=(lapse/1000)%60;
            ms=(lapse/10)%100;
            l1.setText(hr+":"+min+":"+sec+":"+ms);
        }
    });

    public Clock(){
        bar=new JMenuBar();
        menu=new JMenu("Select");
        i1=new JMenuItem("StopWatch");
        i2=new JMenuItem("CLock");
        i3=new JMenuItem("Timer");
        bar.add(menu);
        menu.add(i1);
        menu.add(i2);
        menu.add(i3);
        this.setSize(600,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        layout =new CardLayout();
        this.setLayout(layout);
        p1=new JPanel();
        p1.setLayout(new GridBagLayout());
        l1=new JLabel();
        l1.setPreferredSize(new Dimension(250,75));
        l1.setHorizontalAlignment(JTextField.CENTER);
        l1.setText(hr+":"+min+":"+sec+":"+ms);
        l1.setFont(new Font("Verdena",Font.BOLD,35));
        l1.setBorder(BorderFactory.createBevelBorder(1));
        l1.setFocusable(false);
        p1.add(l1);
        b1=new JButton("Start");
        b1.setPreferredSize(new Dimension(100,30));
        b1.setFocusable(false);
        p1.add(b1);
        b1.addActionListener(this);
        b2 = new JButton("Reset");
        b2.setPreferredSize(new Dimension(100,30));
        b2.addActionListener(this);
        p1.add(b2);
        this.add(p1,"Panel 1");
        this.setJMenuBar(bar);
        p2=new JPanel();
        l2=new JLabel();
        l2.setPreferredSize(new Dimension(250,75));
        l2.setHorizontalAlignment(JTextField.CENTER);
        l2.setFont(new Font("Ink Free",Font.BOLD,35));
        l2.setBorder(BorderFactory.createBevelBorder(1));
        t=formatter.format(ZonedDateTime.now());
        l2.setText(t);
        p2.add(l2);
        this.add(p2,"Panel 2");
        cb=new JComboBox<>(times);
        p2.add(cb);
        p1.setBackground(new Color(135,206,235));
        l1.setOpaque(true);
        l1.setBackground(new Color(176,196,222));
        p2.setBackground(new Color(240, 255, 249));
        l2.setOpaque(true);
        l2.setBackground(new Color(255,240,245));
        i1.addActionListener(this);
        i2.addActionListener(this);
        cb.addActionListener(this);
        p3=new JPanel();
        p3.setLayout(new GridBagLayout());
        l3=new JLabel();
        l3.setPreferredSize(new Dimension(250,75));
        l3.setBorder(BorderFactory.createBevelBorder(5));
        l3.setHorizontalAlignment(JTextField.CENTER);
        p3.add(l3);
        this.add(p3,"Panel 3");
        l3.setOpaque(true);
        i3.addActionListener(this);
        this.setVisible(true);
        clocktimer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            if (started == false) {
                started = true;
                b1.setText("Stop");
                start();
            } else {
                started = false;
                b1.setText("Start");
                stop();
            }

        }
        if (e.getSource()==b2){
            started=false;
            b1.setText("Start");
            stop();
            reset();
        }
        if (e.getSource()==i1){
            layout.show(this.getContentPane(),"Panel 1");
        }
        if (e.getSource()==i2){
            started=false;
            stop();
            reset();
            layout.show(this.getContentPane(),"Panel 2");
            clocktimer.start();
        }
        if (e.getSource()==cb){
            getZoneTime();
        }
        if (e.getSource()==i3){
            layout.show(this.getContentPane(),"Panel 3");
            p3.setBackground(new Color(240,255,255));
            String ob=JOptionPane.showInputDialog(null,"Enter your desired time in hh:mm:ss format ");
            if (ob !=null && ob.matches("\\d{2}:\\d{2}:\\d{2}")){
                l3.setText(ob);
                l3.setFont(new Font("Serif",Font.BOLD,35));
                String [] parts= ob.split(":");
                hr1=Integer.parseInt(parts[0]);
                min1=Integer.parseInt(parts[1]);
                sec1=Integer.parseInt(parts[2]);

            }

        }
    }
    void start(){
        time.start();
    }
    void stop(){
        time.stop();
    }
    void reset(){
        ms=0;
        min=0;
        sec=0;
        hr=0;
        l1.setText(hr+":"+min+":"+sec+":"+ms);
    }
    void getZoneTime(){
        try{
        String zone=(String)cb.getSelectedItem();
        ZoneId id= ZoneId.of(zone);
        ZonedDateTime ztime=ZonedDateTime.now(id);
        l2.setText(formatter.format(ztime));}catch (Exception l){
            JOptionPane.showMessageDialog(null,"Error Occured");
        }
    }

    Timer clocktimer=new Timer(1000,e -> {
        zone = (String) cb.getSelectedItem();
        id = ZoneId.of(zone);
        ztime = ZonedDateTime.now(id);
        t = formatter.format(ztime);
        l2.setText(t);
    });

}

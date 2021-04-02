import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheatMenu extends JFrame implements ActionListener {
    private final Color bgColor = new Color(34,36,49);
    private final Font deFont = new Font("Verdana", Font.BOLD, 13);
    private final JCheckBox cGlow = new JCheckBox("GlowESP: false");
    private final JCheckBox cThird = new JCheckBox("Third Person: false");
    private final Cheats cheat = new Cheats();
    public CheatMenu() {
        cheat.start();
        cheat.setPriority(Thread.MAX_PRIORITY);
        WindowForm();


    }

    private void WindowForm(){
        this.setTitle("Gamingchair - CSGO");
        this.setResizable(false);
        this.setSize(250,440);
        this.getContentPane().setBackground(bgColor);
        this.setFont(deFont);
        this.setIconImage(new ImageIcon(getClass().getResource("/resources/Gamingchair.png")).getImage());
        this.setLocationRelativeTo(this);
        this.setUndecorated(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel title = new JLabel("Gamingchair");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Tahoma", Font.BOLD, 25));
        Panel nor = new Panel(new FlowLayout(FlowLayout.CENTER));
        nor.add(title);
        add(nor, BorderLayout.NORTH);
        Panel izq = new Panel(new GridLayout(9,2));
        cGlow.setForeground(Color.WHITE);
        cGlow.setBackground(bgColor);
        cGlow.setFont(deFont);
        cGlow.addActionListener(this);
        izq.add(cGlow);
        cThird.setForeground(Color.WHITE);
        cThird.setBackground(bgColor);
        cThird.setFont(deFont);
        cThird.addActionListener(this);
        izq.add(cThird);
        add(izq, BorderLayout.WEST);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cGlow){
            cheat.setGlowEnabled(cGlow.isSelected());
            cGlow.setText("GlowESP: " + cheat.isGlowEnabled());
        }
        if(e.getSource() == cThird){
            cheat.setThirdEnabled(cThird.isSelected());
            cThird.setText("Third Person: " + cheat.isThirdEnabled());
        }

    }
}

package selectMode;

import openWindow.InitialWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TopicSelect extends JFrame {
    public static TopicSelect topicSelect;
    private JButton Topic1;
    private JButton Topic2;
    private JButton Topic3;
    private JButton backButton;
    private int sss;//没有实际含义，只是为了根据点击不同的按钮来决定不同的modeSelect界面背景。

    public TopicSelect() {
        topicSelect = this;
        this.setSize(600, 400);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);

        Topic1 = new JButton("草原");
        Topic1.setFont(new Font("黑体", Font.BOLD, 18));
        Topic1.setBounds(250, 120, 90, 35);
        Topic1.setOpaque(false);
        Topic1.setContentAreaFilled(false);
        Topic1.setForeground(Color.BLACK);
        this.add(Topic1);
        Topic1.addActionListener(e -> {
            sss = 1;//下同，给sss赋值区分不同主题
            this.hide();
            new ModeSelect();
        });

        Topic2 = new JButton("冰原");
        Topic2.setFont(new Font("黑体", Font.BOLD, 18));
        Topic2.setBounds(250, 170, 90, 35);
        Topic2.setOpaque(false);
        Topic2.setContentAreaFilled(false);
        Topic2.setForeground(Color.BLACK);
        this.add(Topic2);
        Topic2.addActionListener(e -> {
            sss = 2;
            this.hide();
            new ModeSelect();
        });

        Topic3 = new JButton("沙漠");
        Topic3.setFont(new Font("黑体", Font.BOLD, 18));
        Topic3.setBounds(250, 220, 90, 35);
        Topic3.setOpaque(false);
        Topic3.setContentAreaFilled(false);
        Topic3.setForeground(Color.BLACK);
        this.add(Topic3);
        Topic3.addActionListener(e -> {
            sss = 3;
            this.hide();
            new ModeSelect();
        });

        backButton = new JButton("返回");
        backButton.setFont(new Font("黑体", Font.BOLD, 18));
        backButton.setBounds(450, 350, 100, 35);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(Color.BLACK);
        this.add(backButton);
        backButton.addActionListener(e -> {
            this.dispose();
            new InitialWindow();
        });
        if (this.sss == 1) {
            this.add(new Background1());
        } else if (this.sss == 2) {
            this.add(new Background1());//在这可以换不同背景
        } else if (this.sss == 3) {
            this.add(new Background1());
        }
        this.add(new Background1());
        this.setVisible(true);

    }

    public int getSss() {
        return sss;
    }

    public void setSss(int sss) {
        this.sss = sss;
    }
}

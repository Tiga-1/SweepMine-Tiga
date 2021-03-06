package openWindow;

import ModeOfGame.ReadGame;
import Music.playMusic;
import selectMode.TopicSelect;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;


public class InitialWindow extends JFrame implements ActionListener {
    //在任何地方通过InitialWindow.window获得对象
    public static InitialWindow window;

    private JButton newButton;
    private JButton getSaveBtn1;
    private JButton getSaveBTn2;
    private JButton cancelButton;
    private JButton musicButton;
    private int musicCount = 1;
    private JLabel TitleLabel;
    private ArrayList<ArrayList<Integer>> copyOfMine;
    private ArrayList<ArrayList<Integer>> copyOfState;
    private ArrayList<ArrayList<Integer>> copyOfScore;
    private ArrayList<String> copyOfName;
    private ArrayList<String> copyOfTime;
    Thread musicThread = new Thread(() -> {
        while (true) {
            new playMusic();
        }
    });


    public InitialWindow() {
        window = this;
        this.setSize(900, 600);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);

        TitleLabel = new JLabel("Minemine");
        TitleLabel.setBounds(360, 100, 200, 55);
        TitleLabel.setForeground(Color.BLACK);
        TitleLabel.setFont(new Font("times new roman", Font.ITALIC, 40));
        this.add(TitleLabel);



        newButton = new JButton("创建新的游戏");
        newButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        newButton.setBounds(350, 205, 200, 50);
        newButton.setForeground(Color.BLACK);
        newButton.setOpaque(false);
        newButton.setContentAreaFilled(false);
        this.add(newButton);
        newButton.addActionListener(this);


        getSaveBtn1 = new JButton("进入游戏存档");
        getSaveBtn1.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        getSaveBtn1.setBounds(350, 305, 200, 50);
        getSaveBtn1.setForeground(Color.BLACK);
        getSaveBtn1.setOpaque(false);
        getSaveBtn1.setContentAreaFilled(false);
        this.add(getSaveBtn1);
        getSaveBtn1.addActionListener(this);
        getSaveBtn1.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "Input the name you want to read");
            System.out.println("fileName :" + fileName);
            try {
                ArrayList<ArrayList<Integer>> copyOfMine = new ArrayList<>(readInitialDataToFile(fileName));
                this.copyOfMine = copyOfMine;
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(null, "找不到您要的存档，请更换存档名再试", "提示", JOptionPane.PLAIN_MESSAGE);
                new InitialWindow();
            }

            try {
                ArrayList<ArrayList<Integer>> copyOfState = new ArrayList<>(readInitialDataToFile(fileName + "state"));
                this.copyOfState = copyOfState;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            try {
                ArrayList<ArrayList<Integer>> copyOfScore = new ArrayList<>(readInitialDataToFile(fileName + "playerScores"));
                this.copyOfScore = copyOfScore;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            try {
                ArrayList<String> copyOfName = new ArrayList<>(readUserNameToFile(fileName + "playerID"));
                this.copyOfName = copyOfName;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            try {
                ArrayList<String> copyTime = new ArrayList(readUserNameToFile(fileName + "time"));
                this.copyOfTime = copyTime;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            dispose();
            new ReadGame();
        });



        cancelButton = new JButton("退出游戏");
        cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        cancelButton.setBounds(350, 405, 200, 50);
        cancelButton.setOpaque(false);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setForeground(Color.BLACK);
        this.add(cancelButton);
        cancelButton.addActionListener(this);


        musicButton = new JButton("音乐");
        musicButton.setFont(new Font("黑体", Font.BOLD, 18));
        musicButton.setBounds(600, 500, 100, 35);
        musicButton.setOpaque(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setForeground(Color.BLACK);
        this.add(musicButton);
        musicButton.addActionListener(e -> {
            if (musicCount == 1) {
                musicThread.start();// Lambda表达式
                musicCount++;
            } else if (musicCount == 2) {
                musicCount++;
                musicThread.suspend();
            } else if (musicCount == 3) {
                musicThread.resume();
                musicCount = 2;
            }
        });

        this.add(new InitialPanel());
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bt = (JButton) e.getSource();
        if (bt.equals(newButton)) {
            dispose();
            new TopicSelect();
        }

        if (e.getSource() == cancelButton) {
            JOptionPane.showMessageDialog(this, "welcome again");
            System.exit(0);
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override

            public void run() {
                new InitialWindow().setVisible(true);

            }
        });
    }


    //读取之前的雷场，需要传入一个参数，以明确读取的名字
    public ArrayList<ArrayList<Integer>> readInitialDataToFile(String name) throws IOException {
        ArrayList<ArrayList<Integer>> readDemo = new ArrayList<>();
        File file = new File("E:\\project 的存档", name);
        Reader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        if (file.exists()) {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] strings = s.split("\t");
                readDemo.add(new ArrayList());
                for (int i = 0; i < strings.length; i++) {
                    int num = Integer.parseInt(strings[i]);
                    readDemo.get(readDemo.size() - 1).add(i, num);
                }
            }
        }
        bufferedReader.close();
        return readDemo;
    }


    public ArrayList<String> readUserNameToFile(String name) throws IOException {
        ArrayList<String> readDemo = new ArrayList<>();
        File file = new File("E:\\project 的存档", name);
        Reader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        if (file.exists()) {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] strings = s.split("\t");
                for (int i = 0; i < strings.length; i++) {
                    readDemo.add(i, strings[i]);
                }
            }
        }
        bufferedReader.close();
        return readDemo;
    }


    public ArrayList<ArrayList<Integer>> getCopyOfMine() {
        return copyOfMine;
    }

    public ArrayList<ArrayList<Integer>> getCopyOfState() {
        return copyOfState;
    }

    public ArrayList<ArrayList<Integer>> getCopyOfScore() {
        return copyOfScore;
    }

    public ArrayList<String> getCopyOfName() {
        return copyOfName;
    }

    public ArrayList<String> getCopyOfTime() {
        return copyOfTime;
    }
}

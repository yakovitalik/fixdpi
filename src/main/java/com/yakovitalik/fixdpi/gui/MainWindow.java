package com.yakovitalik.fixdpi.gui;

import com.yakovitalik.fixdpi.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class MainWindow extends JFrame {

    private static final String GREETING_MESSAGE = "Для запуска прокси нажмите кнопку";
    private static final String STARTED_MESSAGE = "Прокси запущен на 127.0.0.1:8881.";

    private final JButton startButton, stopButton;

    private final JLabel label;

    private final EHandler handler = new EHandler();

    public MainWindow(String s) {
        super(s);
        setLayout(new FlowLayout());
        var container = getContentPane();
        container.setBackground(Color.GRAY);
        container.setForeground(Color.RED);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createAboutMenu());
        menuBar.setBackground(Color.GRAY);
        setJMenuBar(menuBar);
        startButton = new JButton("          Запуск прокси          ");
        startButton.setBackground(Color.LIGHT_GRAY);
        stopButton = new JButton("          Остановить и выйти          ");
        stopButton.setBackground(Color.LIGHT_GRAY);

        label = new JLabel(GREETING_MESSAGE);
        var font = new Font("Verdana", Font.BOLD, 13);
        label.setFont(font);

        add(label);
        add(startButton);
        add(stopButton);

        startButton.addActionListener(handler);
        stopButton.addActionListener(handler);
    }

    private JMenu createAboutMenu() {
        JMenu menu = new JMenu("Меню");
        JMenuItem aboutProg = new JMenuItem("О программе");
        menu.add(aboutProg);
        aboutProg.addActionListener(arg0 -> AboutStart.start());

        return menu;
    }

    public class EHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startButton) {
                ProxyStarter.startProxy();
                label.setText(STARTED_MESSAGE);
            }

            if(e.getSource() == stopButton) {
                System.exit(0);
            }
        }
    }
}

package com.yakovitalik.fixdpi;

import com.yakovitalik.fixdpi.gui.AboutStart;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class MainWindow extends JFrame {

    private static final String STARTED_MESSAGE = "Прокси запущен на 127.0.0.1:8881.";

    private final JButton startButton, stopButton;

    private final EHandler handler = new EHandler();

    private JLabel greetingLabel, startedLabel, emptyString;

    public MainWindow(String s) {
        super(s);
        setLayout(new FlowLayout());
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createAboutMenu());
        setJMenuBar(menuBar);
        startButton = new JButton("          Запуск прокси          ");
        stopButton = new JButton("          Остановить и выйти          ");

        startedLabel = new JLabel("");
        startedLabel.setFont(new Font("Verdana", Font.PLAIN, 13));

        greetingLabel = new JLabel("Для запуска прокси нажмите кнопку");
        greetingLabel.setFont(new Font("Verdana", Font.PLAIN, 13));

        add(greetingLabel);
        add(startedLabel);
        add(startButton);
        add(stopButton);

        startButton.addActionListener(handler);
        stopButton.addActionListener(handler);
    }

    private JMenu createAboutMenu() {
        JMenu menu = new JMenu("Меню");

        JMenuItem aboutProg = new JMenuItem("О программе");
        menu.add(aboutProg);

        aboutProg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                AboutStart.start();
            }
        });

        return menu;
    }

    public class EHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startButton) {
                ProxyStarter.startProxy();
                startedLabel.setText(STARTED_MESSAGE);
            }

            if(e.getSource() == stopButton) {
                System.exit(0);
            }
        }
    }
}

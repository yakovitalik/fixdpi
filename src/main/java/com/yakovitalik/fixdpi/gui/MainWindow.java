package com.yakovitalik.fixdpi.gui;

import static com.yakovitalik.fixdpi.gui.Colors.BACKGROUND_COLOR;
import static com.yakovitalik.fixdpi.gui.Colors.MENU_COLOR;
import static com.yakovitalik.fixdpi.gui.Colors.START_BUTTON_COLOR;
import static com.yakovitalik.fixdpi.gui.Colors.STOP_BUTTON_COLOR;

import com.yakovitalik.fixdpi.ProxyStarter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainWindow extends JFrame {

    private static final String GREETING_MESSAGE = "Для запуска прокси нажмите кнопку";
    private static final String STARTED_MESSAGE = "Прокси запущен на 127.0.0.1:8881.";

    private final RoundedButton startButton, stopButton;
    private final JLabel label;
    private final EHandler handler = new EHandler();

    public MainWindow(String s) {
        super(s);
        setSize(400, 260);
        setLayout(new FlowLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        setJMenuBar(createMenuBar());

        startButton = new RoundedButton("Запуск прокси", 10);
        startButton.setPreferredSize(new Dimension(200, 20));
        startButton.setBackground(START_BUTTON_COLOR);
        startButton.setForeground(Color.BLACK);
        startButton.setFont(new Font("Arial", Font.BOLD, 14));

        stopButton = new RoundedButton("Остановить и выйти", 10);
        stopButton.setPreferredSize(new Dimension(200, 20));
        stopButton.setBackground(STOP_BUTTON_COLOR);
        stopButton.setForeground(Color.BLACK);
        stopButton.setFont(new Font("Arial", Font.BOLD, 14));
        stopButton.setVisible(false);

        label = new JLabel(GREETING_MESSAGE);
        var font = new Font("Verdana", Font.BOLD, 13);
        label.setFont(font);

        add(label);
        add(startButton);
        add(stopButton);

        startButton.addActionListener(handler);
        stopButton.addActionListener(handler);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createAboutMenu());
        menuBar.setBackground(MENU_COLOR);

        return menuBar;
    }

    private JMenu createAboutMenu() {
        JMenu menu = new JMenu("Меню");
        JMenuItem aboutProg = new JMenuItem("О программе");
        aboutProg.getComponent().setBackground(MENU_COLOR);
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
                startButton.setVisible(false);
                stopButton.setVisible(true);
            }

            if(e.getSource() == stopButton) {
                System.exit(0);
            }
        }
    }
}

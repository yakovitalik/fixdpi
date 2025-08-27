package com.yakovitalik.fixdpi;

import static com.yakovitalik.fixdpi.gui.Colors.BACKGROUND_COLOR;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

import com.yakovitalik.fixdpi.gui.MainWindow;
import java.net.URL;
import javax.swing.ImageIcon;

public class Application {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow("CoolProxy");
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainWindow.setResizable(true);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.getContentPane().setBackground(BACKGROUND_COLOR);
        URL imageUrl = Application.class.getClassLoader().getResource("images/logo.png");
        ImageIcon imageIcon = new ImageIcon(imageUrl);
        mainWindow.setIconImage(imageIcon.getImage());
    }
}

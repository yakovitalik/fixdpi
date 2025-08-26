package com.yakovitalik.fixdpi.gui;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class About extends JFrame {

    private final JLabel label;

    private final String text = "<html>\n" +
            "    <h2>FixDPI ver 2.0</h2>\n" +
            "    Программа позволяет смотреть Ютуб в России, обойдя блокировку DPI <br>\n" +
            "    при помощи локального запуска прокси с обработкой пакетов <br>\n" +
            "    <br>\n" +
            "    Для работы необходимо задать прокси по адресу 127.0.0.1, порт:8881<br>\n" +
            "    Для этого, лучше всего использовать браузер Mozilla Firefox, <br>\n" +
            "    так как у него можно задать прокси-сервер в настройках<br>\n" +
            "    <br>\n" +
            "    Для работы требуется версия Java (JDK версии 17 и выше)<br>\n" +
            "    <br>\n" +
            "    Автор: Виталий Яковлев, vintyak@gmail.com<br>\n" +
            "</html>";

    public About(String s) {
        super(s);
        setLayout(new FlowLayout());
        label = new JLabel(text);
        getContentPane().setBackground(Color.GRAY);

        add(label);
    }
}


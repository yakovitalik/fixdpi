package com.yakovitalik.fixdpi.gui;

import static com.yakovitalik.fixdpi.gui.Colors.BACKGROUND_COLOR;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class About extends JFrame {

    private final JLabel label;

    private final String text = "<html>\n" +
            "    <h2>CoolProxy</h2>\n" +
            "    Программа позволяет смотреть Ютуб в России, без использования VPN, <br>\n" +
            "    без смены страны и местоположения, без ограничения скорости и разрешения. <br>\n" +
            "    Запуск прокси-сервера происходит локально, на вашем компьютере.<br>\n" +
            "    <br>\n" +
            "    Для работы необходимо задать в браузере прокси по адресу 127.0.0.1, порт:8881<br>\n" +
            "    Для этого, лучше всего использовать браузер Mozilla Firefox, <br>\n" +
            "    так как у него можно задать прокси-сервер в настройках<br>\n" +
            "    <br>\n" +
            "    Для работы требуется установка Java (JDK версии 17 и выше)<br>\n" +
            "    <br>\n" +
            "    Автор: Виталий Яковлев, vintyak@gmail.com<br>\n" +
            "</html>";

    public About(String s) {
        super(s);
        setLayout(new FlowLayout());
        label = new JLabel(text);
        getContentPane().setBackground(BACKGROUND_COLOR);

        add(label);
    }
}


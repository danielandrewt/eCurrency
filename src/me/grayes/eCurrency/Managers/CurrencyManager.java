package me.grayes.eCurrency.Managers;

import me.grayes.eCurrency.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CurrencyManager {

    public Main plugin;

    private HashMap<UUID, Integer> currency = new HashMap<UUID, Integer>();

    public CurrencyManager(Main plugin) {
        this.plugin = plugin;
    }

    public void saveCurrencyFile() throws FileNotFoundException, IOException {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            File file = new File("CurrencyFile/currency.dat");
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            //Check if a player has any previous currency saves.
            if (currency.get(p.getUniqueId()) != null) {
                //If exists then put a hashmap.
                currency.put(p.getUniqueId(), currency.get(p.getUniqueId()));
            }

            try {
                output.writeObject(currency);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadCurrencyFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("CurrencyFile/currency.dat");

        if (file != null) {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObject = input.readObject();
            input.close();

            if (!(readObject instanceof HashMap)) {
                throw new IOException("Listed Data is not a HashMap!");
            }

                currency = (HashMap<UUID, Integer>) readObject;
                for (UUID key : currency.keySet()) {
                    currency.put(key, currency.get(key));
            }
        }
    }

    public void addCurrencyToPlayer(OfflinePlayer p, int amount) {
        if (currency.get(p.getUniqueId().toString()) != null) {
            currency.put(p.getUniqueId(), currency.get(p.getUniqueId()) + amount);
        } else {
            currency.put(p.getUniqueId(), amount);
        }
    }

    public void removeCurrencyFromPlayer(OfflinePlayer p, int amount) {
        if (currency.get(p.getUniqueId().toString()) != null) {
            currency.put(p.getUniqueId(), currency.get(p.getUniqueId()) - amount);
        }
    }

    public void setPlayerCurrency(OfflinePlayer p, int amount) {
        currency.put(p.getUniqueId(), amount);
    }

    public int getPlayerCurrency(OfflinePlayer p) {
        if (currency.get(p.getUniqueId()) != null) {
            return currency.get(p.getUniqueId());
        } else {
            return 0;
        }
    }
}

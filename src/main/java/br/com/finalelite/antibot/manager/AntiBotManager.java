package br.com.finalelite.antibot.manager;

import br.com.finalelite.antibot.AntiBot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * @author Willian Gois (github/willgoix)
 */
public class AntiBotManager {

    private HashMap<String, Long> verifieds = new HashMap<>();

    public AntiBotManager(AntiBot antiBot) {
        addLoggerFilter(antiBot);

        antiBot.getProxy().getScheduler().schedule(antiBot, () -> {
            //antiBot.getProxy().getConsole().sendMessage("§a[AntiBot] §7Resetando contas verificadas.");
            verifieds.clear();
        }, 25, 25, TimeUnit.SECONDS);
    }

    public boolean verifyName(String name){
        return (name.length() > 16 || name.contains(" ") || name.replace("_", "").matches("[^A-Za-z0-9]"));
    }

    private void addLoggerFilter(AntiBot antiBot) {
        String[] regex = new String[]{
                "{0} has connected",
                "{0} disconnected with: {1}",
                "{0} has disconnected"};

        antiBot.getProxy().getLogger().setFilter(new Filter() {
            @Override
            public boolean isLoggable(LogRecord record) {
                if (record.getMessage() != null) {
                    for (String message : regex) {
                        if (record.getMessage().contains(message)) {
                            return false;
                        }
                    }
                }
                return true;
            }
        });
    }

    public Map<String, Long> getVerifieds() {
        return verifieds;
    }
}

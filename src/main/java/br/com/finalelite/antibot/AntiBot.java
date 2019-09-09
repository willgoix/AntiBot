package br.com.finalelite.antibot;

import br.com.finalelite.antibot.command.AntiBotCommand;
import br.com.finalelite.antibot.listener.AntiBotListener;
import br.com.finalelite.antibot.manager.AntiBotManager;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * @author Willian Gois (github/willgoix)
 */
public class AntiBot extends Plugin{

    private AntiBotManager antiBotManager;
    private static AntiBot instance;

    @Override
    public void onEnable() {
        instance = this;
        antiBotManager = new AntiBotManager(this);

        getProxy().getPluginManager().registerListener(this, new AntiBotListener(this));
        getProxy().getPluginManager().registerCommand(this, new AntiBotCommand(this));

        getProxy().getConsole().sendMessage("§a[AntiBot] §7Ativado.");
    }

    @Override
    public void onDisable() {
        getProxy().getConsole().sendMessage("§a[AntiBot] §7Desativado.");
    }

    public static AntiBot getInstance() {
        return instance;
    }

    public AntiBotManager getAntiBotManager() {
        return antiBotManager;
    }
}

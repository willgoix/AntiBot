package br.com.finalelite.antibot.listener;

import br.com.finalelite.antibot.AntiBot;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.logging.Level;

/**
 * @author Willian Gois (github/willgoix)
 */
public class AntiBotListener implements Listener{

    private AntiBot antiBot;

    public AntiBotListener(AntiBot antiBot){
        this.antiBot = antiBot;
    }

    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        String nick = event.getConnection().getName();

        if (antiBot.getAntiBotManager().verifyName(nick)){
            event.getConnection().disconnect("§cVocê está usando um nickname inválido, retire os caracteres espaciais (!, @, #, [, ], -, etc)");
            return;
        }

        if (!antiBot.getAntiBotManager().getVerifieds().containsKey(nick)){
            antiBot.getAntiBotManager().getVerifieds().put(nick, System.currentTimeMillis());
            event.getConnection().disconnect("§aSua conexão está verificada! Conecte-se novamente.\n§7(verificação válida por 25 segundos)");
        }else{
            antiBot.getProxy().getLogger().log(Level.WARNING, "{0} conectado com conexao verificada.", new Object[]{nick});
        }
    }
}
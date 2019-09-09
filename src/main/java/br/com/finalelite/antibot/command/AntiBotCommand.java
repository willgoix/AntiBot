package br.com.finalelite.antibot.command;

import br.com.finalelite.antibot.AntiBot;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * @author Willian Gois (github/willgoix)
 */
public class AntiBotCommand extends Command {

    private AntiBot antiBot;

    public AntiBotCommand(AntiBot antiBot) {
        super("antibot", "antibot.comando");
        this.antiBot = antiBot;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            sender.sendMessage("§cComando disponível somente no console.");
            return;
        }

        int five = 0;    // requests há cinco minutos
        int ten = 0;     // requests há dez minutos
        int fifteen = 0; // requests há quinze minutos
        int thirty = 0;  // requests há trinta minutos

        for (Entry<String, Long> entry : antiBot.getAntiBotManager().getVerifieds().entrySet()) {
            long timestamp = System.currentTimeMillis() - entry.getValue();

            if (timestamp < TimeUnit.MINUTES.toMillis(5)) {
                five++;
            } else if (timestamp < TimeUnit.MINUTES.toMillis(10)) {
                ten++;
            } else if (timestamp < TimeUnit.MINUTES.toMillis(15)) {
                fifteen++;
            } else if (timestamp < TimeUnit.MINUTES.toMillis(30)) {
                thirty++;
            }
        }

        sender.sendMessages(
                " Relatório de requisição de verificações ao AntiBot em:",
                "",
                " 5 minutos: §8" + (five == 0 ? "SEM DADOS" : getColored(five)),
                " 10 minutos: §8" + (ten == 0 ? "SEM DADOS" : getColored(ten)),
                " 15 minutos: §8" + (fifteen == 0 ? "SEM DADOS" : getColored(fifteen)),
                " 30 minutos: §8" + (thirty == 0 ? "SEM DADOS" : getColored(thirty)),
                " últimos 60 minutos: §a" + antiBot.getAntiBotManager().getVerifieds().size(),
                ""
        );
    }

    private String getColored(int number) {
        if (number < 5) {
            return "§a" + number;
        } else if (number < 15) {
            return "§e" + number;
        } else if (number < 35) {
            return "§6" + number;
        } else {
            return "§c" + number;
        }
    }
}

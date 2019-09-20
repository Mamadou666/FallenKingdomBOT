package re.alwyn974.fallenkingdom.bot.cmds;

import static re.alwyn974.fallenkingdom.bot.FallenKingdomBOT.i;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import re.alwyn974.fallenkingdom.bot.Command;
import re.alwyn974.fallenkingdom.bot.FallenKingdomBOT;

public class HelpCMD extends ListenerAdapter implements Command {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		if (e.getMessage().getContentRaw().startsWith(getPrefix())) {
			String fullCmd = e.getMessage().getContentRaw().substring(1);
			String[] args = fullCmd.split(" ");
			String primaryCmd = args[0];
			
			if (primaryCmd.equalsIgnoreCase("help")) {
				i("Commande : %s", getName());
				i("Utilisateur : %s", e.getAuthor().getAsTag() + " | " + e.getAuthor().getId());
				i("Channel : %s", e.getChannel().getName());
				
				e.getChannel().sendMessage(new EmbedBuilder()
						.setColor(Color.CYAN)
						.setTitle("Liste des commandes")
						.addField(new FkRoleCMD().getName(), "Permet de créer le message pour choisir les Teams !", false)
						.addField(new ClearCMD().getName(), "Permet de clear les messages", false)
						.addField(new ServerCMD().getName(), "Permet d'avoir des informations sur le serveur", false)
						.addField(new RoleReactionCMD().getName(), "Permet de configurer le rôle reçu avec l'émoji prédéfini", false)
						.addField(new RemoveRolesTeamCMD().getName(), "Permet de supprimer la Team de tout les Joueurs", false)
						.setFooter("Développé par " + e.getAuthor().getAsTag() + " | " + FallenKingdomBOT.getActivity(), e.getGuild().getIconUrl())
						.build()).queue();
			}
		}
	}

	@Override
	public String getName() {
		return getPrefix() + "help";
	}

}

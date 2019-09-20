package re.alwyn974.fallenkingdom.bot.cmds;

import static re.alwyn974.fallenkingdom.bot.FallenKingdomBOT.i;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import re.alwyn974.fallenkingdom.bot.Command;
import re.alwyn974.fallenkingdom.bot.FallenKingdomBOT;

public class FkRoleCMD extends ListenerAdapter implements Command {

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		if (e.getMessage().getContentRaw().startsWith(getPrefix())) {
			String fullCmd = e.getMessage().getContentRaw().substring(1);
			String[] args = fullCmd.split(" ");
			String primaryCmd = args[0];
			
			if (primaryCmd.equalsIgnoreCase("fkrole") && args.length <= 1 && e.getGuild().getMember(e.getAuthor()).hasPermission(Permission.ADMINISTRATOR)) {
				e.getChannel().sendMessage(error().setDescription("Utilisation: `/fkrole <channel> pour créer le message de choix des Teams !`").build()).complete();
			}

			if (primaryCmd.equalsIgnoreCase("fkrole") && args.length >= 2 && e.getGuild().getMember(e.getAuthor()).hasPermission(Permission.ADMINISTRATOR)) {
				
				i("Commande : %s", getName());
				i("Utilisateur : %s", e.getAuthor().getAsTag() + " | " + e.getAuthor().getId());
				
				String channelID = e.getMessage().getMentionedChannels().get(0).getId();
				String channelName = e.getMessage().getMentionedChannels().get(0).getName();
				i("Channel : %s", channelName);
				
				e.getGuild().getTextChannelById(channelID).sendMessage(new EmbedBuilder()
						.setColor(Color.CYAN)
						.setTitle("Choisis ta team !")
						.addField("🔵 = Team Bleu", "", false)
						.addField("🔴 = Team Rouge", "", false)
						.addField("🔶 = Team Orange", "", false)
						.addField("🍏 = Team Verte", "", false)
						.setFooter("Développé par " + e.getAuthor().getAsTag() + " | " + FallenKingdomBOT.getActivity(), e.getGuild().getIconUrl())
						.build()).queue();
				
				
				Message rep = e.getChannel().sendMessage(success().setDescription("Successfully create the message").build()).complete();
				
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						rep.delete().queue();
					}
				}, 5000);
			}
		}
	}

	@Override
	public String getName() {
		return getPrefix() + "fkrole";
	}

}

package re.alwyn974.fallenkingdom.bot.cmds;

import static re.alwyn974.fallenkingdom.bot.FallenKingdomBOT.i;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import re.alwyn974.fallenkingdom.bot.Command;

public class ServerCMD extends ListenerAdapter implements Command {

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		if (e.getMessage().getContentRaw().startsWith(getPrefix())) {
			String fullCmd = e.getMessage().getContentRaw().substring(1);
			String[] args = fullCmd.split(" ");
			String primaryCmd = args[0];

			if (primaryCmd.equalsIgnoreCase("server") && e.getGuild().getMember(e.getAuthor()).hasPermission(Permission.ADMINISTRATOR)) {
				
				i("Commande : %s", getName());
				i("Utilisateur : %s", e.getMember().getEffectiveName() + " | " + e.getAuthor().getId());
				
				Guild g = e.getGuild();
				e.getChannel().sendMessage(new EmbedBuilder()
						.setColor(Color.cyan)
						.setTitle("Guild information for guild " + g.getName(), null)
						.setThumbnail(g.getIconUrl())
						.setDescription("\n\n")
						.addField("Name", g.getName(), false)
						.addField("ID", g.getId(), false)
						.addField("Owner", g.getOwner().getAsMention(), false)
						.addField("Members", g.getMembers().size() + " (Online: "+ g.getMembers().stream().filter(m -> !m.getOnlineStatus().equals(OnlineStatus.OFFLINE)).count()+ ")", false)
						.addField("Prefix", "`" + getPrefix() + "`", false).build()).queue();
			}
		}
	}
	
	@Override
	public String getName() {
		return getPrefix() +"server";
	}
}

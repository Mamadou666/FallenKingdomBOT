package re.alwyn974.fallenkingdom.bot.cmds;

import static re.alwyn974.fallenkingdom.bot.FallenKingdomBOT.i;

import java.util.List;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import re.alwyn974.fallenkingdom.bot.Command;

public class RemoveRolesTeamCMD extends ListenerAdapter implements Command {

	private String[] playerRoles = new String[] { "623141889444544541", "623785256956198912", "623785490729926657",
			"623785881425412117" };

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		if (e.getMessage().getContentRaw().startsWith(getPrefix())) {
			String fullCmd = e.getMessage().getContentRaw().substring(1);
			String[] args = fullCmd.split(" ");
			String primaryCmd = args[0];

			if (primaryCmd.equalsIgnoreCase("removeRoles")
					&& e.getGuild().getMember(e.getAuthor()).hasPermission(Permission.ADMINISTRATOR)) {
				i("Commande : %s", getName());
				i("Utilisateur : %s", e.getAuthor().getAsTag() + " | " + e.getAuthor().getId());
				
				for (int i=0; i< playerRoles.length; i++) {
					Role roles = e.getGuild().getRoleById(playerRoles[i]);
					List<Member> members = e.getGuild().getMembersWithRoles(roles);
					
					for (int j=0; j < members.size(); j++) {
						e.getGuild().getController().removeSingleRoleFromMember(members.get(j), roles).queue();
						i("Remove role %s to %s", roles.getName(), members.get(j).getEffectiveName());
					}
				}

			}
		}
	}

	@Override
	public String getName() {
		return getPrefix() + "removeRoles";
	}

}

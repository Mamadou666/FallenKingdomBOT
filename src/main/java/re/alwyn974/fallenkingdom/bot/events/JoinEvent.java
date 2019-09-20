package re.alwyn974.fallenkingdom.bot.events;

import static re.alwyn974.fallenkingdom.bot.FallenKingdomBOT.i;

import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JoinEvent extends ListenerAdapter {

	private String[] playerRoles = new String[] { "623786269431824384", "623786529948696579", "623141694220533770" };

	public void onGuildMemberJoin(GuildMemberJoinEvent e) {
		i("Member Join : %s", e.getMember().getEffectiveName());
		for (int i = 0; i < playerRoles.length; i++) {
			e.getGuild().getController().addSingleRoleToMember(e.getMember(), e.getGuild().getRoleById(playerRoles[i])).queue();
			i("Adding Role [%s] to %s", e.getGuild().getRoleById(playerRoles[i]).getName(),e.getMember().getEffectiveName());
		}
	}
}

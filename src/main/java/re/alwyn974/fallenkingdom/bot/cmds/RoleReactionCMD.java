package re.alwyn974.fallenkingdom.bot.cmds;

import static re.alwyn974.fallenkingdom.bot.FallenKingdomBOT.i;

import java.io.File;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import re.alwyn974.fallenkingdom.bot.Command;
import re.alwyn974.fallenkingdom.bot.Saver;

public class RoleReactionCMD extends ListenerAdapter implements Command {

	String channelID = "";
	String message = "";
	String reaction = "";
	String roleID = "";

	private Saver saver = new Saver(new File("rolereaction.txt"));

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		if (e.getMessage().getContentRaw().startsWith(getPrefix())) {
			String fullCmd = e.getMessage().getContentRaw().substring(1);
			String[] args = fullCmd.split(" ");
			String primaryCmd = args[0];

			if (primaryCmd.equalsIgnoreCase("addRole") && args.length <= 3) {
				e.getChannel().sendMessage(
						error().setDescription("Utilisation: `/addRole <channel> <message> <reaction> <role>`").build())
						.complete();
			}

			if (primaryCmd.equalsIgnoreCase("addRole") && args.length > 4) {

				i("Commande : %s", getName());
				i("Utilisateur : %s", e.getAuthor().getAsTag() + " | " + e.getAuthor().getId());

				channelID = e.getMessage().getMentionedChannels().get(0).getId();
				String channelName = e.getMessage().getMentionedChannels().get(0).getName();
				message = args[2];
				reaction = args[3];
				roleID = e.getMessage().getMentionedRoles().get(0).getId();
				String role = e.getMessage().getMentionedRoles().get(0).getName();

				saver.set(reaction, roleID);
				saver.set(channelName, channelID);

				e.getGuild().getTextChannelById(channelID).addReactionById(message, reaction).queue();
				i("====================");
				i("Adding Reaction : %s", reaction);
				i("Channel : %s", channelName);
				i("Message : %s", message);
				i("Role : %s", role);

			}

			if (primaryCmd.equalsIgnoreCase("removeRole") && args.length <= 2) {
				e.getChannel().sendMessage(
						error().setDescription("Utilisation: `/removeRole <channel> <message> <reaction>`").build())
						.complete();
			}

			if (primaryCmd.equalsIgnoreCase("removeRole") && args.length > 3) {
				i("Commande : %s", "/removeRole");
				i("Utilisateur : %s", e.getMember().getEffectiveName() + " | " + e.getAuthor().getId());

				String channelID = e.getMessage().getMentionedChannels().get(0).getId();
				String message = args[2];
				String reaction = args[3];

				i("Remove reaction %s on %s in %s", reaction, message, channelID);
				
				e.getGuild().getTextChannelById(channelID).removeReactionById(message, reaction).queue();
			}
		}
	}

	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
		System.err.println(e.getReaction().getReactionEmote().getName());
		System.err.println(saver.get(e.getReaction().getReactionEmote().getName()));
		
		if (e.getChannel().getId().equals(saver.get(e.getChannel().getName())) && !e.getUser().isBot()) {
			e.getGuild().getController().addSingleRoleToMember(e.getMember(),
					e.getGuild().getRoleById(saver.get(e.getReaction().getReactionEmote().getName()))).queue();
			i("====================");
			i("Reaction Role Add");
			i("Role %s added to %s",
					e.getGuild().getRoleById(saver.get(e.getReaction().getReactionEmote().getName())).getName(),
					e.getMember().getEffectiveName());
		}
	}

	@Override
	public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent e) {
		System.err.println(e.getReaction().getReactionEmote().getName());
		System.err.println(saver.get(e.getReaction().getReactionEmote().getName()));
		
		if (e.getChannel().getId().equals(saver.get(e.getChannel().getName())) && !e.getUser().isBot()) {
			e.getGuild().getController().removeSingleRoleFromMember(e.getMember(),
					e.getGuild().getRoleById(saver.get(e.getReaction().getReactionEmote().getName()))).queue();
			i("====================");
			i("Reaction Role Remove");
			i("Role %s remove to %s",
					e.getGuild().getRoleById(saver.get(e.getReaction().getReactionEmote().getName())).getName(),
					e.getMember().getEffectiveName());
		}
	}

	@Override
	public String getName() {
		return getPrefix() + "addRole";
	}
}

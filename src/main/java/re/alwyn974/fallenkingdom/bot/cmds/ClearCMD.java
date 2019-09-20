package re.alwyn974.fallenkingdom.bot.cmds;

import static re.alwyn974.fallenkingdom.bot.FallenKingdomBOT.i;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import re.alwyn974.fallenkingdom.bot.Command;

public class ClearCMD extends ListenerAdapter implements Command {

	private int getInt(String arg) {
		try {
			return Integer.parseInt(arg);
		} catch (Exception e) {
			return 0;
		}
	}

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

		if (e.getMessage().getContentRaw().startsWith(getPrefix())) {
			String fullCmd = e.getMessage().getContentRaw().substring(1);
			String[] args = fullCmd.split(" ");
			String primaryCmd = args[0];

			if (primaryCmd.equalsIgnoreCase("clear") && args.length == 1) {
				
				i("Commande : %s", getName());
				i("Utilisateur : %s", e.getAuthor().getAsTag() + " | " + e.getAuthor().getId());

				e.getChannel().sendMessage(error().setDescription("Utilisation: `/clear <amount of messages (>2) || all>  pour supprimer un nombre de message !`").build()).complete();

				if (args.length > 1 && e.getGuild().getMember(e.getAuthor()).hasPermission(Permission.MESSAGE_MANAGE)) {	
					try {
						MessageHistory history = new MessageHistory(e.getChannel());
						List<Message> msgs;
						if (args[1].equalsIgnoreCase("all")) {
							try {
								while (true) {
									msgs = history.retrievePast(1).complete();
									msgs.get(0).delete().queue();
								}
							} catch (Exception ex) {
							}
							Message answer = e.getChannel().sendMessage(success().setDescription("Successfully deleted all messages !").build()).complete();

							new Timer().schedule(new TimerTask() {
								@Override
								public void run() {
									answer.delete().queue();
								}
							}, 3000);
							
						} else if (getInt(args[1]) <= 100) {
							e.getMessage().delete().queue();
							msgs = history.retrievePast(getInt(args[1])).complete();
							e.getChannel().deleteMessages(msgs).queue();

							Message answer = e.getChannel().sendMessage(success().setDescription("Successfully deleted " + args[1] + " messages!").build()).complete();

							new Timer().schedule(new TimerTask() {
								@Override
								public void run() {
									answer.delete().queue();
								}
							}, 3000);
						}

						else {
							e.getChannel().sendMessage(error().addField("Error Type", "Message value out of bounds.", false).addField("Description","The entered number if messages can not be more than 100 messages!",false).build()).queue();
						}
					} catch (Exception ex) {
						e.getChannel().sendMessage(error().addField("Error Type", ex.getLocalizedMessage(), false).addField("Message", ex.getMessage(), false).build()).queue();
					}
				}
			}

		}
	}

	@Override
	public String getName() {
		return getPrefix() + "clear";
	}
}

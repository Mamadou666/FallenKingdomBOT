package re.alwyn974.fallenkingdom.bot;

import static re.alwyn974.fallenkingdom.bot.Utils.getPrefix;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import re.alwyn974.fallenkingdom.bot.cmds.ClearCMD;
import re.alwyn974.fallenkingdom.bot.cmds.FkRoleCMD;
import re.alwyn974.fallenkingdom.bot.cmds.HelpCMD;
import re.alwyn974.fallenkingdom.bot.cmds.RemoveRolesTeamCMD;
import re.alwyn974.fallenkingdom.bot.cmds.RoleReactionCMD;
import re.alwyn974.fallenkingdom.bot.cmds.ServerCMD;
import re.alwyn974.fallenkingdom.bot.events.JoinEvent;
import re.alwyn974.logger.BasicLogger;
import re.alwyn974.logger.LoggerFactory;

public class FallenKingdomBOT {

	private static JDA jda;
	private static final String TOKEN = "Eh bah non sheh";
	private static String version = "v1.0.0";
	private static String gameActivity = "FallenKindom BOT | " + version;

	private static final BasicLogger logger = LoggerFactory.getLogger("Fallen Kingdom BOT");

	public static void main(String[] args) throws LoginException {
		jda = new JDABuilder(AccountType.BOT).setToken(TOKEN).build();
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.getPresence().setGame(Game.playing(gameActivity));
		jda.addEventListener(new JoinEvent());
		jda.addEventListener(new ClearCMD());
		jda.addEventListener(new ServerCMD());
		jda.addEventListener(new RoleReactionCMD());
		jda.addEventListener(new FkRoleCMD());
		jda.addEventListener(new RemoveRolesTeamCMD());
		jda.addEventListener(new HelpCMD());

		i("===============BOT-INFOS===============");
		i("Type = %s", jda.getAccountType());
		i("Status = %s", jda.getPresence().getStatus());
		i("Version = %s", getVersion());
		i("Activity = %s", jda.getPresence().getGame());
		i("Prefix = %s", getPrefix());
		
	}

	public static void setActivity(String activity) {
		gameActivity = "FallenKindom BOT | " + activity;
		jda.getPresence().setGame(Game.playing(gameActivity));
	}

	public static String getVersion() {
		return version;
	}

	public static JDA getInstance() {
		return jda;
	}
	
	public static String getActivity() {
		return gameActivity;
	}
	
	public static void i(String msg) {
		logger.info(msg);
	}

	public static void i(String msg, Object... args) {
		logger.info(msg, args);
	}
	
	public static void e(String msg) {
		logger.error(msg);
	}

	public static void e(String msg, Object... args) {
		logger.error(msg, args);
	}

}

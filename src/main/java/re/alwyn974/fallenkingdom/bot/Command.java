package re.alwyn974.fallenkingdom.bot;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;

public interface Command {
	
	public String getName();
	
	public default EmbedBuilder success() {
		return new EmbedBuilder().setColor(Color.green);
	}
	
	public default EmbedBuilder error() {
        return new EmbedBuilder().setColor(Color.red);
    }
	
	public default String getPrefix() {
		return Utils.getPrefix();
	}

	
}

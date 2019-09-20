package re.alwyn974.fallenkingdom.bot;
import static re.alwyn974.fallenkingdom.bot.FallenKingdomBOT.e;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

public class Saver {

	private File file;
	private Properties properties;

	public Saver(File file) {
		this.file = file;
		this.properties = new Properties();

		if (file.exists())
			load();
	}

	public void set(String key, String value) {
		properties.setProperty(key, value);
		save();
	}

	public String get(String key) {
		return properties.getProperty(key);
	}
	
	public String get(String key, String def) {
		String value = properties.getProperty(key);
		return value == null ? def : value;
	}

	public void save() {
		try {
			properties.store(new BufferedWriter(new FileWriter(file)), "Generated Fallen Kingdom BOT");
		} catch (Throwable t) {
			e("Can't save the properties");
		}
	}

	public void load() {
		try {
			properties.load(new FileInputStream(file));
		} catch (Throwable t) {
			e("Can't load the properties");
		}
	}
}

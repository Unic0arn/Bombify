package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
/**
 * A class to handle settings and configuration information.
 * @author Viking Edström & Fredrik Hallberg
 *
 */
@SuppressWarnings("serial")
public class SettingsContainer extends HashMap<String, String> {
	private String configfile;
	/**
	 * Creates a SettingsContainer and parses the information from the given file.
	 * @param file
	 * @throws ParseException if the configuration file is corrupted in some way.
	 */
	public SettingsContainer(String file) throws ParseException{
		configfile = file;

		try {
			BufferedReader in = new BufferedReader(new FileReader(configfile));
			String str;
			int linenr = 0; //Keeping track on what linenumber the reader is on.
			while ((str = in.readLine()) != null) {
				linenr++;
				if(!str.startsWith("#")){ //This is a comment-line and is therefor ignored.
					String[] splitstr = str.split("//|=");
					if(splitstr.length < 2 || splitstr[1].equals("")){ //If the file is in some way corrupted.
						throw new ParseException(splitstr.toString(), linenr);
					}
					put(splitstr[0].trim(), splitstr[1].trim());
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns an array of the settings that are listed in the
	 * config file.
	 * @return a String array of the available settings.
	 */
	public String[] toArray() {
		String[] result = new String[1];
		result = keySet().toArray(result);
		return result;
	}

	public void writeToFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(configfile));
			out.write("");
			String[] keys = new String[1];
			keys = keySet().toArray(keys);
			for (int i = 0; i < keys.length; i++) {
				out.append(keys[i] + "=" + get(keys[i]));
				out.newLine();
				put(keys[i], get(keys[i]));
			}
			out.close();
		} catch (Exception e) {
		}
	}
}

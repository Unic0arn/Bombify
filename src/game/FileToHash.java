package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

@SuppressWarnings("serial")
public class FileToHash extends HashMap<String, String> {
    private String configfile;

    public FileToHash(String file) {
        configfile = file;

        try {
            BufferedReader in = new BufferedReader(new FileReader(configfile));
            String str;
            while ((str = in.readLine()) != null) {
                String[] splitstr = str.split("=");
                put(splitstr[0].trim(), splitstr[1].trim());
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] toArray() {
        // Returns an array of all the keys in this FTH
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

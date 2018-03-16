package com.edu.jet.server.saver;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Kulakva Daria
 */
public class MemorySaver implements Saver {
    private List<String> listMessage = new LinkedList<>();
    private static File file = new File("history.txt");

    @Override
    public synchronized void save(String message) {
        if (!message.equals("")) {
            try (FileWriter fw = new FileWriter(file, true)) {
                fw.write(message);
                fw.write(System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public List<String> getData() {
        listMessage.clear();
        try (BufferedReader fr = new BufferedReader(new FileReader(file))) {
            String line = null;
            listMessage.add("HISTORY:");
            while ((line = fr.readLine()) != null) {
                listMessage.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMessage;
    }
}

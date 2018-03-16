package com.edu.jet.server.saver;

import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 */
public class MemorySaver implements Saver {
    private  List<String> listMessage = new LinkedList <>();
    public MemorySaver(){
    }
    @Override
    public void save(Object message) {
        if (message.equals("")){
            listMessage.add(message.toString());
        }

    }

    public List<String> getData() {
        return listMessage;
    }
}

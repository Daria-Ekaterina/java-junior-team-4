package com.edu.jet.server.saver;

import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 */
public class MemorySever implements Saver {
    private  List<String> listMessage = new LinkedList <>();
    private Object obj;
    public MemorySever(){
        //this.obj=obj;
    }
    @Override
    public void save(Object message) {
        if (message!=null){
            listMessage.add(message.toString());
        }

    }
}

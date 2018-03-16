package com.edu.jet.server.saver;

import java.util.List;

public interface Saver {
    void save(Object message);
    List<String> getData();
}

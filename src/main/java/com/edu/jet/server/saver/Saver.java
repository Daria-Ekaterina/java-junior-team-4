package com.edu.jet.server.saver;

import java.util.List;

public interface Saver {
    void save(String message);
    List<String> getData();
}

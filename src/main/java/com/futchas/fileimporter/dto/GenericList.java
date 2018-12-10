package com.futchas.fileimporter.dto;

import java.util.ArrayList;

public class GenericList<T> extends ArrayList<T> {

    public GenericList() {
        super();
    }

    void printSeparator() {
        System.out.println("---------------------------------------------------------");
    }

}

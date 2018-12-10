package com.futchas.fileimporter.mapper;

import com.futchas.fileimporter.FileEntry;

import java.util.HashMap;
import java.util.function.BiFunction;

/**
 * Every additional mapper should extend it to ensure all abstract methods are implemented including the apply function
 * which is required by the BiFunction
 * @param <T> the class type to be used in the mapper
 * @param <L> a list of these class types
 */
public abstract class AbstractMapper<T, L> implements BiFunction<FileEntry, T, Boolean> {

    public abstract String objectType();

    public abstract L createList();

    public abstract T createInstance();

    public void mapEntry(FileEntry fileEntry, HashMap<String, String> existingEntries, T specificObject) {

        String previousValue = existingEntries.put(fileEntry.getKey(), fileEntry.getValue());
        if(previousValue != null)
            fileEntry.markAsDuplicateField();

        boolean isUnknownField = apply(fileEntry, specificObject);

        if(isUnknownField && fileEntry.getType() == null)
            fileEntry.markAsUnknownField();


    }


}

package com.futchas.fileimporter;


import com.futchas.fileimporter.dto.AbstractObject;
import com.futchas.fileimporter.mapper.AbstractMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


/**
 * Parses the file and generates a specific object, where the mapping is defined in the corresponding Mapper
 * To extend the supported objects, you need to create a simple class object,
 * a list holding these objects and the corresponding Mapper.
 */
class ObjectParser {

    /**
     * The object parser expects a BufferedReader to read the content from a file and a specific Mapper
     * and returns a list of that Object
     *
     * @param reader BufferedReader to read the file data
     * @param mapper A specific mapper to define the keys inside file and map it to the class field
     * @param <T> The generic class type specified in the corresponding mapper
     * @param <L> A list of class type T
     * @return The generic object instance T
     * @throws IOException Reading from the BufferedReader can case this exception
     */
    <T, L> T read(BufferedReader reader, AbstractMapper<T, L> mapper) throws IOException {

        String currentLine = reader.readLine();
        HashMap<String, String> entries = new HashMap<>();

        T specificObject = mapper.createInstance();

        AbstractObject abstractObject;
        if (specificObject instanceof AbstractObject) {
            abstractObject = (AbstractObject) specificObject;
        } else
            throw new ClassCastException("Class " + specificObject.getClass().getName() +
                    " must extends " + AbstractObject.class.getName());

        while (currentLine != null) {
            if (currentLine.equals("end")) {
                break;
            }

            FileEntry fileEntry = parseEntry(currentLine);
            mapper.mapEntry(fileEntry, entries, specificObject);

            if (!fileEntry.isConsistent())
                abstractObject.addInconsistency(fileEntry);

            currentLine = reader.readLine();
        }

        for (String key : abstractObject.getUnsetFields()) {
            abstractObject.addInconsistency(new FileEntry(key, "", InconsistencyType.FIELD_NOT_SET));
        }

        return specificObject;
    }

    private FileEntry parseEntry(String line) {
        FileEntry fileEntry;
        String[] fieldPair = line.trim().split(":");
        InconsistencyType type = null;

        if (line.isEmpty() || fieldPair.length == 0) // length is 0 when line contains only a colon (:)
            return new FileEntry(InconsistencyType.NO_ENTRY);

        String key = fieldPair[0].trim();
        if (fieldPair.length == 1)
            fileEntry = new FileEntry(key, "", InconsistencyType.NO_VALUE);
        else if (fieldPair.length == 2) {
            String value = fieldPair[1].trim();
            if (key.isEmpty())
                type = InconsistencyType.NO_KEY;
            fileEntry = new FileEntry(key, value, type);
        } else {
            String value = fieldPair[1].trim();
            fileEntry = new FileEntry(key, value, InconsistencyType.MULTIPLE_COLONS);
        }
        return fileEntry;
    }


}

package com.futchas.fileimporter;

import com.futchas.fileimporter.dto.GenericList;
import com.futchas.fileimporter.mapper.AbstractMapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Supports the special 'rough-and-ready' file format e.g.
 * CONTRACTORS
 * start
 *   contractorName : Somebody
 *   fullName       : Somebody Something
 *   email          : me@domain.com
 * end
 * start
 * ...
 * end
 */
class FileParser {

    /**
     * Can parsing following file types: contractor, employee, workflow and workflow instance
     * @param filePath The path relative to the resource folder
     * @param mapper maps file data to object fields
     * @return A object containing all the entries as a HashMap
     */
    <T,L extends GenericList<T>>L parseToObject(String filePath, AbstractMapper<T, L> mapper) throws FileFormatException, FileNotFoundException {

        L objectList = mapper.createList();

        ObjectParser objectParser = new ObjectParser();
        URL resource = getClass().getClassLoader().getResource(filePath);
        if(resource == null)
            throw new FileNotFoundException("Following file could not be found: " + filePath);
        Path path = Paths.get(resource.getPath());
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String currentLine = reader.readLine();
            if(currentLine == null)
                throw new FileFormatException("Couldn't read any line from File!");

            if(!currentLine.trim().equals(mapper.objectType())){
                throw new FileFormatException("Cannot parse this file! Please create a new Mapper class to support this file type");
            }

            int startCounter = 0;
            while (currentLine != null){
                if(currentLine.equals("start")) {
                    startCounter++;
                    T object = objectParser.read(reader, mapper);
                    objectList.add(object);
                }
                currentLine = reader.readLine();
            }
            if(startCounter == 0){
                throw new FileFormatException("The file has no start line! Please follow the 'rough-and-ready' file format standard!");
            }

            return objectList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectList;
    }
}

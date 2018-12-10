package com.futchas.fileimporter.dto;

import com.futchas.fileimporter.FileEntry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractObject {

  private List<FileEntry> inconsistentList = new ArrayList<>();

  public List<FileEntry> getInconsistencies() {
    return inconsistentList;
  }

  public void addInconsistency(FileEntry entry) {
    inconsistentList.add(entry);
  }

  // Alternative to reflection would be to manually check each field in each class for null
  public List<String> getUnsetFields() {
    return Arrays.stream(this.getClass().getDeclaredFields()).filter(field -> {
      field.setAccessible(true); //for private fields
      try {
        return field.get(this) == null; // get value of field
      } catch (IllegalAccessException e) {
        e.printStackTrace();
        return true;
      }
    }).map(Field::getName).collect(Collectors.toList());
  }

}

package com.futchas.fileimporter;

public class FileEntry {

    private InconsistencyType type;
    private String key;
    private String value;
    private boolean isConsistent;

    FileEntry(String key, String value, InconsistencyType type) {
        this.key = key;
        this.value = value;
        this.type = type;
        this.isConsistent = type == null;
    }

    FileEntry(InconsistencyType type) {
        this.key = "";
        this.value = "";
        this.type = type;
        this.isConsistent = type == null;
    }

    public InconsistencyType getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    boolean isConsistent() {
        return isConsistent;
    }

    public void markAsUnknownField() {
        type = InconsistencyType.UNKNOWN_KEY;
        isConsistent = false;
    }

    public void markAsDuplicateField() {
        type = InconsistencyType.DUPLICATE;
        isConsistent = false;
    }

    @Override
    public String toString() {
        return System.lineSeparator() + "FileEntry{" +
                "message=" + type.getMessage() +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", isConsistent=" + isConsistent +
                '}';
    }

}

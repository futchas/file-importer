package com.futchas.fileimporter;

enum InconsistencyType {
    NO_ENTRY("No entry found on this line!"),
    NO_KEY("No key found for this value!"),
    NO_VALUE("No value found related to the key!"),
    MULTIPLE_COLONS("Expecting exactly one colon (:) per line!"),
    DUPLICATE("Entry key already existed and will be overwritten!"),
    UNKNOWN_KEY("Following key cannot be matched to object field!"),
    FIELD_NOT_SET("Field was never set and is null!");

    private String message;

    InconsistencyType(String message) {
        this.message = message;
    }

    public String getMessage() {
            return message;
        }
}


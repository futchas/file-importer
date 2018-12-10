package com.futchas.fileimporter.mapper;

import com.futchas.fileimporter.FileEntry;
import com.futchas.fileimporter.dto.Workflows;
import com.futchas.fileimporter.dto.Workflow;

public class WorkflowMapper extends AbstractMapper<Workflow, Workflows> {

    @Override
    public String objectType() {
        return "WORKFLOWS";
    }

    @Override
    public Workflows createList() {
        return new Workflows();
    }

    @Override
    public Workflow createInstance() {
        return new Workflow();
    }

    @Override
    public Boolean apply(FileEntry entry, Workflow workflow) {
        String key = entry.getKey();
        String value = entry.getValue();
        switch (key) {
            case "id":
                workflow.setId(Integer.valueOf(value));
                break;
            case "name":
                workflow.setName(value);
                break;
            case "author":
                workflow.setAuthor(value);
                break;
            case "version":
                workflow.setVersion(Integer.valueOf(value));
                break;
            default:
                return true;
        }
        return false;
    }
}

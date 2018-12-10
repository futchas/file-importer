package com.futchas.fileimporter.mapper;

import com.futchas.fileimporter.WorkflowStatus;
import com.futchas.fileimporter.dto.WorkflowInstance;
import com.futchas.fileimporter.FileEntry;
import com.futchas.fileimporter.dto.WorkflowInstances;

public class WorkflowInstanceMapper extends AbstractMapper<WorkflowInstance, WorkflowInstances> {

    @Override
    public String objectType() {
        return "WORKFLOW INSTANCES";
    }

    @Override
    public WorkflowInstances createList() {
        return new WorkflowInstances();
    }

    @Override
    public WorkflowInstance createInstance() {
        return new WorkflowInstance();
    }

    @Override
    public Boolean apply(FileEntry entry, WorkflowInstance instance) {
        String key = entry.getKey();
        String value = entry.getValue();
        switch (key) {
            case "id":
                instance.setId(Long.valueOf(value));
                break;
            case "workflowId":
                instance.setWorkflowId(Integer.valueOf(value));
                break;
            case "assignee":
                instance.setAssignee(value);
                break;
            case "step":
                instance.setStep(value);
                break;
            case "status":
                instance.setStatus(WorkflowStatus.valueOf(value));
                break;
            default:
                return true;
        }
        return false;
    }
}

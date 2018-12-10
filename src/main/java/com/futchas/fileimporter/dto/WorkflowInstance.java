package com.futchas.fileimporter.dto;

import com.futchas.fileimporter.WorkflowStatus;

public class WorkflowInstance extends AbstractObject {
    private long id;
    private int workflowId;
    private String assignee;
    private String step;
    private WorkflowStatus status;

    public WorkflowInstance() {
        status = WorkflowStatus.UNKNOWN;
    }

    public int getWorkflowId() {
        return workflowId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public WorkflowStatus getStatus() {
        return status;
    }

    public void setStatus(WorkflowStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WorkflowInstance{" +
                "id=" + id +
                ", workflowId=" + workflowId +
                ", assignee='" + assignee + '\'' +
                ", step='" + step + '\'' +
                ", status=" + status +
                '}';
    }
}

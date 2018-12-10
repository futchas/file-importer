package com.futchas.fileimporter.dto;

import java.util.function.BiPredicate;

public class Workflows extends GenericList<Workflow> {

    public void showWorkflowsByFilter(WorkflowInstances workflowInstances, BiPredicate<Workflow, WorkflowInstance> predicate) {
        this.forEach(workflow ->
                workflowInstances.showWorkflowInstancesByFilter(instance -> predicate.test(workflow, instance)));
    }

    public void showWorkflowsByFilterAndCount(WorkflowInstances workflowInstances, BiPredicate<Workflow, WorkflowInstance> predicate) {
        this.forEach(workflow ->
                workflowInstances.showWorkflowInstancesByFilterAndCount(instance -> predicate.test(workflow, instance)));
    }

}

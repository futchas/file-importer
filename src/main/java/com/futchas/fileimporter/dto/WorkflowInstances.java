package com.futchas.fileimporter.dto;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class WorkflowInstances extends GenericList<WorkflowInstance> {

    public void showWorkflowInstancesByFilter(Predicate<WorkflowInstance> predicate) {
        this.stream().filter(predicate).forEach(System.out::println);
        printSeparator();
    }

    public void showWorkflowInstancesByFilterAndCount(Predicate<WorkflowInstance> predicate) {
        AtomicInteger count = new AtomicInteger();
        this.stream()
                .filter(predicate)
                .forEach(o -> {
                    count.getAndIncrement();
                    System.out.println(o);
                });
        System.out.println("Amount of workflows: " + count);
        printSeparator();
    }
}

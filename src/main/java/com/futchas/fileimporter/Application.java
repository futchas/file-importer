package com.futchas.fileimporter;

import com.futchas.fileimporter.dto.*;
import com.futchas.fileimporter.mapper.ContractorMapper;
import com.futchas.fileimporter.mapper.EmployeeMapper;
import com.futchas.fileimporter.mapper.WorkflowInstanceMapper;
import com.futchas.fileimporter.mapper.WorkflowMapper;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {

        System.out.println();
        LOGGER.info("Reading the data files programmatically.");
        try {
            FileParser fileParser = new FileParser();
            Contractors contractors = fileParser.parseToObject("contractors.data", new ContractorMapper());
            Employees employees = fileParser.parseToObject("employees.data", new EmployeeMapper());
            Workflows workflows = fileParser.parseToObject("workflows.data", new WorkflowMapper());
            WorkflowInstances workflowInstances = fileParser.parseToObject("workflowInstances.data", new WorkflowInstanceMapper());

            System.out.println();
            LOGGER.info("Showing any inconsistent entries.");
            GenericList<AbstractObject> allFileData = Stream.of(contractors, employees, workflowInstances)
                    .flatMap(Collection::stream)
                    .collect(GenericList::new, ArrayList::add, ArrayList::addAll);

            allFileData.stream().map(AbstractObject::getInconsistencies)
                    .filter(inconsistent -> !inconsistent.isEmpty()).forEach(System.out::println);

            System.out.println();
            LOGGER.info("Showing all workflows with their according instances.");

            workflows.showWorkflowsByFilter(workflowInstances,
                    (workflow, instance) -> workflow.getId() == instance.getWorkflowId());

            System.out.println();
            LOGGER.info("Showing all workflows having running instances and the number of those instances.");

            workflows.showWorkflowsByFilterAndCount(workflowInstances, (workflow, instance) ->
                    workflow.getId() == instance.getWorkflowId() && instance.getStatus().equals(WorkflowStatus.RUNNING));

            System.out.println();
            LOGGER.info("Showing all contractors that are assignees to running instances.");
            contractors.forEach(contractor -> {
                System.out.println(contractor);
                workflowInstances.showWorkflowInstancesByFilter(instance ->
                        instance.getAssignee().equals(contractor.getEmail()) &&
                                instance.getStatus().equals(WorkflowStatus.RUNNING));
            });

        } catch (FileFormatException | FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}

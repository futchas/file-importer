package com.futchas.fileimporter;

import com.futchas.fileimporter.dto.WorkflowInstance;
import com.futchas.fileimporter.mapper.ContractorMapper;
import com.futchas.fileimporter.mapper.WorkflowInstanceMapper;
import com.futchas.fileimporter.mapper.WorkflowMapper;
import com.futchas.fileimporter.dto.Contractors;
import com.futchas.fileimporter.dto.WorkflowInstances;
import com.futchas.fileimporter.mapper.EmployeeMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileParserTest {

    private static FileParser fileParser;

    @BeforeAll
    static void setUp() {
        fileParser = new FileParser();
    }

    @Test
    void parseFile_returnFileObjects() throws FileFormatException, FileNotFoundException {


        Contractors contractors = fileParser.parseToObject("contractors.data", new ContractorMapper());

        assertThat(contractors).isNotEmpty();
        assertThat(contractors).hasSize(3);
        assertThat(contractors.stream()).hasSize(3);
    }
    
    @Test
    void parseFile_returnFileObjectsWithInconsistency() throws FileFormatException, FileNotFoundException {
        
        WorkflowInstances workflowInstances =
                fileParser.parseToObject("workflowInstances.data", new WorkflowInstanceMapper());
        
        assertThat(workflowInstances).isNotEmpty();
        assertThat(workflowInstances.stream()).isNotEmpty();
        assertThat(workflowInstances.stream().map(WorkflowInstance::getInconsistencies).filter(i -> !i.isEmpty())).hasSize(2);
    }

    @Test
    void parseFileWithoutStart_throwException() {

        assertThrows(FileFormatException.class, () ->
                fileParser.parseToObject("noStart.data", new EmployeeMapper()));
    }

    @Test
    void parseFileWithOtherContent_throwException() {

        assertThrows(FileFormatException.class, () ->
                fileParser.parseToObject("otherObject.data", new WorkflowMapper()));
    }

    @Test
    void parseEmptyFile_throwException() {

        assertThrows(FileFormatException.class, () ->
                fileParser.parseToObject("empty.data", new ContractorMapper()));
    }

    @Test
    void noFile_throwException() {

        assertThrows(FileNotFoundException.class, () ->
            fileParser.parseToObject("noFile", new EmployeeMapper()));
    }
}
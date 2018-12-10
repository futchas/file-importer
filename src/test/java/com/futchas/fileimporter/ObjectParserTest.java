package com.futchas.fileimporter;

import com.futchas.fileimporter.mapper.ContractorMapper;
import com.futchas.fileimporter.dto.Contractor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectParserTest {

    private BufferedReader reader = Mockito.mock(BufferedReader.class);

    @Test
    void readValidEntries() throws IOException {
        Mockito.when(reader.readLine())
                .thenReturn(" contractorName  :  somebody ")
                .thenReturn(" fullName  :  somebody something ")
                .thenReturn(" email  :  email@my.com ")
                .thenReturn( "end");

        Contractor contractor = new ObjectParser().read(reader, new ContractorMapper());

        assertThat(contractor).isNotNull();
        assertThat(contractor.getContractorName()).isEqualTo("somebody");
        assertThat(contractor.getFullName()).isEqualTo("somebody something");
        assertThat(contractor.getEmail()).isEqualTo("email@my.com");
        assertThat(contractor.getInconsistencies()).isEmpty();
    }

    @Test
    void readMissingEnd_returnNoEntries() throws IOException {
        Mockito.when(reader.readLine()).thenReturn(" contractorName  :  somebody ").thenReturn( null);

        Contractor contractor = new ObjectParser().read(reader, new ContractorMapper());

        assertThat(contractor.getContractorName()).isEqualTo("somebody");
        assertThat(contractor.getFullName()).isNull();
        assertThat(contractor.getEmail()).isNull();
    }

    @Test
    void objectContainsFieldUnsetInconsistency() throws IOException {
        Mockito.when(reader.readLine()).thenReturn(" contractorName : somebody ")
                .thenReturn(" email  :  email@my.com ")
                .thenReturn( "end");

        Contractor contractor = new ObjectParser().read(reader, new ContractorMapper());

        assertThat(contractor.getInconsistencies()).isNotEmpty();
        InconsistencyType inconsistencyType = contractor.getInconsistencies().get(0).getType();
        assertThat(inconsistencyType).isEqualTo(InconsistencyType.FIELD_NOT_SET);
    }

    @Test
    void objectContainsUnknownKeyInconsistency() throws IOException {
        Mockito.when(reader.readLine()).thenReturn(" unknownKey : somebody ")
                .thenReturn(" email  :  email@my.com ")
                .thenReturn( "end");

        Contractor contractor = new ObjectParser().read(reader, new ContractorMapper());

        InconsistencyType inconsistencyType = contractor.getInconsistencies().get(0).getType();
        assertThat(inconsistencyType).isEqualTo(InconsistencyType.UNKNOWN_KEY);
    }

    @Test
    void objectContainsDuplicateKeyInconsistency() throws IOException {
        Mockito.when(reader.readLine()).thenReturn(" contractorName : somebody ")
                .thenReturn(" contractorName : somebody ")
                .thenReturn( "end");

        Contractor contractor = new ObjectParser().read(reader, new ContractorMapper());

        assertThat(contractor.getInconsistencies()).isNotEmpty();
        InconsistencyType inconsistencyType = contractor.getInconsistencies().get(0).getType();
        assertThat(inconsistencyType).isEqualTo(InconsistencyType.DUPLICATE);
    }

    @Test
    void objectContainsMultipleColonsInconsistency() throws IOException {
        Mockito.when(reader.readLine()).thenReturn(" contractorName : : somebody ")
                .thenReturn( "end");

        Contractor contractor = new ObjectParser().read(reader, new ContractorMapper());

        assertThat(contractor.getInconsistencies()).isNotEmpty();
        InconsistencyType inconsistencyType = contractor.getInconsistencies().get(0).getType();
        assertThat(inconsistencyType).isEqualTo(InconsistencyType.MULTIPLE_COLONS);
    }

    @Test
    void objectContainsNoEntryInconsistency() throws IOException {
        Mockito.when(reader.readLine()).thenReturn("")
                .thenReturn( "end");

        Contractor contractor = new ObjectParser().read(reader, new ContractorMapper());

        assertThat(contractor.getInconsistencies()).isNotEmpty();
        InconsistencyType inconsistencyType = contractor.getInconsistencies().get(0).getType();
        assertThat(inconsistencyType).isEqualTo(InconsistencyType.NO_ENTRY);
    }

    @Test
    void objectContainsNoKeyInconsistency() throws IOException {
        Mockito.when(reader.readLine()).thenReturn(": asasas")
                .thenReturn( "end");

        Contractor contractor = new ObjectParser().read(reader, new ContractorMapper());

        assertThat(contractor.getInconsistencies()).isNotEmpty();
        InconsistencyType inconsistencyType = contractor.getInconsistencies().get(0).getType();
        assertThat(inconsistencyType).isEqualTo(InconsistencyType.NO_KEY);
    }

    @Test
    void objectContainsNoValueInconsistency() throws IOException {
        Mockito.when(reader.readLine()).thenReturn("contractorName :")
                .thenReturn( "end");

        Contractor contractor = new ObjectParser().read(reader, new ContractorMapper());

        assertThat(contractor.getInconsistencies()).isNotEmpty();
        InconsistencyType inconsistencyType = contractor.getInconsistencies().get(0).getType();
        assertThat(inconsistencyType).isEqualTo(InconsistencyType.NO_VALUE);
    }
}
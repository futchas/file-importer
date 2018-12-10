package com.futchas.fileimporter.mapper;

import com.futchas.fileimporter.FileEntry;
import com.futchas.fileimporter.dto.Contractor;
import com.futchas.fileimporter.dto.Contractors;

public class ContractorMapper extends AbstractMapper<Contractor, Contractors> {

    @Override
    public Contractors createList() {
        return new Contractors();
    }

    @Override
    public Contractor createInstance() {
        return new Contractor();
    }

    @Override
    public String objectType() {
        return "CONTRACTORS";
    }

    @Override
    public Boolean apply(FileEntry entry, Contractor contractor) {
        String key = entry.getKey();
        String value = entry.getValue();
        switch (key) {
            case "contractorName":
                contractor.setContractorName(value);
                break;
            case "fullName":
                contractor.setFullName(value);
                break;
            case "email":
                contractor.setEmail(value);
                break;
            default:
                return true;
        }
        return false;
    }


}
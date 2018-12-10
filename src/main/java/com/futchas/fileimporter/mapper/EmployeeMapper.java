package com.futchas.fileimporter.mapper;

import com.futchas.fileimporter.FileEntry;
import com.futchas.fileimporter.dto.Employees;
import com.futchas.fileimporter.dto.Employee;

public class EmployeeMapper extends AbstractMapper<Employee, Employees> {

    @Override
    public String objectType() {
        return "EMPLOYEES";
    }

    @Override
    public Employees createList() {
        return new Employees();
    }

    @Override
    public Employee createInstance() {
        return new Employee();
    }

    @Override
    public Boolean apply(FileEntry entry, Employee employee) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "employeeId":
                    employee.setEmployeeId(value);
                    break;
                case "fullName":
                    employee.setFullName(value);
                    break;
                case "email":
                    employee.setEmail(value);
                    break;
                default:
                    return true;
            }
        return false;
    }

}

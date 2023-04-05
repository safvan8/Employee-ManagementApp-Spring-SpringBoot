package in.ineuron.service;

import java.util.List;

import in.ineuron.dto.EmployeeDTO;
import in.ineuron.util.JdbcUtil.DatabaseOperationsStaus;

public interface IEmployeeService {
	DatabaseOperationsStaus save(EmployeeDTO dto);

	List<EmployeeDTO> findAllEmployees();

	EmployeeDTO findById(Integer eid);

	DatabaseOperationsStaus update(EmployeeDTO dto);

	DatabaseOperationsStaus deleteById(Integer eid);
}

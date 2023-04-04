package in.ineuron.dao;

import java.util.List;

import in.ineuron.bo.EmployeeBO;
import in.ineuron.util.JdbcUtil.DatabaseOperationsStaus;

public interface IEmployeeDao {
	
	DatabaseOperationsStaus save(EmployeeBO bo);

	List<EmployeeBO> findAllEmployees();

	EmployeeBO findById(Integer eid);

	DatabaseOperationsStaus update(EmployeeBO bo);

	DatabaseOperationsStaus deleteById(Integer eid);

}

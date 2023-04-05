package in.ineuron.controller;

import java.util.List;

import in.ineuron.util.JdbcUtil.DatabaseOperationsStaus;
import in.ineuron.vo.EmployeeVO;

public interface IEmployeeController {

	DatabaseOperationsStaus save(EmployeeVO vo);

	List<EmployeeVO> findAllEmployees();

	EmployeeVO findById(Integer eid);

	DatabaseOperationsStaus update(EmployeeVO vo);

	DatabaseOperationsStaus deleteById(Integer eid);

}

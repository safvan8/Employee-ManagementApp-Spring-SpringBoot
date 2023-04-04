package in.ineuron.controller;

import java.util.ArrayList;
import java.util.List;

import in.ineuron.dto.EmployeeDTO;
import in.ineuron.service.IEmployeeService;
import in.ineuron.util.JdbcUtil.DatabaseOperationsStaus;
import in.ineuron.vo.EmployeeVO;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeeControllerImpl.
 */
public class EmployeeControllerImpl implements IEmployeeController
{

	// performing constructo injection
	private IEmployeeService service;

	static
	{
		System.out.println("EmployeeControllerImpl.class file is loading.......");
	}

	public EmployeeControllerImpl()
	{
		System.out.println("EmployeeControllerImpl :: zero arg constrcutor.......");
	}

	public EmployeeControllerImpl(IEmployeeService service)
	{
		System.out.println("EmployeeControllerImpl:: one arg constructor...........");
		System.out.println("Injecting service object to Controller.................\n");
		this.service = service;
	}

	/**
	 * Save user entered employe details to db
	 *
	 * @param EmployeeVo the vo
	 * @return enum contains status of insert operations staus
	 */
	@Override
	public DatabaseOperationsStaus save(EmployeeVO vo)
	{
		// Converting VO object DTO and passing to service layer
		EmployeeDTO dto = new EmployeeDTO();
		dto.setEaddress(vo.getEaddress());
		dto.setEage(Integer.parseInt(vo.getEage()));
		dto.setEname(vo.getEname());
		return service.save(dto);
	}

	/**
	 * fetch all existing employee records from DB.
	 *
	 * @return the list
	 */
	@Override
	public List<EmployeeVO> findAllEmployees()
	{
		List<EmployeeDTO> employeeDTO = service.findAllEmployees();
		List<EmployeeVO> employeeVo = new ArrayList<EmployeeVO>();
		for (EmployeeDTO dto : employeeDTO)
		{

			EmployeeVO vo = new EmployeeVO();
			vo.setEid(String.valueOf(dto.getEid()));
			vo.setEname(dto.getEname());
			vo.setEage(String.valueOf(dto.getEage()));
			vo.setEaddress(dto.getEaddress());

			employeeVo.add(vo);
		}

		return employeeVo;
	}

	/**
	 * Find by id.
	 *
	 * @param eid -- Employee id
	 * @return vo object if the employee details exists in DB
	 */
	@Override
	public EmployeeVO findById(Integer eid)
	{
		EmployeeDTO dto = service.findById(eid);
		EmployeeVO vo = new EmployeeVO();
		vo.setEid(String.valueOf(dto.getEid()));
		vo.setEname(dto.getEname());
		vo.setEage(String.valueOf(dto.getEage()));
		vo.setEaddress(dto.getEaddress());

		return vo;
	}

	/**
	 * UpdateEmployee details using by Employee Id.
	 *
	 * @param new Emplooyee vo object for updating
	 * @return the enum which contains update operations staus
	 */
	@Override
	public DatabaseOperationsStaus update(EmployeeVO vo)
	{
		// Converting VO object DTO and passing to service layer
		EmployeeDTO dto = new EmployeeDTO();
		dto.setEaddress(vo.getEaddress());
		dto.setEage(Integer.parseInt(vo.getEage()));
		dto.setEname(vo.getEname());
		dto.setEid(Integer.parseInt(vo.getEid()));
		return service.update(dto);
	}

	/**
	 * Delete by id.
	 *
	 * @param eid the Employee ID
	 * @return the enum which contains delete operations staus
	 */
	@Override
	public DatabaseOperationsStaus deleteById(Integer eid)
	{
		return service.deleteById(eid);
	}

	@Override
	public String toString()
	{
		return "EmployeeControllerImpl [service=" + service + "]";
	}

}

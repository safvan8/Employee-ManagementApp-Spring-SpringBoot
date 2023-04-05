package in.ineuron.service;

import java.util.ArrayList;
import java.util.List;

import in.ineuron.bo.EmployeeBO;
import in.ineuron.dao.IEmployeeDao;
import in.ineuron.dto.EmployeeDTO;
import in.ineuron.util.JdbcUtil.DatabaseOperationsStaus;

/**
 * The Class EmployeeServiceImpl.
 */
public class EmployeeServiceImpl implements IEmployeeService
{

	// injecting Dao to service uisng constcrutor injection
	private IEmployeeDao dao;

	static
	{
		System.out.println("EmployeeServiceImpl.class file is loading.......");
	}

	public EmployeeServiceImpl()
	{
		System.out.println("EmployeeServiceImpl :: zero arg constrcutor.......");
	}

	/**
	 * @param dao the dao
	 */
	public EmployeeServiceImpl(IEmployeeDao dao)
	{
		System.out.println("EmployeeServiceImpl:: one arg constructor...........");
		System.out.println("Injecting dao object to service layer.................\n");
		this.dao = dao;
	}

	/**
	 * Save user entered employe details to db
	 *
	 * @param EmployeeDTO the dto
	 * @return enum contains status of insert operations staus
	 */
	@Override
	public DatabaseOperationsStaus save(EmployeeDTO dto)
	{
		// converting DTO to BO
		// and passing to DAO layer
		EmployeeBO bo = new EmployeeBO();
		bo.setEname(dto.getEname());
		bo.setEage(dto.getEage());
		bo.setEaddress(dto.getEaddress());

		return dao.save(bo);
	}

	/**
	 * fetch all existing employee records from DB.
	 *
	 * @return the list
	 */
	@Override
	public List<EmployeeDTO> findAllEmployees()
	{
		List<EmployeeBO> employeesBO = dao.findAllEmployees();
		List<EmployeeDTO> employeeDTO = new ArrayList<EmployeeDTO>();
		for (EmployeeBO bo : employeesBO)
		{

			EmployeeDTO dto = new EmployeeDTO();
			dto.setEid(bo.getEid());
			dto.setEname(bo.getEname());
			dto.setEage(bo.getEage());
			dto.setEaddress(bo.getEaddress());

			employeeDTO.add(dto);
		}
		return employeeDTO;
	}

	/**
	 * Find by id.
	 *
	 * @param eid -- Employee id
	 * @return dto object if the employee details exists in DB ,else retruns null
	 */
	@Override
	public EmployeeDTO findById(Integer eid)
	{
		EmployeeBO bo = dao.findById(eid);
		// converting bo to dto
		EmployeeDTO dto = new EmployeeDTO();
		dto.setEid(bo.getEid());
		dto.setEname(bo.getEname());
		dto.setEage(bo.getEage());
		dto.setEaddress(bo.getEaddress());

		return dto;
	}

	/**
	 * UpdateEmployee details using by Employee Id.
	 *
	 * @param eid the Employee ID
	 * @return the enum which contains update operations staus
	 */
	@Override
	public DatabaseOperationsStaus update(EmployeeDTO dto)
	{
		// converting DTO to BO
		// and passing to DAO layer
		EmployeeBO bo = new EmployeeBO();
		bo.setEname(dto.getEname());
		bo.setEage(dto.getEage());
		bo.setEaddress(dto.getEaddress());
		bo.setEid(dto.getEid());
		return dao.update(bo);
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
		// TODO Auto-generated method stub
		return dao.deleteById(eid);
	}

	@Override
	public String toString()
	{
		return "EmployeeServiceImpl [dao=" + dao + "]";
	}

}

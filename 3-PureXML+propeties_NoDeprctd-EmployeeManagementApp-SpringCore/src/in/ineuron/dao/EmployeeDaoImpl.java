package in.ineuron.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import in.ineuron.bo.EmployeeBO;
import in.ineuron.util.JdbcUtil.DatabaseOperationsStaus;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeeDaoImpl.
 */
public class EmployeeDaoImpl implements IEmployeeDao
{

	/** The Constant SQL_INSERT_QUERY. */
	private static final String SQL_INSERT_QUERY = "insert into employee(`ename`,`eage`,`eaddress`) values(?,?,?)";

	/** The Constant SQL_SELECT_QUERY. */
	private static final String SQL_SELECT_QUERY = "select eid,ename,eage,eaddress from employee";

	/** The Constant FIND_BY_ID_SQL_QUERY. */
	private static final String FIND_BY_ID_SQL_QUERY = "select eid,ename,eage,eaddress from employee where eid = ?";

	/** The Constant SQL_UPDATE_QUERY. */
	private static final String SQL_UPDATE_QUERY = "update employee set ename=?,eaddress=?,eage=? where eid = ?";

	/** The Constant DELETE_SQL_QUERY. */
	private static final String DELETE_SQL_QUERY = "delete from employee where eid=?";

	// inject ing DataSource object from Spring Framework throgh constrcutor
	private DataSource dataSource;

	static
	{
		System.out.println("EmployeeDaoImpl.class file is loading.......");
	}

	public EmployeeDaoImpl()
	{
		System.out.println("EmployeeDaoImpl :: zero arg constrcutor.......");
	}

	/**
	 * @param dataSource the data source
	 */
	public EmployeeDaoImpl(DataSource dataSource)
	{
		System.out.println("EmployeeDaoImpl:: one arg constructor...........");
		System.out.println("InjectingDataSource object from Spring Framework throgh constrcutor.................\n");
		this.dataSource = dataSource;
	}

	/**
	 * to Save user entered employe details to db.
	 *
	 * @param bo the bo
	 * @return enum contains status of insert operations staus
	 */
	@Override
	public DatabaseOperationsStaus save(EmployeeBO bo)
	{

		int rowcount = 0;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SQL_INSERT_QUERY))
		{

			pstmt.setString(1, bo.getEname());
			pstmt.setInt(2, bo.getEage());
			pstmt.setString(3, bo.getEaddress());
			rowcount = pstmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return rowcount == 1 ? DatabaseOperationsStaus.SUCCESS : DatabaseOperationsStaus.FAILED;
	}

	/**
	 * fetch all existing employee records from DB.
	 *
	 * @return the list
	 */
	@Override
	public List<EmployeeBO> findAllEmployees()
	{

		ArrayList<EmployeeBO> empListBO = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SQL_SELECT_QUERY);
				ResultSet resultSet = pstmt.executeQuery())
		{
			empListBO = new ArrayList<EmployeeBO>();
			while (resultSet.next())
			{

				EmployeeBO employeeBO = new EmployeeBO();
				employeeBO.setEid(resultSet.getInt(1));
				employeeBO.setEname(resultSet.getString(2));
				employeeBO.setEage(resultSet.getInt(3));
				employeeBO.setEaddress(resultSet.getString(4));

				empListBO.add(employeeBO);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return empListBO;
	}

	/**
	 * Find by id.
	 *
	 * @param eid -- Employee id
	 * @return bo object if the employee details exists in DB
	 */
	@Override
	public EmployeeBO findById(Integer eid)
	{
		EmployeeBO bo = new EmployeeBO();

		try (Connection connection = dataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(FIND_BY_ID_SQL_QUERY))
		{
			pstmt.setInt(1, eid);

			// executng query
			ResultSet resultSet = pstmt.executeQuery();

			if (resultSet.next())
			{

				bo.setEid(resultSet.getInt(1));
				bo.setEname(resultSet.getString(2));
				bo.setEage(resultSet.getInt(3));
				bo.setEaddress(resultSet.getString(4));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return bo;
	}

	/**
	 * UpdateEmployee details using by Employee Id.
	 *
	 * @param eid the Employee ID
	 * @return the enum which contains update operations staus
	 */
	@Override
	public DatabaseOperationsStaus update(EmployeeBO bo)
	{
		int rowcount = 0;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_QUERY))
		{
			pstmt.setString(1, bo.getEname());
			pstmt.setString(2, bo.getEaddress());
			pstmt.setInt(3, bo.getEage());
			pstmt.setInt(4, bo.getEid());

			rowcount = pstmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return rowcount == 1 ? DatabaseOperationsStaus.SUCCESS : DatabaseOperationsStaus.FAILED;
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
		int deletedRowsCount = 0;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_SQL_QUERY))
		{
			pstmt.setInt(1, eid);
			deletedRowsCount = pstmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return deletedRowsCount == 1 ? DatabaseOperationsStaus.SUCCESS : DatabaseOperationsStaus.FAILED;
	}

	@Override
	public String toString()
	{
		return "EmployeeDaoImpl [dataSource=" + dataSource + "]";
	}

}

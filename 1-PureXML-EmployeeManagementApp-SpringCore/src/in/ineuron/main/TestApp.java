package in.ineuron.main;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import in.ineuron.controller.IEmployeeController;
import in.ineuron.util.JdbcUtil.DatabaseOperationsStaus;
import in.ineuron.vo.EmployeeVO;

/**
 * The Class TestApp.
 */
public class TestApp
{

	/** The bean factory. */
	private static DefaultListableBeanFactory beanFactory;
	static
	{

		try
		{
			/**
			 * to start performing dependency injection during class laoding itself eager
			 * loading is not avaialble for singleton objects in case if
			 * DefaultListableBeanFactory
			 */
			beanFactory = new DefaultListableBeanFactory();
			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
			int numberOFBeanDefinitionsLoaded = reader.loadBeanDefinitions("/in/ineuron/cfg/applicationContext.xml");

			System.out.println("beanFactory conatiner successfully creatde during TestApp.class file loading....");
			System.out.println("No. of bean definitoions loaded :" + numberOFBeanDefinitionsLoaded);

		} catch (Exception e)
		{
			throw new ExceptionInInitializerError(
					"Exception occured During DefaultListableBeanFactory object creatoon..");
		}

	}

	/**
	 * The main method- entry point of Application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{

		// getting object of controller bean from Container
		IEmployeeController controller = beanFactory.getBean("controller", IEmployeeController.class);

		int choice = 0;

		Scanner scanner = new Scanner(System.in);
		try
		{
			while (true)
			{

				System.out.println("\n\n-----------All avaialble services---------------\n");

				System.out.println("1. CREATE");
				System.out.println("2. READ");
				System.out.println("3. READ ALL RECORDS");
				System.out.println("4. UPDATE RECORD");
				System.out.println("5. DELETE RECORD");
				System.out.println("6. EXIT");
				System.out.print("Enter ur choice:: ");
				choice = scanner.nextInt();
				switch (choice)
				{
				case 1:
					String ename = null, eage = null, eaddress = null;

					System.out.print("Enter the EmployeeName :: ");
					ename = scanner.next();

					System.out.print("Enter the EmployeeAge :: ");
					eage = scanner.next();

					System.out.print("Enter the EmployeeAddress :: ");
					eaddress = scanner.next();

					EmployeeVO employeeForInsert = new EmployeeVO();
					employeeForInsert.setEname(ename);
					employeeForInsert.setEaddress(eaddress);
					employeeForInsert.setEage(eage);

					// for saving user entered Employee details to Database
					DatabaseOperationsStaus insertOpStatus = controller.save(employeeForInsert);

					DatabaseOperationsStaus insertSuccess = DatabaseOperationsStaus.SUCCESS;
					DatabaseOperationsStaus insertFailed = DatabaseOperationsStaus.FAILED;

					if (insertSuccess.equals(insertOpStatus))
						System.out.println("Employee record inserted succefully..");
					else if (insertFailed.equals(insertOpStatus))
						System.out.println("Employee record insertion failed");
					else
						System.out.println("Something went wrong while inserting record....");
					break;

				case 2:

					System.out.print("Enter the Employee id :: ");
					int eid = scanner.nextInt();
					EmployeeVO existingEmployee = controller.findById(eid);

					if (existingEmployee != null)
					{
						System.out.println("record found for the id: " + existingEmployee.getEid());
						System.out.println(existingEmployee);
					} else
					{
						System.out.println("Record Not availble........");
					}

					break;

				case 3:
					List<EmployeeVO> employees = null;
					employees = controller.findAllEmployees();
					employees.forEach(System.out::println);
					break;
				case 4:
					System.out.print("Enter the Employee id :: ");
					int eidForUpdate = scanner.nextInt();

					EmployeeVO existingEmployeeForUpdate = controller.findById(eidForUpdate);

					if (existingEmployeeForUpdate != null)
					{

						System.out.println("Enter new  Employee name [ old name: "
								+ existingEmployeeForUpdate.getEname() + " ]-->");
						String newEmpName = scanner.next();

						System.out.println("Enter new  Employee address [ old address: "
								+ existingEmployeeForUpdate.getEaddress() + " ]-->");
						String newEmpAddress = scanner.next();

						System.out.println(
								"Enter new  Employee age [ old name: " + existingEmployeeForUpdate.getEage() + " ]-->");
						String newEmpId = scanner.next();

						// updating new details
						if (newEmpName.trim() != "")
							existingEmployeeForUpdate.setEname(newEmpName);

						if (newEmpAddress.trim() != "")
							existingEmployeeForUpdate.setEaddress(newEmpAddress);

						if (newEmpId.trim() != "")
							existingEmployeeForUpdate.setEname(newEmpId);

						// performing update
						DatabaseOperationsStaus updateOpStatus = controller.update(existingEmployeeForUpdate);

						DatabaseOperationsStaus updateSuccess = DatabaseOperationsStaus.SUCCESS;
						DatabaseOperationsStaus updateFailed = DatabaseOperationsStaus.FAILED;

						if (updateSuccess.equals(updateOpStatus))
							System.out.println("Employee record updated successfully");

						else if (updateFailed.equals(updateOpStatus))
							System.out.println("Employee record updated Failed....");
						else
							System.out.println("Smothing went wrong while updating.");
					} else
					{
						System.out.println("Record not vailable for Updating, try another id");
					}

					break;
				case 5:

					System.out.print("Enter the Employee id for deletion :: ");
					int eidForDelete = scanner.nextInt();

					DatabaseOperationsStaus deleteOpStatus = controller.deleteById(eidForDelete);
					
					DatabaseOperationsStaus deleteSuccess = DatabaseOperationsStaus.SUCCESS;
					DatabaseOperationsStaus deleteFailed = DatabaseOperationsStaus.FAILED;

					if (deleteSuccess.equals(deleteOpStatus))
						System.out.println("Employee record deleted successfully");

					else if (deleteFailed.equals(deleteOpStatus))
						System.out.println("Employee record deletion Failed....");
					else
						System.out.println("Smothing went wrong while deleting...");

					break;
				case 6:
					break;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				((Closeable) beanFactory).close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			scanner.close();
		}
	}
}

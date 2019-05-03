package sluque.callcenter;

import java.util.List;

/**
 * Call Center Rules model
 * @author silvina.luque
 *
 */
public class CallCenterRules {
	
	//list of employees
	private List<Employee> employees;
	
	//the way to select employees to take calls
	private EmployeeCallSelectorStrategy employeeCallSelector;

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public EmployeeCallSelectorStrategy getEmployeeSelector() {
		return employeeCallSelector;
	}

	public void setEmployeeSelector(EmployeeCallSelectorStrategy employeeSelector) {
		this.employeeCallSelector = employeeSelector;
	}
	

	

}

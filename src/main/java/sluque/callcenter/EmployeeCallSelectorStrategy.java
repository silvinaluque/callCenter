package sluque.callcenter;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * EmployeeCallSelectorStrategy model
 * @author silvina.luque
 *
 */
public class EmployeeCallSelectorStrategy  {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeCallSelectorStrategy.class);

	/**
	 * Return first operator, supervisor or director available (in order)
	 * @param employeeList
	 * @return
	 */
    public Employee findNextEmployeeAvailable(Collection<Employee> employeeList) {
        Validate.notNull(employeeList);
        List<Employee> availableEmployees = employeeList.stream().filter(e -> e.getEmployeeState() == EmployeeStateEnum.AVAILABLE).collect(Collectors.toList());

        Optional<Employee> employee = availableEmployees.stream().filter(e -> e instanceof Operator).findAny();
        if (!employee.isPresent()) {
            employee = availableEmployees.stream().filter(e -> e instanceof Supervisor).findAny();
            if (!employee.isPresent()) {
                employee = availableEmployees.stream().filter(e -> e instanceof Director).findAny();
                if (!employee.isPresent()) {
                    return null;
                }
            }
        }
        logger.info("Employee with range: " + employee.get().getClass().getName()+ " found");
        return employee.get();
    }

}

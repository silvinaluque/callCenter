package sluque.callcenter;

import org.junit.Test;
import sluque.callcenter.Employee;
import sluque.callcenter.EmployeeStateEnum;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Test EmployeeCallSelectorStrategyTest
 * @author silvina.luque
 *
 */
public class EmployeeCallSelectorStrategyTest {

    private EmployeeCallSelectorStrategy employeeCallSelectorStrategy;

    public EmployeeCallSelectorStrategyTest() {
        this.employeeCallSelectorStrategy = new EmployeeCallSelectorStrategy();
    }

    /**
     * Test operator priority 
     */
    @Test
    public void testTakeOperator() {
        Employee operator = new Operator("Operator");
        Employee supervisor = new Supervisor("Supervisor");
        Employee director = new Director("Director");
        List<Employee> employeeList = Arrays.asList(operator, supervisor, director);
        Employee employee = this.employeeCallSelectorStrategy.findNextEmployeeAvailable(employeeList);
        assertTrue(employee instanceof Operator);
        
    }

    /**
     * Test operator busy, take supervisor
     */
    @Test
    public void testTakeSupervisor() {
        Employee operator = mockBusyOperator("Operator");
        Employee supervisor = new Supervisor("Supervisor");
        List<Employee> employeeList = Arrays.asList(operator,supervisor);
        Employee employee = this.employeeCallSelectorStrategy.findNextEmployeeAvailable(employeeList);
        assertNotNull(employee);
    }

    /**
     * Test operator and supervisor busy, take director
     */
    @Test
    public void testOnlyDirectorDirector() {
        Employee operator = mockBusyOperator("Operator");
        Employee supervisor = mockBusySupervisor("Supervisor");
        Employee director = new Director("Director");
        List<Employee> employeeList = Arrays.asList(operator, supervisor, director);
        Employee employee = this.employeeCallSelectorStrategy.findNextEmployeeAvailable(employeeList);
        assertNotNull(employee);
    }

    /**
     * Test not available employees
     */
    @Test
    public void testAllBusy() {
        Employee operator = mockBusyOperator("Operator");
        Employee supervisor = mockBusySupervisor("Supervisor");
        Employee director = mockBusyDirector("Director");
        List<Employee> employeeList = Arrays.asList(operator, supervisor, director);
        Employee employee = this.employeeCallSelectorStrategy.findNextEmployeeAvailable(employeeList);
        assertNull(employee);
    }

    /**
     * Build employee operator busy
     * @param name
     * @return
     */
    private static Employee mockBusyOperator(String name) {
        Employee employee = mock(Operator.class);
        when(employee.getName()).thenReturn(name);
        when(employee.getEmployeeState()).thenReturn(EmployeeStateEnum.BUSY);
        return employee;
    }

    /**
     * Build employee supervisor busy
     * @param name
     * @return
     */
    private static Employee mockBusySupervisor(String name) {
        Employee employee = mock(Supervisor.class);
        when(employee.getName()).thenReturn(name);
        when(employee.getEmployeeState()).thenReturn(EmployeeStateEnum.BUSY);
        return employee;
    }
    
    /**
     * Build employee director busy
     * @param name
     * @return
     */
    private static Employee mockBusyDirector(String name) {
        Employee employee = mock(Director.class);
        when(employee.getName()).thenReturn(name);
        when(employee.getEmployeeState()).thenReturn(EmployeeStateEnum.BUSY);
        return employee;
    }



}

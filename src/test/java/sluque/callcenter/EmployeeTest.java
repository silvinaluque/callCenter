package sluque.callcenter;

import org.junit.Test;


import sluque.callcenter.Employee;
import sluque.callcenter.EmployeeStateEnum;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Test employee
 * @author silvina.luque
 *
 */
public class EmployeeTest {

	/**
	 * Test new employee with null value
	 */
    @Test(expected = NullPointerException.class)
    public void testEmployeeNullInit() {
        new Employee(null);
    }

    /**
     * Test available employee when is init
     */
    @Test
    public void testInitEmployee() {
        Employee employee = new Operator("Operator");
        assertNotNull(employee);
        assertEquals(EmployeeStateEnum.AVAILABLE, employee.getEmployeeState());
    }

    /**
     * Test employee take a call when is available and don't drink coffee
     * @throws InterruptedException
     */
    @Test
    public void testEmployeeAttendWhileAvailable() throws InterruptedException {
        Employee employee = new Operator("Operator");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(employee);
        employee.takeCall(MockCallGenerator.buildCall(1, 5));
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        assertEquals(1, employee.getHandledCalls().intValue());
    }

    /**
     * Test employee is busy when is in a call 
     * @throws InterruptedException
     */
    @Test
    public void testEmployeeStatesWhileAttend() throws InterruptedException {
        Employee employee = new Operator("Operator");
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(employee);
        assertEquals(EmployeeStateEnum.AVAILABLE, employee.getEmployeeState());
        TimeUnit.SECONDS.sleep(1);
        employee.takeCall(MockCallGenerator.buildCall(2, 3));
        employee.takeCall(MockCallGenerator.buildCall(0, 1));
        TimeUnit.SECONDS.sleep(1);
        assertEquals(EmployeeStateEnum.BUSY, employee.getEmployeeState());

        executorService.awaitTermination(5, TimeUnit.SECONDS);
        assertEquals(2, employee.getHandledCalls().intValue());
    }

}

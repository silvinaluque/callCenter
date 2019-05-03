package sluque.callcenter;

import org.junit.Test;
import sluque.callcenter.Call;
import sluque.callcenter.Dispatcher;
import sluque.callcenter.Employee;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test calls dispatcher.
 * @author silvina.luque
 *
 */
public class DispatcherTest {

    private static final int CALLS_10 = 10;
    
    private static final int CALLS_20 = 20;
    
    private static final int CALL_DURATION_MIN = 5;

    private static final int CALL_DURATION_MAX = 10;
    
    
    /**
     * Test init invalid dispatcher
     */
    @Test(expected = NullPointerException.class)
    public void testDispatcherNullCallCenterRules() {
        new Dispatcher(null);
    }


    
    /**
     * Test with 10 employees and 10 calls
     * @throws InterruptedException
     */
    @Test
    public void testDispatchCallsToEmployees() throws InterruptedException {
        CallCenterRules callCenterRules = buildCallCenterRules();
        Dispatcher dispatcher = new Dispatcher(callCenterRules);
        dispatcher.start();
        TimeUnit.SECONDS.sleep(1);
        //pool with  only dispatcher thread 
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(dispatcher);
        TimeUnit.SECONDS.sleep(1);

        buildCallList(CALLS_10).stream().forEach(call -> {
            dispatcher.dispatchCall(call);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                fail();
            }
        });
        
        //validate all calls were handled
        executorService.awaitTermination(CALL_DURATION_MAX * 2, TimeUnit.SECONDS);
        assertEquals(CALLS_10, callCenterRules.getEmployees().stream().mapToInt(employee -> employee.getHandledCalls()).sum());
    }

    
    /**
     * Test with 2 employees and 10 calls
     * @throws InterruptedException
     */
    @Test
    public void testDispatchCallsTo2Employees() throws InterruptedException {
        CallCenterRules callCenterRules = buildCallCenterRules2Employees();
        Dispatcher dispatcher = new Dispatcher(callCenterRules);
        dispatcher.start();
        TimeUnit.SECONDS.sleep(1);
        //pool with  only dispatcher thread 
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(dispatcher);
        TimeUnit.SECONDS.sleep(1);

        buildCallList(CALLS_10).stream().forEach(call -> {
            dispatcher.dispatchCall(call);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                fail();
            }
        });
        
        //validate all calls were handled
        executorService.awaitTermination(CALL_DURATION_MAX * 5, TimeUnit.SECONDS);
        assertEquals(CALLS_10, callCenterRules.getEmployees().stream().mapToInt(employee -> employee.getHandledCalls()).sum());
    }
    
    

    
    /**
     * Test with 10 employees and 20 calls
     * @throws InterruptedException
     */
    @Test
    public void testDispatch20CallsEmployees() throws InterruptedException {
        CallCenterRules callCenterRules = buildCallCenterRules();
        Dispatcher dispatcher = new Dispatcher(callCenterRules);
        dispatcher.start();
        TimeUnit.SECONDS.sleep(1);
        //pool with  only dispatcher thread 
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(dispatcher);
        TimeUnit.SECONDS.sleep(1);

        buildCallList(CALLS_20).stream().forEach(call -> {
            dispatcher.dispatchCall(call);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                fail();
            }
        });
        
        //validate all calls were handled
        executorService.awaitTermination(CALL_DURATION_MAX * 2, TimeUnit.SECONDS);
        assertEquals(CALLS_20, callCenterRules.getEmployees().stream().mapToInt(employee -> employee.getHandledCalls()).sum());
    }


	/**
     * Build mock employees list
     * @return
     */
    private static List<Employee> build10EmployeeList() {
	    Employee operator1 = new Operator("Operador1");
	    Employee operator2 = new Operator("Operador2");
	    Employee operator3 = new Operator("Operador3");
	    Employee operator4 = new Operator("Operador4");
	    Employee operator5 = new Operator("Operador5");
	    Employee operator6 = new Operator("Operador6");
	    Employee supervisor1= new Supervisor("Supervisor1");
	    Employee supervisor2= new Supervisor("Supervisor2");
	    Employee supervisor3= new Supervisor("Supervisor3");
	    Employee director1 = new Director("Director1");
        return Arrays.asList(operator1, operator2, operator3, operator4, operator5, operator6,
                supervisor1, supervisor2, supervisor3, director1);
    }

    private static List<Call> buildCallList(int numberOfCalls) {
        return MockCallGenerator.buildListCalls(numberOfCalls, CALL_DURATION_MIN, CALL_DURATION_MAX);
    }
    
    /**
     * Make call center rules with 10 employees and selector strategy
     * @return
     */
    private CallCenterRules buildCallCenterRules() {
	    CallCenterRules callRules = new CallCenterRules();
        List<Employee> employeeList = build10EmployeeList();
		callRules.setEmployees(employeeList);
	    EmployeeCallSelectorStrategy employeeSelector = new EmployeeCallSelectorStrategy();
		callRules.setEmployeeSelector(employeeSelector );
	    return callRules;	
    }

    /**
     * Make call center rules with 2 employees and selector strategy
     * @return
     */
    private CallCenterRules buildCallCenterRules2Employees() {
	    CallCenterRules callRules = new CallCenterRules();
        List<Employee> employeeList = build2EmployeeList();
		callRules.setEmployees(employeeList);
	    EmployeeCallSelectorStrategy employeeSelector = new EmployeeCallSelectorStrategy();
		callRules.setEmployeeSelector(employeeSelector );
	    return callRules;	
    }


    /**
     * Build 2 employees list
     * @return
     */
	private List<Employee> build2EmployeeList() {
	    Employee operator1 = new Operator("Operador1");
	    Employee supervisor1= new Supervisor("Supervisor1");
        return Arrays.asList(operator1, supervisor1 );
	}

    
    
}

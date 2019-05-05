package sluque.callcenter;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

/**
 * Employee model
 * @author silvina.luque
 *
 */
public class Employee implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Employee.class);

    private EmployeeStateEnum employeeState;
    
    private String name;

    private ConcurrentLinkedDeque<Call> incomingCalls;

    //total calls attended for check employee work
    private Integer handledCalls;

    public Employee(String name) {
        Validate.notNull(name);
        this.name = name;
        this.employeeState = EmployeeStateEnum.AVAILABLE;
        this.incomingCalls = new ConcurrentLinkedDeque<>();
        this.handledCalls = 0;
    }


    public synchronized EmployeeStateEnum getEmployeeState() {
        return employeeState;
    }

    private synchronized void setEmployeeState(EmployeeStateEnum employeeState) {
        logger.info("Employee " + this.getName() + " now is: " + employeeState);
        this.employeeState = employeeState;
    }

    public synchronized Integer getHandledCalls() {
        return handledCalls;
    }

    
    public synchronized void takeCall(Call call) {
        logger.info("Employee " +  this.getName()  + " take a call of " + call.getDuration() + " sec.");
        this.incomingCalls.add(call);
    }


    /**
     * Employee is busy  during the call and then is available again
     */
    @Override
    public void run() {
        logger.info("Employee " + this.getName() + " it's ready to work");
        while (true) {
            if (!this.incomingCalls.isEmpty()) {
                Call call = this.incomingCalls.poll();
                this.setEmployeeState(EmployeeStateEnum.BUSY);
                logger.info("Employee " + this.getName() + " starts a call.");
                try {
                    TimeUnit.SECONDS.sleep(call.getDuration());
                } catch (InterruptedException e) {
                    logger.error("Employee " + this.getName() + " not finish call.");
                } finally {
                    this.setEmployeeState(EmployeeStateEnum.AVAILABLE);
                }
                this.handledCalls++;
                logger.info("Employee " + this.getName() + " finishes a call.");
            }
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

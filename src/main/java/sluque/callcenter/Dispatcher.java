package sluque.callcenter;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Dispatcher calls model
 * @author silvina.luque
 *
 */
public class Dispatcher implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

	private static final int MIN_CURRENT_CALLS = 10;

    private Boolean ready;

    private ExecutorService executorService;
    
    private CallCenterRules callCenterRules;

    private ConcurrentLinkedDeque<Call> offeredCalls;

    
    /**
     * Dispatcher creation 
     * @param callCenterRules
     */
    public Dispatcher(CallCenterRules callCenterRules) {
    	Validate.notNull(callCenterRules);
        Validate.notNull(callCenterRules.getEmployees());
        Validate.notNull(callCenterRules.getEmployeeSelector());
        Validate.isTrue(callCenterRules.getEmployees().size() >= MIN_CURRENT_CALLS);
        this.callCenterRules = callCenterRules;
        this.offeredCalls = new ConcurrentLinkedDeque<>();
        this.executorService = Executors.newFixedThreadPool(callCenterRules.getEmployees().size());
                
    }


    /**
     * Add new incoming calls to the queue
     * @param call
     */
    public synchronized void dispatchCall(Call call) {
        logger.info("New call incoming....");
        this.offeredCalls.addLast(call);
    }


    public synchronized void start() {
        this.ready = true;
        for (Employee employee : this.callCenterRules.getEmployees()) {
        	//employees "threads" in line!
            this.executorService.execute(employee);
        }
    }


    public synchronized void stop() {
        this.ready = false;
        this.executorService.shutdown();
    }

    public synchronized Boolean getReady() {
        return ready;
    }

    /**
     * Assign offered calls to available employees.
     */
    @Override
    public void run() {
        while (getReady()) {
            if (this.offeredCalls.isEmpty()) {
                continue;
            } else {
                Employee employee = this.callCenterRules.getAvailableEmployee();
                if (employee == null) {
                    continue;
                }
                Call call = this.offeredCalls.pollFirst();
                try {
                    employee.takeCall(call);
                } catch (Exception e) {
                	//something failed, add call to the queue
                    logger.error(e.getMessage());
                    this.offeredCalls.addFirst(call);
                }
            }
        }
    }

}

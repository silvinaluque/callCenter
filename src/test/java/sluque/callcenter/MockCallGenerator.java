package sluque.callcenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.Validate;

/**
 * Class for build random calls
 * @author silvina.luque
 *
 */
public  class MockCallGenerator {
	
    /**
     * Build a call 
     * @param minDuration
     * @param maxDuration
     * @return
     */
    public  static Call buildCall(Integer minDuration, Integer maxDuration) {
        return new Call(ThreadLocalRandom.current().nextInt(minDuration, maxDuration + 1));
    }

    /**
     * Build a list of calls
     * @param size
     * @param minDuration
     * @param maxDuration
     * @return
     */
    public  static List<Call> buildListCalls(Integer size, Integer minDuration, Integer maxDuration) {
        Validate.isTrue(size >= 0);
        List<Call> callList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            callList.add(buildCall(minDuration, maxDuration));
        }
        return callList;
    }

}

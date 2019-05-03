package sluque.callcenter;

import org.junit.Test;

import sluque.callcenter.Call;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test call creation.
 * @author silvina.luque
 *
 */
public class CallTest {
	
	/**
	 * Test creation a valid call
	 */
    @Test
    public void testCallCreation() {
        Integer min = 5;
        Integer max = 10;
        Call call = MockCallGenerator.buildCall(min, max);

        assertNotNull(call);
        assertTrue(min <= call.getDuration());
        assertTrue(call.getDuration() <= max);
    }
    
    /**
     * Test creation with invalid duration
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCallCreationWithInvalidParameter() {
        new Call(-1);
    }

    /**
     * Test creation with null duration
     */
    @Test(expected = NullPointerException.class)
    public void testCallCreationWithNullParameter() {
        new Call(null);
    }




}

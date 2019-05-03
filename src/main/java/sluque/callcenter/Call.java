package sluque.callcenter;

import org.apache.commons.lang3.Validate;

/**
 * Call model
 * @author silvina.luque
 *
 */
public class Call {

    private Integer duration;

    public Call(Integer duration) {
        Validate.notNull(duration);
        Validate.isTrue(duration >= 0);
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }


}

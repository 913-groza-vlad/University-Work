package Messages;

import java.io.Serializable;

public class UpdateMessage extends Message implements Serializable {
    private String variable;
    private int value;

    public UpdateMessage(String variable, int value) {
        this.variable = variable;
        this.value = value;
    }

    public String getVariable() {
        return this.variable;
    }

    public int getValue() {
        return this.value;
    }
}

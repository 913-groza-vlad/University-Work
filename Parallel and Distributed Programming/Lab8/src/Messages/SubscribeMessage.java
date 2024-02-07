package Messages;

import java.io.Serializable;

public class SubscribeMessage extends Message implements Serializable {
    private String variable;
    private int rank;

    public SubscribeMessage(String variable, int rank) {
        this.variable = variable;
        this.rank = rank;
    }

    public String getVariable() {
        return this.variable;
    }

    public int getRank() {
        return this.rank;
    }
}

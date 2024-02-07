import Messages.CloseMessage;
import Messages.Message;
import Messages.SubscribeMessage;
import Messages.UpdateMessage;
import mpi.MPI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DSM {
    private Map<String, Set<Integer>> subscribers;
    private Map<String, Integer> variables;

    private final Lock lock = new ReentrantLock();

    public DSM(Map<String, Set<Integer>> subscribers, Map<String, Integer> variables) {
        this.subscribers = subscribers;
        this.variables = variables;
    }

    public void setVariable(String variable, int value) {
        this.variables.put(variable, value);
    }

    private void sendMessageToSubscribers(String variable, Message message) {
        for (int i = 0; i < MPI.COMM_WORLD.Size(); i++) {
            if (this.subscribers.get(variable).contains(i) && i != MPI.COMM_WORLD.Rank()) {
                MPI.COMM_WORLD.Send(new Object[]{message}, 0, 1, MPI.OBJECT, i, 0);
            }
        }
    }

    public void updateVariable(String variable, int value) {
        this.setVariable(variable, value);
        this.sendMessageToSubscribers(variable, new UpdateMessage(variable, value));
    }

    public void compareAndExchange(String variable, int value, int newValue) {
        lock.lock();
        if (this.variables.get(variable).equals(value) && this.subscribers.get(variable).contains(MPI.COMM_WORLD.Rank())) {
            this.updateVariable(variable, newValue);
        }
        lock.unlock();
    }

    private void sendMessageToAllNodes(Message message) {
        for (int i = 0; i < MPI.COMM_WORLD.Size(); i++) {
            if (i != MPI.COMM_WORLD.Rank() || message instanceof CloseMessage) {
                MPI.COMM_WORLD.Send(new Object[]{message}, 0, 1, MPI.OBJECT, i, 0);
            }
        }
    }

    public void subscribe(String variable) {
        Set<Integer> subscribers = this.subscribers.get(variable);
        subscribers.add(MPI.COMM_WORLD.Rank());
        this.subscribers.put(variable, subscribers);
        sendMessageToAllNodes(new SubscribeMessage(variable, MPI.COMM_WORLD.Rank()));
    }

    public void syncSubscription(String variable, int rank) {
        Set<Integer> subscribers = this.subscribers.get(variable);
        subscribers.add(rank);
        this.subscribers.put(variable, subscribers);
    }

    public void close() {
        sendMessageToAllNodes(new CloseMessage());
    }

    @Override
    public String toString() {
        return "DSM{" +
                "subscribers=" + subscribers +
                ", variables=" + variables +
                '}';
    }

}

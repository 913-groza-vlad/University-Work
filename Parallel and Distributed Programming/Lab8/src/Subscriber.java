import Messages.CloseMessage;
import Messages.Message;
import Messages.SubscribeMessage;
import Messages.UpdateMessage;
import mpi.*;

public class Subscriber implements Runnable {

    private DSM dsm;

public Subscriber(DSM dsm) {
        this.dsm = dsm;
    }

    @Override
    public void run() {
        while (true) {
            Object[] messageObject = new Object[1];
            MPI.COMM_WORLD.Recv(messageObject, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, MPI.ANY_TAG);
            Message message = (Message) messageObject[0];

            if (message instanceof UpdateMessage updateMessage) {
                System.out.println("Process " + MPI.COMM_WORLD.Rank() + " received an update message for variable " + updateMessage.getVariable() + " with value " + updateMessage.getValue());
                this.dsm.setVariable(updateMessage.getVariable(), updateMessage.getValue());
            }
            else if (message instanceof SubscribeMessage subscribeMessage) {
                System.out.println("Process " + MPI.COMM_WORLD.Rank() + " received a subscribe message: " + subscribeMessage.getRank() + " subscribed to variable " + subscribeMessage.getVariable());
                this.dsm.syncSubscription(subscribeMessage.getVariable(), subscribeMessage.getRank());
            }
            else if (message instanceof CloseMessage) {
                System.out.println("Process " + MPI.COMM_WORLD.Rank() + " closed");
                break;
            }
        }

        System.out.println("Node " + MPI.COMM_WORLD.Rank() + " -> " + this.dsm);
    }
}

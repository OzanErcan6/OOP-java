
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface IMazeHub extends Remote {
    void createMaze(int width, int height) throws RemoteException;
    IMaze getMaze(int index) throws RemoteException;
    boolean removeMaze(int index) throws RemoteException;


    int getNumberOfMaze() throws RemoteException;

    void Servercreate(int height, int width, int index) throws RemoteException;
    MazeObject ServergetObject(Position position,int index) throws RemoteException;
    boolean ServercreateObject(Position position, MazeObjectType type,int index) throws RemoteException;
    boolean ServerdeleteObject(Position position,int index) throws RemoteException;
    Vector<Agent> ServergetAgents(int index) throws RemoteException;
    boolean ServermoveAgent(int id, Position position,int index) throws RemoteException;
    void Serverprint(int index) throws RemoteException;
}

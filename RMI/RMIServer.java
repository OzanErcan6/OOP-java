import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;


public class RMIServer extends UnicastRemoteObject implements IMazeHub{
    private static final long serialVersionUID = 1L;
    private Vector<Maze> mazeHubVector;

    private RMIServer() throws RemoteException {
        mazeHubVector = new Vector<Maze>();
    }


    @Override
    public void createMaze(int width, int height) throws RemoteException {
        Maze maze = new Maze(width,height);
        mazeHubVector.add(maze);
    }

    @Override
    public IMaze getMaze(int index) throws RemoteException {
        if(index<0 || mazeHubVector.size()<index+1) return null;

        return mazeHubVector.elementAt(index);
    }

    @Override
    public boolean removeMaze(int index) throws RemoteException {

        if(index<0 || mazeHubVector.size()<index+1) return false;

        mazeHubVector.removeElementAt(index);
        return true;
    }

    @Override
    public int getNumberOfMaze() throws RemoteException {

        if(mazeHubVector!=null)
            return mazeHubVector.size();

        else return 0;
    }




    @Override
    public void Servercreate(int height, int width, int index) throws RemoteException {
        this.getMaze(index).create(height,width);
    }

    @Override
    public MazeObject ServergetObject(Position position, int index) throws RemoteException {
        return this.getMaze(index).getObject(position);
    }

    @Override
    public boolean ServercreateObject(Position position, MazeObjectType type, int index) throws RemoteException {
        return this.getMaze(index).createObject(position,type);
    }

    @Override
    public boolean ServerdeleteObject(Position position, int index) throws RemoteException {
        return this.getMaze(index).deleteObject(position);
    }

    @Override
    public Vector<Agent> ServergetAgents(int index) throws RemoteException {
        return this.getMaze(index).getAgents();
    }

    @Override
    public boolean ServermoveAgent(int id, Position position, int index) throws RemoteException {
        return this.getMaze(index).moveAgent(id,position);
    }

    @Override
    public void Serverprint(int index) throws RemoteException {

        if(this.getMaze(index).print()=="") return;
        String[] printParts = this.getMaze(index).print().split(",");

        for(int i = 0 ; i < this.getMaze(index).getHeight()+2;i++){
            String part0 = printParts[i];
            System.out.println(part0);
        }
    }




    public static void main(String[] args){
        try {
            Naming.rebind("//localhost/MyServer", new RMIServer());

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }


}

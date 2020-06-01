/*
import java.rmi.RemoteException;
import java.util.Vector;

public class MazeHub implements IMazeHub{
    private Vector<Maze> mazeHubVector;

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
    public void helloTo(String userName) throws RemoteException {
        System.out.println("buraya girmemesi lazım");
    }
}
*/

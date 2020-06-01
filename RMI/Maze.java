import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Vector;

public class Maze implements IMaze, Serializable {
    private int height;
    private int width;
    private Vector <MazeObject> vector;
    private Vector <Agent> agentVector;

    int agentID;

    Maze(int width,int height){
        this.height=height;
        this.width=width;

        vector= new Vector<MazeObject>();
        agentVector= new Vector<Agent>();
        agentID=0;
    }

    @Override
    public void create(int height, int width) throws RemoteException {
        this.height=height;
        this.width=width;

        vector= new Vector<MazeObject>();
        agentVector= new Vector<Agent>();
        agentID=0;
    }

    @Override
    public MazeObject getObject(Position position) throws RemoteException {

        for (MazeObject obj : vector) {
            if(obj.getPosition().distance(position)==0)
            //System.out.println(obj.toString()+obj.getPosition().toString());
            return obj;
        }
        return null;
    }

    @Override
    public boolean createObject(Position position, MazeObjectType type) throws RemoteException {

        if(position.getX()> width || position.getX()<0 || position.getY()>height || position.getY()<0) return false;


        MazeObject obj = new MazeObject(position,type);
        Agent agent = new Agent(position,agentID);

        if(getObject(position)==null && type==MazeObjectType.AGENT) {
            vector.add(obj);
            agentVector.add(agent);
            agentID++;
            return true;
        }

        if(getObject(position)==null) {
            vector.add(obj);
            return true;
        }

        return false;
    }

    public void killAgent(MazeObject obj){
        for (Agent a: agentVector){
            if(a.getPosition().distance(obj.getPosition())==0){
                a.killAgent();
            }

        }
    }

    @Override
    public boolean deleteObject(Position position) throws RemoteException {
        //System.out.println("delete object girdi" + position.toString() + " silinmeye çalışıyor");
        for (MazeObject obj: vector) {
            if (obj.getPosition().distance(position)==0){

                //System.out.println("doğru yere girdi");
                //vector.removeElement(obj);
                killAgent(obj);
                //obj.setPosition(new Position(-10,-10));
                vector.remove(obj);
                return true;
            }
        }
        return false;
    }

    @Override
    public Vector<Agent> getAgents() throws RemoteException {

        for (Agent a: agentVector){
            if(!a.isAgentAlive()){
                //System.out.println("agent removed");
                agentVector.remove(a);
            }

        }

        if (agentVector.size()==0) return null;
       return agentVector;
    }

    public Agent getAgentWithId(int id){
        for(Agent v : agentVector ){
            if (v.getId()==id) return v;
        }
        return null;
    }


    @Override
    public boolean moveAgent(int id, Position position) throws RemoteException {

        if(getAgentWithId(id)==null) return false;
        if(getAgentWithId(id).getPosition().distance(position)==1 && getAgentWithId(id).isAgentAlive()){ return true;}

/*        if(getAgentWithId(id).getPosition().distance(position)==1 && getAgentWithId(id).isAgentAlive()){
            if(getObject(position).getType()==MazeObjectType.HOLE) {
                // remove from list and vector
                //System.out.println("deliğe düştü");
                deleteObject(position);
                return true;
            }
            if(getObject(position).getType()==MazeObjectType.WALL || getObject(position).getType()==MazeObjectType.AGENT){
                // stays in same position
                return false;
            }
            if(getObject(position).getType()==MazeObjectType.GOLD) {
                // collect and move
                deleteObject(position);

                for (MazeObject obj: vector) {
                    if (obj.getPosition().distance(getAgentWithId(id).getPosition())==0){
                        obj.setPosition(position);
                        getAgentWithId(id).AddGold();
                        getAgentWithId(id).setPosition(position);
                        return true;
                    }
                }

            }
            else{
                for (MazeObject obj: vector) {
                    if (obj.getPosition().distance(getAgentWithId(id).getPosition())==0){
                        //obj.setPosition(position);
                        //getAgentWithId(id).setPosition(position);
                        return true;
                    }
                }
            }
        }*/

        return false;
    }

    @Override
    public String print() throws RemoteException{
        String result = "";


        for (int j = 0; j < height+2; j++) {
            for (int i = 0; i < width+2; i++) {

                if((i==0 && j==0) || (i==0 && j==height+1) || (i==width+1 && j==0) || (i==width+1 && j==height+1))
                    result = result + "+";
                else if(j==0 || j==height+1){
                    result = result + "-";
                }

                else if(i==0 || i==width+1)
                    result = result + "|";
                else {
                    Position position=new Position(i-1,j-1);
                    if(getObject(position)!=null){
                        result = result + this.getObject(position).toString();
                    }

                    else
                        result=result+" ";

                }
            }
            result=result+",";
        }


        //bu commandi açınca null pointer exception veriyor getObject çalışmıyor
        //System.out.println(this.getObject(new Position(3,3)).toString());
        //for (MazeObject obj : vector) {
         //   System.out.println(obj.toString()+obj.getPosition().toString());

        //}

        return result;
    }

    @Override
    public int getHeight(){
        return height;
    }
}




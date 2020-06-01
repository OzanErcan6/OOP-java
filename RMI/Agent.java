import java.io.Serializable;

public class Agent extends MazeObject implements Serializable {
    
    private final int id;
    private int collectedGold;
    private boolean isAlive;
    public Agent(Position position, int id) {
        super(position, MazeObjectType.AGENT);
        this.id = id;
        this.collectedGold = 0;
        this.isAlive=true;
    }
    public void AddGold(){
        collectedGold++;
    }

    public void killAgent(){
        this.isAlive=false;
    }

    public boolean isAgentAlive(){
        return isAlive;
    }

    public int getId(){return id;}
    public int getCollectedGold(){return collectedGold;}


}

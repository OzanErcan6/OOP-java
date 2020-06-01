import java.util.Iterator;
import java.util.Vector;

/**
 *calculateCloserZombie method finds the closer zombie or soldier depending on you input list
 * (if you give soldier list it finds closer Soldier)
 * also i save objects' general types which are "soldier","zombie" or "bullet" in this class because it is common.
 */
public abstract class SimulationObject {
    private final String name;
    private Position position;
    private Position direction;
    private final double speed;
    private boolean active;
    private String generalType;


    public SimulationObject(String name, Position position, double speed) {
        this.name = name;
        this.position = position;
        this.speed = speed;
        this.direction = null;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getDirection() {
        return direction;
    }

    public void setDirection(Position direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract void step(SimulationController controller);

    public boolean isInBound(SimulationController controller,Position position){

        if(position.getX() > controller.getWidth() || position.getX() < 0) return false;
        if(position.getY() > controller.getHeight() || position.getY() < 0) return false;
        return true;
    }
    public void setType(String x){
        generalType=x;
    }

    public String getType(){return generalType;}

    public  SimulationObject calculateCloserZombie(Vector<SimulationObject> zombies) {
        if (zombies.isEmpty()) return null;

        SimulationObject zombie1 =null;
        double min = Double.MAX_VALUE;

        Iterator<SimulationObject> iterator= zombies.iterator();

        while (iterator.hasNext()){
            SimulationObject z = iterator.next();
            if(z.isActive()) {
                double distance = this.getPosition().distance(z.getPosition());
                if (distance < min && z.isActive()) {
                    min = distance;
                    zombie1 = z;
                }
            }
        }
        return zombie1;
    }


    public abstract double getCollisionRange();
}

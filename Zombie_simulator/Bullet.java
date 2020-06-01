import java.util.Iterator;

/**
 *Bullet class extends Simulation Object
 *Because we need an object to represent bullet and there are some common functions and variables.
 *
 */

public class Bullet extends SimulationObject{

    public Bullet(String name, Position position, double speed,Position direction) {
        super(name, position, speed);
        this.setDirection(direction);
        setType("bullet");
    }

    /**
     *firstly check if the bullet is active
     * also check if the simulation is over
     * i checked for every zombie in the zombie list from controller
     */
    @Override
    public void step(SimulationController controller) {
        if(!this.isActive()){
            return;
        }

        if(controller.isFinished())
            return;

        for (int x=0 ;x<=this.getSpeed();x++){

            if(!this.isInBound(controller,this.getPosition())){
                System.out.println(this.getName()+" moved out of bounds");
                this.setActive(false);
                return;
            }
            Iterator<SimulationObject> iterator= controller.getZombies().iterator();

            while (iterator.hasNext()){
                SimulationObject zombie = iterator.next();

                if(this.getPosition().distance(zombie.getPosition())<=zombie.getCollisionRange() && zombie.isActive()){
                    System.out.println(this.getName()+" hit "+zombie.getName());
                    zombie.setActive(false);
                    this.setActive(false);
                    return;
                }
            }

            Position newPos = new Position(this.getPosition().getX()+this.getDirection().getX(),this.getPosition().getY()+this.getDirection().getY());
            this.setPosition(newPos);
            x++;

            }
        System.out.println(this.getName()+" dropped to the ground at "+this.getPosition().toString());
        this.setActive(false);

}
    public double getCollisionRange(){return -1;}


}

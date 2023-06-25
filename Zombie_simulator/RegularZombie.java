import java.util.Iterator;
/**
 *I decleare the String "soldier" for every soldier object and "zombie" for every zombie
 * because at the beginning i put soldiers and zombies into separate lists.
 * other than that soldiers and zombies constructors and functions similar with each other
 * Also i can change collisionRange , starting state etc from constructor easily
 */

public interface ZombieState {
    void step(RegularZombie zombie, SimulationController controller);
}

public class WanderingState implements ZombieState {
    @Override
    public void step(RegularZombie zombie, SimulationController controller) {
        // Lógica específica del estado de "wandering"
        // ...
    }
}

public class FollowingState implements ZombieState {
    @Override
    public void step(RegularZombie zombie, SimulationController controller) {
        // Lógica específica del estado de "following"
        // ...
    }
}

public class RegularZombie extends SimulationObject {

    private ZombieState state;
    private double collisionRange;
    private double detectionRange;
    private int NoOfFollowingStep;


    public RegularZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name,position,5.0);
        state=ZombieState.WANDERING;
        this.setDirection(Position.generateRandomDirection(true));
        this.collisionRange=2.0;
        this.detectionRange=20.0;
        setType("zombie");
        NoOfFollowingStep=0;
    }

    @Override
    public void step(SimulationController controller) {

        if(!this.isActive()){
            return;
        }


        if(this.calculateCloserZombie(controller.getSoldiers())!=null){

            Iterator<SimulationObject> iterator= controller.getSoldiers().iterator();

            while (iterator.hasNext()){
                SimulationObject soldier = iterator.next();
                double max = this.getCollisionRange() + soldier.getCollisionRange();

                double soldierDistance = this.calculateCloserZombie(controller.getSoldiers()).getPosition().distance(this.getPosition());
                if(soldierDistance<=max && soldier.isActive() ){
                    soldier.setActive(false);
                    System.out.println(this.getName()+" killed "+soldier.getName());
                    return;
                    }
                }
            }

        if (this.state == ZombieState.WANDERING) {


            SimulationObject closeSoldier = this.calculateCloserZombie(controller.getSoldiers());
            double distance ;

            //Calculate the next position of the zombie
            Position new_position = new Position(this.getPosition().getX() + this.getDirection().getX() * this.getSpeed(), this.getPosition().getY() + this.getDirection().getY() * this.getSpeed());
            if (isInBound(controller, new_position)) {
                this.setPosition(new_position);
                System.out.println(this.getName()+" moved to "+this.getPosition().toString());
            }
            if (!this.isInBound(controller, new_position)) {
                //If the position is out of bounds, change direction to random value.
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(this.getName()+" changed direction to "+this.getDirection().toString());

                }

            if (closeSoldier != null) {
                distance = this.getPosition().distance(closeSoldier.getPosition());

                if ( distance <= this.detectionRange) {
                    NoOfFollowingStep=0;
                    state = ZombieState.FOLLOWING;
                    System.out.println(this.getName()+" changed state to "+this.state.toString());

                }
            }
            return;
        }

        if(this.state==ZombieState.FOLLOWING && this.NoOfFollowingStep!=4) {
            //Calculate the next position of the zombie
            Position new_position = new Position(this.getPosition().getX() + this.getDirection().getX() * this.getSpeed(), this.getPosition().getY() + this.getDirection().getY() * this.getSpeed());
            if (!this.isInBound(controller, new_position)) {
                //If the position is out of bounds, change direction to random value.
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(this.getName() + " changed direction to " + this.getDirection().toString());
            }

            if (isInBound(controller, new_position)) {
                this.setPosition(new_position);
                System.out.println(this.getName() + " moved to " + this.getPosition().toString());

            }
            NoOfFollowingStep++;
            return;
        }

        if(NoOfFollowingStep==4&&this.state==ZombieState.FOLLOWING){
            NoOfFollowingStep=0;
            this.state = ZombieState.WANDERING;
            System.out.println(this.getName() + " changed state to " + this.state.toString());
        }

        }
    public double getCollisionRange(){return this.collisionRange;}
    }




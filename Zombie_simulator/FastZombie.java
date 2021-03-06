import java.util.Iterator;
/**
 *I decleare the String "soldier" for every soldier object and "zombie" for every zombie
 * because at the beginning i put soldiers and zombies into separate lists.
 * other than that soldiers and zombies constructors and functions similar with each other
 * Also i can change collisionRange , starting state etc from constructor easily
 *
 */
public class FastZombie extends SimulationObject{
    private ZombieType type;
    private ZombieState state;
    private double collisionRange;
    private double detectionRange;

    public FastZombie(String name, Position position) {
        super(name,position,20.0);
        type=ZombieType.FAST;
        state=ZombieState.WANDERING;
        this.setDirection(Position.generateRandomDirection(true));
        this.collisionRange=2.0;
        this.detectionRange=20.0;
        setType("zombie");
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
            double distance=-1;
            if(closeSoldier!=null) distance = this.getPosition().distance(closeSoldier.getPosition());

            if (distance <= this.detectionRange&&closeSoldier!=null) {
                state = ZombieState.FOLLOWING;
                System.out.println(this.getName() + " changed state to " + this.state.toString());
                return;}
            else {
                Position new_position = new Position(this.getPosition().getX() + this.getDirection().getX() * this.getSpeed(), this.getPosition().getY() + this.getDirection().getY() * this.getSpeed());
                if (!this.isInBound(controller, new_position)) {
                    //If the position is out of bounds, change direction to random value.
                    this.setDirection(Position.generateRandomDirection(true));
                    System.out.println(this.getName()+" changed direction to "+this.getDirection().toString());
                }
                if (isInBound(controller, new_position)) {
                    this.setPosition(new_position);
                    System.out.println(this.getName()+" moved to "+this.getPosition().toString());
                }
            }
        }


        if(this.state==ZombieState.FOLLOWING ) {
            Position new_position = new Position(this.getPosition().getX() + this.getDirection().getX() * this.getSpeed(), this.getPosition().getY() + this.getDirection().getY() * this.getSpeed());
            if (!this.isInBound(controller, new_position)) {
                //If the position is out of bounds, change direction to random value.
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(this.getName()+" changed direction to "+this.getDirection().toString());
            }
            if (isInBound(controller, new_position)) {
                this.setPosition(new_position);
                System.out.println(this.getName()+" moved to "+this.getPosition().toString());
            }
            state = ZombieState.WANDERING;
            System.out.println(this.getName() + " changed state to " + this.state.toString());

        }



    }
    @Override
    public double getCollisionRange(){return this.collisionRange;}


}

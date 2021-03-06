/**
 *I decleare the String "soldier" for every soldier object and "zombie" for every zombie
 * because at the beginning i put soldiers and zombies into separate lists.
 * other than that soldiers and zombies constructors and functions similar with each other
 * Also i can change collisionRange , starting state etc from constructor easily
 * To improve the code i could add bulletSpeed variable to the constructor instead of when creating bullet typing 100 for sniper for example.
 */
public class Sniper extends SimulationObject{
    private SoldierState state;
    private SoldierType type;
    private double collisionRange;
    private double shootingRange;

    public Sniper(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name,position,2.0);
        this.state=SoldierState.SEARCHING;
        this.collisionRange=5.0;
        this.shootingRange=40.0;
        setType("soldier");
        this.type=SoldierType.SNIPER;
        this.setActive(true);
        this.setDirection(Position.generateRandomDirection(true));
    }

    public SoldierType getSoldierType() {
        return this.type;
    }

    @Override
    public void step(SimulationController controller) {
        if(!this.isActive()) {
            return;
        }
        if(!controller.getBullets().isEmpty()) return;

        if (this.state == SoldierState.SEARCHING) {
            Position new_position = new Position(this.getPosition().getX() + this.getDirection().getX() * this.getSpeed(), this.getPosition().getY() + this.getDirection().getY() * this.getSpeed());
            if (!this.isInBound(controller, new_position)) {
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(this.getName()+" changed direction to "+this.getDirection().toString());
            }
            if (isInBound(controller, new_position)) {
                setPosition(new_position);
                System.out.println(this.getName()+" moved to "+this.getPosition().toString());
            }

            state=SoldierState.AIMING;
            System.out.println(this.getName()+" changed state to "+this.state.toString());
            return;
        }

        if(this.state==SoldierState.AIMING) {
            SimulationObject closeZombie = this.calculateCloserZombie(controller.getZombies());
            double distance ;

            if (closeZombie != null) {
                distance = this.getPosition().distance(closeZombie.getPosition());
                Position x = new Position(closeZombie.getPosition().getX() - this.getPosition().getX(), closeZombie.getPosition().getY() - this.getPosition().getY());
                x.normalize();

                if (distance <= this.shootingRange) {
                    this.setDirection(x);
                    System.out.println(this.getName()+" changed direction to "+this.getDirection().toString());
                    state = SoldierState.SHOOTING;
                    System.out.println(this.getName() + " changed state to " + this.state.toString());
                    return;
                }
                else
                    state=SoldierState.SEARCHING;
                    System.out.println(this.getName()+" changed state to "+this.state.toString());
                    return;

            }
        }

        if (this.state == SoldierState.SHOOTING){
            Integer bulletNo = controller.getNewBullet();
            SimulationObject newBullet = new Bullet("Bullet"+bulletNo.toString(),this.getPosition(),100,this.getDirection());
            controller.getBullets().add(newBullet);
            System.out.println(this.getName()+" fired "+newBullet.getName() +" to direction "+newBullet.getDirection().toString());

            SimulationObject closerZombie = calculateCloserZombie(controller.getZombies());
            double distance = this.getPosition().distance(closerZombie.getPosition());
            if(distance<=this.shootingRange){
                state=SoldierState.AIMING;
                System.out.println(this.getName()+" changed state to "+this.state.toString());
     }
            else{
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(this.getName()+" changed direction to "+this.getDirection().toString());
                this.state=SoldierState.SEARCHING;
                System.out.println(this.getName()+" changed state to "+this.state.toString());

            }
        }


    }

    @Override
    public String getType() {
        return "soldier";
    }

    @Override
    public double getCollisionRange() {
        return collisionRange;
    }
}

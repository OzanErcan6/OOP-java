/**
 *I decleare the String "soldier" for every soldier object and "zombie" for every zombie
 * because at the beginning i put soldiers and zombies into separate lists.
 * other than that soldiers and zombies constructors and functions similar with each other
 * Also i can change collisionRange , starting state etc from constructor easily
 * To improve the code i could add bulletSpeed variable to the constructor instead of when creating bullet typing 100 for sniper for example.
 *
 */
public class RegularSoldier extends SimulationObject {
    private SoldierState state;
    private SoldierType type;
    private double collisionRange;
    private double shootingRange;

    public RegularSoldier(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name,position,5.0);
        this.state=SoldierState.SEARCHING;
        this.collisionRange=2.0;
        this.shootingRange=20.0;
        setType("soldier");
        this.type=SoldierType.REGULAR;
        this.setActive(true);
        this.setDirection(Position.generateRandomDirection(true));
    }

    @Override
    public void step(SimulationController controller) {
        if(!this.isActive()) {
            return;
        }


       /*   – Calculate the next position of the soldier with formula:
                    new_position = position + direction ∗ speed
            – If the position is out of bounds, change direction to random value
            – If the position is not out of bounds, change soldier position to the new_position.*/
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

        /*– Calculate the euclidean distance (3.1.2) to the closest zombie.
          – If the distance is shorter than or equal to the shooting range of the soldier, change state to AIMING.*/

        SimulationObject closeZombie = this.calculateCloserZombie(controller.getZombies());
        double distance = -1;
        Position position;
        if (closeZombie != null) {
            distance = this.getPosition().distance(closeZombie.getPosition());
            if (distance != -1 && distance <= this.shootingRange) {
                state = SoldierState.AIMING;
                System.out.println(this.getName()+" changed state to "+this.state.toString());
            }
        }
        return;
        }
        ///////////////////AIMING/////////////////////////////////
        /*– Calculate the euclidean distance (3.1.2) to the closest zombie.
          – If the distance is shorter than or equal to the shooting range of the soldier, change soldier
            direction to zombie and change state to SHOOTING. Do not forget to normalize the direction.
            If not, change state to SEARCHING*/
        if (this.state == SoldierState.AIMING) {
            SimulationObject closeZombie = this.calculateCloserZombie(controller.getZombies());
            double distance ;

            if (closeZombie != null) {
                distance = this.getPosition().distance(closeZombie.getPosition());
                Position x = new Position(closeZombie.getPosition().getX() - this.getPosition().getX(), closeZombie.getPosition().getY() - this.getPosition().getY());
                x.normalize();

                //change soldier direction to zombie, change state to SHOOTING and return
                if (distance <= this.shootingRange) {
                    this.setDirection(x);
                    System.out.println(this.getName()+" changed direction to "+this.getDirection().toString());
                    state = SoldierState.SHOOTING;
                    System.out.println(this.getName()+" changed state to "+this.state.toString());

                }
            }
            else{
                state = SoldierState.SEARCHING;
                System.out.println(this.getName()+" changed state to "+this.state.toString());

            }
            return;
        }

/*– Create a bullet. As mentioned before, bullet’s position and direction should be same as sol-
dier’s. Speed depends on the soldier which for RegularSoldier is 40.0. Add the bullet to the
simulation after all step function are executed.
– Calculate the euclidean distance (3.1.2) to the closest zombie.
– If the distance is shorter than or equal to the shooting range of the soldier, change state to
AIMING.
– If not, randomly change soldier direction and change state to SEARCHING.*/
        if (this.state == SoldierState.SHOOTING){
            Integer bulletNo = controller.getNewBullet();
            SimulationObject newBullet = new Bullet("Bullet"+bulletNo.toString(),this.getPosition(),40,this.getDirection());
            controller.getBullets().add(newBullet);
            System.out.println(this.getName()+" fired "+newBullet.getName() +" to direction "+newBullet.getDirection().toString());

            SimulationObject closerZombie = calculateCloserZombie(controller.getZombies());
            double distance = this.getPosition().distance(closerZombie.getPosition());
            if(distance<=this.shootingRange){
                this.state=SoldierState.AIMING;
                System.out.println(this.getName()+" changed state to "+this.state.toString());
                return;}
            else{
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(this.getName()+" changed direction to "+this.getDirection().toString());
                this.state=SoldierState.SEARCHING;
                System.out.println(this.getName()+" changed state to "+this.state.toString());
                return;
            }
        }
    }

    @Override
    public String getType() {
        return "soldier";
    }


    public SoldierType getSoldierType() {
        return this.type;
    }


    @Override
    public double getCollisionRange() {
        return collisionRange;
    }
}

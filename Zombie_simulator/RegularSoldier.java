/**
 *I decleare the String "soldier" for every soldier object and "zombie" for every zombie
 * because at the beginning i put soldiers and zombies into separate lists.
 * other than that soldiers and zombies constructors and functions similar with each other
 * Also i can change collisionRange , starting state etc from constructor easily
 * To improve the code i could add bulletSpeed variable to the constructor instead of when creating bullet typing 100 for sniper for example.
 *
 */
public interface ZombieInteraction {
    SimulationObject calculateClosestZombie(List<SimulationObject> zombies, Position soldierPosition);
}

public class RegularSoldier extends SimulationObject {
    private SoldierState state;
    private SoldierType type;
    private double collisionRange;
    private double shootingRange;
    private ZombieInteraction zombieInteraction;

    public RegularSoldier(String name, Position position, ZombieInteraction zombieInteraction) {
        super(name, position, 5.0);
        this.state = SoldierState.SEARCHING;
        this.collisionRange = 2.0;
        this.shootingRange = 20.0;
        setType("soldier");
        this.type = SoldierType.REGULAR;
        this.setActive(true);
        this.setDirection(Position.generateRandomDirection(true));
        this.zombieInteraction = zombieInteraction;
    }

    @Override
    public void step(SimulationController controller) {
        if (!this.isActive()) {
            return;
        }

        if (controller.isFinished()) {
            return;
        }

        switch (state) {
            case SEARCHING:
                handleSearchingState(controller);
                break;
            case AIMING:
                handleAimingState(controller);
                break;
            case SHOOTING:
                handleShootingState(controller);
                break;
        }
    }

    private void handleSearchingState(SimulationController controller) {
        Position newPosition = calculateNewPosition();

        if (!isInBound(controller, newPosition)) {
            changeDirection();
            System.out.println(this.getName() + " changed direction to " + this.getDirection().toString());
        }

        if (isInBound(controller, newPosition)) {
            setPosition(newPosition);
            System.out.println(this.getName() + " moved to " + this.getPosition().toString());
        }

        SimulationObject closestZombie = zombieInteraction.calculateClosestZombie(controller.getZombies(), getPosition());

        if (closestZombie != null) {
            double distance = this.getPosition().distance(closestZombie.getPosition());
            if (distance != -1 && distance <= this.shootingRange) {
                state = SoldierState.AIMING;
                System.out.println(this.getName() + " changed state to " + this.state.toString());
            }
        }
    }

    private void handleAimingState(SimulationController controller) {
        SimulationObject closestZombie = zombieInteraction.calculateClosestZombie(controller.getZombies(), getPosition());

        if (closestZombie != null) {
            double distance = this.getPosition().distance(closestZombie.getPosition());
            Position directionToZombie = new Position(closestZombie.getPosition().getX() - this.getPosition().getX(), closestZombie.getPosition().getY() - this.getPosition().getY());
            directionToZombie.normalize();

            if (distance <= this.shootingRange) {
                this.setDirection(directionToZombie);
                System.out.println(this.getName() + " changed direction to " + this.getDirection().toString());
                state = SoldierState.SHOOTING;
                System.out.println(this.getName() + " changed state to " + this.state.toString());
            } else {
                state = SoldierState.SEARCHING;
                System.out.println(this.getName() + " changed state to " + this.state.toString());
            }
        } else {
            state = SoldierState.SEARCHING;
            System.out.println(this.getName() + " changed state to " + this.state.toString());
        }
    }

    private void handleShootingState(SimulationController controller) {
        Integer bulletNo = controller.getNewBullet();
        SimulationObject newBullet = new Bullet("Bullet" + bulletNo.toString(), this.getPosition(), 40, this.getDirection());
        controller.getBullets().add(newBullet);
        System.out.println(this.getName() + " fired " + newBullet.getName() + " to direction " + newBullet.getDirection().toString());

        SimulationObject closestZombie = zombieInteraction.calculateClosestZombie(controller.getZombies(), getPosition());
        double distance = this.getPosition().distance(closestZombie.getPosition());

        if (distance <= this.shootingRange) {
            this.state = SoldierState.AIMING;
            System.out.println(this.getName() + " changed state to " + this.state.toString());
        } else {
            changeDirection();
            System.out.println(this.getName() + " changed direction to " + this.getDirection().toString());
            this.state = SoldierState.SEARCHING;
            System.out.println(this.getName() + " changed state to " + this.state.toString());
        }
    }

    private Position calculateNewPosition() {
        double newX = this.getPosition().getX() + this.getDirection().getX() * this.getSpeed();
        double newY = this.getPosition().getY() + this.getDirection().getY() * this.getSpeed();
        return new Position(newX, newY);
    }

    private void changeDirection() {
        this.setDirection(Position.generateRandomDirection(true));
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

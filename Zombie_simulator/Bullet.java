import java.util.Iterator;

/**
 *Bullet class extends Simulation Object
 *Because we need an object to represent bullet and there are some common functions and variables.
 *
 */

public interface ZombieInteraction {
    boolean checkCollisionWithZombies(Position bulletPosition, double collisionRange);
}

public class Bullet extends SimulationObject {
    private ZombieInteraction zombieInteraction;

    public Bullet(String name, Position position, double speed, Position direction, ZombieInteraction zombieInteraction) {
        super(name, position, speed);
        this.setDirection(direction);
        setType("bullet");
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

        for (int x = 0; x <= this.getSpeed(); x++) {
            if (!this.isInBound(controller, this.getPosition())) {
                System.out.println(this.getName() + " moved out of bounds");
                this.setActive(false);
                return;
            }

            if (checkCollisionWithZombies()) {
                return;
            }

            updatePosition();
            x++;
        }

        System.out.println(this.getName() + " dropped to the ground at " + this.getPosition().toString());
        this.setActive(false);
    }

    private boolean checkCollisionWithZombies() {
        return zombieInteraction.checkCollisionWithZombies(this.getPosition(), this.getCollisionRange());
    }

    private void updatePosition() {
        Position newPos = new Position(this.getPosition().getX() + this.getDirection().getX(), this.getPosition().getY() + this.getDirection().getY());
        this.setPosition(newPos);
    }

    public double getCollisionRange() {
        return -1;
    }
}

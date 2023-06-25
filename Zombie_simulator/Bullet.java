import java.util.Iterator;

/**
 *Bullet class extends Simulation Object
 *Because we need an object to represent bullet and there are some common functions and variables.
 *
 */

public class Bullet extends SimulationObject {
    public Bullet(String name, Position position, double speed, Position direction) {
        super(name, position, speed);
        this.setDirection(direction);
        setType("bullet");
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

            if (checkCollisionWithZombies(controller)) {
                return;
            }

            updatePosition();
            x++;
        }

        System.out.println(this.getName() + " dropped to the ground at " + this.getPosition().toString());
        this.setActive(false);
    }

    private boolean checkCollisionWithZombies(SimulationController controller) {
        Iterator<SimulationObject> iterator = controller.getZombies().iterator();

        while (iterator.hasNext()) {
            SimulationObject zombie = iterator.next();

            if (this.getPosition().distance(zombie.getPosition()) <= zombie.getCollisionRange() && zombie.isActive()) {
                System.out.println(this.getName() + " hit " + zombie.getName());
                zombie.setActive(false);
                this.setActive(false);
                return true;
            }
        }

        return false;
    }

    private void updatePosition() {
        Position newPos = new Position(this.getPosition().getX() + this.getDirection().getX(), this.getPosition().getY() + this.getDirection().getY());
        this.setPosition(newPos);
    }

    public double getCollisionRange() {
        return -1;
    }
}


import java.util.Iterator;
import java.util.Vector;

/**
 *in StepAll function i used 3 loops for bullet, zombie and soldier lists
 * firstly i checked if the object is still active if not removed.
 * Then it calls step function for every object with an order.
 *
 * Also i save the bullet number in the controller
 *
 * Furthermore i can add new soldier or zombie classes to the program with implementing common functions
 *
 */
public class SimulationController {
    private final double height;
    private final double width;
    private Vector<SimulationObject> soldiers ;
    private Vector<SimulationObject> zombies ;
    private Vector<SimulationObject> bullets ;
    private int bulletNo;

    public SimulationController(double width, double height) {
        this.width = width;
        this.height = height;
        soldiers = new Vector<SimulationObject>();
        zombies = new Vector<SimulationObject>();
        bullets = new Vector<SimulationObject>();
        bulletNo=-1;
    }
    
    double getHeight() {
        return height;
    }

    double getWidth() {
        return width;
    }

    //Simulates the objects in the given simulation. There is no specific order of simulation.
    //You can simulate them in any order you want. Please note that any simulation object added
    //or removed from the simulation during iteration of objects will be problematic. Therefore
    //simulation objects should be added after all step functions are called. Similarly removed
    //objects should be removed after all step functions are called. Moreover, if an object is removed
    //prior to calling their step function, they should not be simulated.
    //For example: If a bullet hits a zombie before zombie’s step function is called, zombie should
    //not be simulated and removed after all other object simulations are finished.
    //önce bullet var mı bak
    void stepAll() {

        //removing inactive objects
        Iterator<SimulationObject> iterator = bullets.iterator();
        Iterator<SimulationObject> iterator2 = zombies.iterator();
        Iterator<SimulationObject> iterator3= soldiers.iterator();

            while (iterator.hasNext()) {
                SimulationObject b = iterator.next();
                if (!b.isActive()) {
                    iterator.remove();
                }
                if (b.isActive())
                    b.step(this);
            }
            while (iterator2.hasNext()) {
                if (isFinished()) return;
                SimulationObject b = iterator2.next();
                if (!b.isActive()) iterator2.remove();
                if (b.isActive())
                    b.step(this);
            }

            while (iterator3.hasNext()) {
                if (isFinished()) return;
                SimulationObject b = iterator3.next();
                if (!b.isActive()) iterator3.remove();
                if (b.isActive())
                    b.step(this);
            }



    }
    //Adds the simulation object given in the parameter to the simulation.
    //You can store the simulation object in any way you want. You can store different simulation
    //objects in different lists or arrays by determining their class using Reflection or store them in
    //the same list.
    void addSimulationObject(SimulationObject obj) {
        if(obj.getType().equals("zombie"))  {
            zombies.add(obj);
            return;
        }
        if(obj.getType().equals("soldier")){
            soldiers.add(obj);
        }
    }
    //Removes the simulation object given in the parameter from the
    //simulation.
    public void removeSimulationObject(SimulationObject obj) {
        if(obj.getType().equals("soldier") && !soldiers.isEmpty())
                soldiers.remove(obj);

        if(obj.getType().equals("zombie") && !zombies.isEmpty())
                zombies.remove(obj);

    }
    //Checks whether there are only zombies or soldiers left in the simulation. If it is,
    //should return true. If there is no zombie or soldier left, should also return true.
    boolean isFinished() {
        if(zombies.isEmpty()){
            zombies.clear();
            return true;
        }
        if(soldiers.isEmpty()&&bullets.isEmpty()){
            soldiers.clear();
            return true;
        }
        return false;
    }

    Vector<SimulationObject> getBullets() { return this.bullets; }
    Vector<SimulationObject> getZombies() { return this.zombies; }
    Vector<SimulationObject> getSoldiers() { return this.soldiers; }


    int getNewBullet(){
        bulletNo=bulletNo+1;
        return bulletNo;
    }
}

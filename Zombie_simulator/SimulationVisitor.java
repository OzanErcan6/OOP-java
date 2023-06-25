public abstract class SimulationObject {
    // Métodos y propiedades comunes para todos los objetos de simulación

    public abstract void accept(SimulationObjectVisitor visitor);
}

public interface SimulationObjectVisitor {
    void visit(Zombie zombie);
    void visit(Soldier soldier);
}

public class SimulationController implements SimulationObjectVisitor {
    // Resto de propiedades y métodos de la clase

    void addSimulationObject(SimulationObject obj) {
        obj.accept(this);
    }

    @Override
    public void visit(Zombie zombie) {
        zombies.add(zombie);
    }

    @Override
    public void visit(Soldier soldier) {
        soldiers.add(soldier);
    }

    // Resto de métodos de la clase
}

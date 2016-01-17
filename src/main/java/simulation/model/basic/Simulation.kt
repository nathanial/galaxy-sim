package simulation.model.basic

import com.google.common.eventbus.EventBus
import org.pcollections.TreePVector
import util.map
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class TickEvent(val systems: TreePVector<SolarSystem>) {

}

class Simulation(val bus: EventBus, val systemCount: Int, val planetsRange: ClosedRange<Int>) {
    private val random = Random()
    public val systems = createSystems()

    public fun tick(){
        systems.set(TreePVector.from(map(systems.get(), { s ->
            s.rotatePlanets(degrees=1.0)
        })))
        bus.post(TickEvent(systems.get()))
    }

    private fun createSystems(): AtomicReference<TreePVector<SolarSystem>> {
        var i = 0;
        val systems = ArrayList<SolarSystem>()

        while(i < systemCount){
            val asteroids = TreePVector.empty<Asteroid>()
            val coords = Coordinates(
                random.nextGaussian() * 1000 + 500,
                random.nextGaussian() * 1000 + 500
            )
            val planets = generatePlanets()
            systems.add(SolarSystem("System " + i, Star(), coords, planets, asteroids))
            i++;
        }
        //removeOverlapping(systems)
        return AtomicReference(TreePVector.from(systems))
    }

    private fun generatePlanets(): TreePVector<Planet> {
        val planetCount = random.nextInt((planetsRange.endInclusive - planetsRange.start) + 1) + planetsRange.start
        val planets = ArrayList<Planet>()
        var i = 0;
        var radius = 1.0;
        while(i < planetCount){
            radius += random.nextDouble() + 0.5;
            val angle = random.nextDouble() * 360
            val kind = randomPlanetKind()
            val size = kind.size(random)
            planets.add(Planet(kind, radius, size, angle, Math.max(Math.random(),0.5) / radius))
            i += 1
        }
        return TreePVector.from(planets)
    }
}
package simulation.model.basic

import org.pcollections.TreePVector
import java.util.*

class Simulation(val systemCount: Int, val planetsRange: ClosedRange<Int>) {
    private val random = Random()
    public val systems = createSystems()

    public fun step(){

    }

    private fun createSystems(): TreePVector<SolarSystem> {
        var i = 0;
        val systems = ArrayList<SolarSystem>()

        while(i < systemCount){
            val asteroids = TreePVector.empty<Asteroid>()
            val coords = Coordinates(
                random.nextGaussian() * 300 + 500,
                random.nextGaussian() * 300 + 500
            )
            val planets = generatePlanets(coords)
            systems.add(SolarSystem("System " + i, Star(), coords, planets, asteroids))
            i++;
        }
        return TreePVector.from(systems)
    }

    private fun generatePlanets(systemCoordinates: Coordinates): TreePVector<Planet> {
        val planetCount = random.nextInt((planetsRange.endInclusive - planetsRange.start) + 1) + planetsRange.start
        val planets = ArrayList<Planet>()
        var i = 0;
        while(i < planetCount){
            val offset = Coordinates(random.nextDouble() * 3 - 1, random.nextDouble() * 3 - 1);
            planets.add(Planet(offset))
            i += 1
        }
        return TreePVector.from(planets)
    }
}
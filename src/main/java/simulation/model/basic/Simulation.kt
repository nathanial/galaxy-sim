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
            val planets = generatePlanets()
            systems.add(SolarSystem("System " + i, Star(), coords, planets, asteroids))
            i++;
        }
        return TreePVector.from(systems)
    }

    private fun generatePlanets(): TreePVector<Planet> {
        val planetCount = random.nextInt((planetsRange.endInclusive - planetsRange.start) + 1) + planetsRange.start
        val planets = ArrayList<Planet>()
        var i = 0;
        while(i < planetCount){
            val radius = random.nextInt(3) + 1
            val angle = Math.toRadians(random.nextDouble() * 360)
            val offset = Coordinates(Math.cos(angle) * radius, Math.sin(angle) * radius)
            planets.add(Planet(offset))
            i += 1
        }
        return TreePVector.from(planets)
    }
}
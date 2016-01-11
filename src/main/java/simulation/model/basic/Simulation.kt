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
                random.nextGaussian() * 1000 + 500,
                random.nextGaussian() * 1000 + 500
            )
            val planets = generatePlanets()
            systems.add(SolarSystem("System " + i, Star(), coords, planets, asteroids))
            i++;
        }
        //removeOverlapping(systems)
        return TreePVector.from(systems)
    }



    private fun generatePlanets(): TreePVector<Planet> {
        val planetCount = random.nextInt((planetsRange.endInclusive - planetsRange.start) + 1) + planetsRange.start
        val planets = ArrayList<Planet>()
        var i = 0;
        var radius = 1.0;
        while(i < planetCount){
            radius += random.nextDouble() + 0.5;
            val angle = random.nextDouble() * 360
            planets.add(Planet(radius, angle))
            i += 1
        }
        return TreePVector.from(planets)
    }
}
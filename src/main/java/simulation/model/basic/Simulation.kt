package simulation.model.basic

import org.pcollections.TreePVector
import java.util.*

class Simulation(val systemCount: Int, val planetsRange: ClosedRange<Int>) {
    public val systems = createSystems()

    public fun step(){

    }

    private fun createSystems(): TreePVector<SolarSystem> {
        var i = 0;
        val systems = ArrayList<SolarSystem>()
        val random = Random()
        while(i < systemCount){
            val planets = TreePVector.empty<Planet>()
            val asteroids = TreePVector.empty<Asteroid>()
            val coords = GalacticCoordinates(
                    Math.floor(random.nextGaussian() * 100 + 500).toInt(),
                    Math.floor(random.nextGaussian() * 100 + 500).toInt()
            )
            systems.add(SolarSystem("System " + i, Star(), coords, planets, asteroids))
            i++;
        }
        return TreePVector.from(systems)
    }
}
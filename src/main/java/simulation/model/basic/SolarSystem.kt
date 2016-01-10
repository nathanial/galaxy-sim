package simulation.model.basic

import org.pcollections.TreePVector
import java.awt.Color

class SolarSystem(val name: String,
                  val star: Star,
                  val galacticCoordinates: GalacticCoordinates,
                  val planets: TreePVector<Planet>,
                  val asteroids: TreePVector<Asteroid>) {

}
package simulation.model.basic

import org.pcollections.TreePVector
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D

class SolarSystem(val name: String,
                  val star: Star,
                  val galacticCoordinates: Coordinates,
                  val planets: TreePVector<Planet>,
                  val asteroids: TreePVector<Asteroid>) {

    enum class PaintOptions {
        ALL,
        HIDE_PLANETS
    }

    fun paint(g2d: Graphics2D, options: PaintOptions = PaintOptions.ALL){
        val oldTransform = AffineTransform(g2d.transform)
        g2d.translate(galacticCoordinates.x, galacticCoordinates.y)

        star.paint(g2d)

        if(options == PaintOptions.ALL){
            for(planet in planets){
                planet.paint(g2d)
            }
        }

        g2d.transform = oldTransform
    }
}
package simulation.model.basic

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.util.*

enum class PlanetKind {
    EARTH {
        override fun getColor() = Color(45,136,45)
    },

    DESERT {
        override fun getColor() = Color(233,193,132)
    },

    OCEANIC {
        override fun getColor() = Color(43,75,111)
    },

    VOLCANIC {
        override fun getColor() = Color(107, 11, 11)
    },

    BARREN {
        override fun getColor() = Color(107,69,11)
    },

    ARCTIC {
        override fun getColor() = Color(171,189,209)
    };

    abstract fun getColor(): Color
}

fun randomPlanetKind():PlanetKind {
    return PlanetKind.values[Random().nextInt(PlanetKind.values.size)]
}

class Planet(val kind: PlanetKind,
             val orbitRadius: Double,
             val angle: Double,
             val speed: Double) {

    public fun rotate(degrees: Double):Planet {
        return Planet(kind, orbitRadius, angle + degrees, speed);
    }

    fun paint(g2d: Graphics2D){
        this.paintOrbit(g2d)

        val x = orbitRadius * Math.cos(Math.toRadians(angle))
        val y = orbitRadius * Math.sin(Math.toRadians(angle))

        val oldTransform = AffineTransform(g2d.transform)
        g2d.translate(x, y)
        g2d.color = kind.getColor()
        val radius = 0.1
        val planetShape = Ellipse2D.Double(
            0 - radius,
            0 - radius,
            2.0 * radius,
            2.0 * radius
        )
        g2d.fill(planetShape)

        g2d.stroke = BasicStroke(0.01f)
        g2d.color = Color.white
        g2d.draw(planetShape)
        g2d.transform = oldTransform
    }

    fun paintOrbit(g2d: Graphics2D){
        val orbitShape = Ellipse2D.Double(
            0 - orbitRadius,
            0 - orbitRadius,
            2 * orbitRadius,
            2 * orbitRadius
        )
        g2d.stroke = BasicStroke(0.025f)
        g2d.color = Color(1.0f, 1.0f, 1.0f, 0.1f)
        g2d.draw(orbitShape)
    }

}
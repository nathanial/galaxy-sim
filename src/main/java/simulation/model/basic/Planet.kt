package simulation.model.basic

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D

class Planet(val coordinates: Coordinates) {

    fun paint(g2d: Graphics2D){
        this.paintOrbit(g2d)

        val oldTransform = AffineTransform(g2d.transform)
        g2d.translate(coordinates.x, coordinates.y)
        g2d.color = Color.green
        val radius = 0.1
        val planetShape = Ellipse2D.Double(
            0 - radius,
            0 - radius,
            2.0 * radius,
            2.0 * radius
        )
        g2d.fill(planetShape)
        g2d.transform = oldTransform
    }

    fun paintOrbit(g2d: Graphics2D){
        val (x,y) = coordinates
        val radius = Math.sqrt(x * x + y * y)
        val orbitShape = Ellipse2D.Double(
            0 - radius,
            0 - radius,
            2 * radius,
            2 * radius
        )
        g2d.stroke = BasicStroke(0.05f)
        g2d.color = Color.white
        g2d.draw(orbitShape)
    }

}
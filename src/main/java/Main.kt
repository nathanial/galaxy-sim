package main

import org.pcollections.TreePVector
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.geom.Ellipse2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel

class Coords(val x:Int, val y: Int) {

}

class Planet(val coords: Coords) {

}

class Asteroid(val coords: Coords) {

}

class SolarSystem(val name: String,
                  val starColor: Color,
                  val coords: Coords,
                  val planets: TreePVector<Planet>,
                  val asteroids: TreePVector<Asteroid>) {

}

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
            val coords = Coords(Math.floor(random.nextGaussian() * 100 + 500).toInt(), Math.floor(random.nextGaussian() * 100 + 500).toInt())
            val randColor = Color((Math.random() * 255).toInt(), (Math.random() * 255).toInt(), (Math.random() * 255).toInt())
            systems.add(SolarSystem("System " + i, randColor, coords, planets, asteroids))
            i++;
        }
        return TreePVector.from(systems)
    }
}

class GalaxyView(val simulation: Simulation) : JPanel() {
    init {
        background = Color.white
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        for(system in simulation.systems){
            g2d.color = system.starColor
            val radius = 1
            val theCircle = Ellipse2D.Double(system.coords.x.toDouble() - radius, system.coords.y.toDouble() - radius, 2.0 * radius, 2.0 * radius);
            g2d.fill(theCircle);
        }
    }

    override fun getPreferredSize(): Dimension? {
        return Dimension(1000,1000)
    }
}

class SimulationView(val simulation: Simulation) : JPanel() {
    init {
        background = Color.white
        add(GalaxyView(simulation))
    }
}


class SimulationFrame(val simulation: Simulation) : JFrame("Galactic Simulation") {

    init {
        isResizable = false
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        background = Color.white

        add(SimulationView(simulation))
        pack()
        isVisible = true
    }
}

fun main(args: Array<String>){
    val sim = Simulation(systemCount=1000, planetsRange=0..10)
    SimulationFrame(sim)
}

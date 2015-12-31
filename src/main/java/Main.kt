package main

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import org.pcollections.TreePVector
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.geom.Ellipse2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel


class ZoomEvent(val zoom: Int) {

}

class GalacticCoordinates(val x:Int, val y: Int) {

}

class Planet(val galacticCoordinates: GalacticCoordinates) {

}

class Asteroid(val galacticCoordinates: GalacticCoordinates) {

}

class SolarSystem(val name: String,
                  val starColor: Color,
                  val galacticCoordinates: GalacticCoordinates,
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
            val coords = GalacticCoordinates(Math.floor(random.nextGaussian() * 100 + 500).toInt(), Math.floor(random.nextGaussian() * 100 + 500).toInt())
            val randColor = Color((Math.random() * 255).toInt(), (Math.random() * 255).toInt(), (Math.random() * 255).toInt())
            systems.add(SolarSystem("System " + i, randColor, coords, planets, asteroids))
            i++;
        }
        return TreePVector.from(systems)
    }
}

class GalaxyView(val simulation: Simulation) : JPanel() {
    var zoom = 1.0

    init {
        background = Color.white
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        println("Paint Me: " + zoom)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g2d.scale(zoom, zoom)
        g2d.translate(-500,-500)
        for(system in simulation.systems){
            g2d.color = system.starColor
            val radius = 1
            val theCircle = Ellipse2D.Double(system.galacticCoordinates.x.toDouble() - radius, system.galacticCoordinates.y.toDouble() - radius, 2.0 * radius, 2.0 * radius);
            g2d.fill(theCircle);
        }
    }

    override fun getPreferredSize(): Dimension? {
        return Dimension(1000,1000)
    }

    @Subscribe
    public fun onZoom(e: ZoomEvent){
        if(e.zoom > 0){
            zoom *= 0.9
        } else {
            zoom *= 1.1
        }
        println("Change Zoom: " +  zoom)
        repaint()
    }
}


class SimulationView(val simulation: Simulation, val bus: EventBus) : JPanel() {
    init {
        background = Color.white
        val galaxyView = GalaxyView(simulation)
        bus.register(galaxyView)
        add(galaxyView)
        addMouseWheelListener {
            val zoom = it.wheelRotation * it.scrollAmount
            bus.post(ZoomEvent(zoom))
        }
    }
}


class SimulationFrame(val simulation: Simulation, val bus: EventBus) : JFrame("Galactic Simulation") {

    init {
        isResizable = false
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        background = Color.white

        add(SimulationView(simulation, bus))
        pack()
        isVisible = true
    }
}

fun main(args: Array<String>){
    val sim = Simulation(systemCount=1000, planetsRange=0..10)
    val bus = EventBus("Simulation Bus")
    SimulationFrame(sim, bus)
}

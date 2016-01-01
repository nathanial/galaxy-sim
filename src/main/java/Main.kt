package main

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import org.pcollections.TreePVector
import java.awt.*
import java.awt.event.MouseMotionAdapter
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel

data class XY(val x: Double, val y: Double)

class ZoomEvent(val zoom: Int) {

}

class MouseEvent(val x: Int, val y: Int){

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
    var lastX = 0.0
    var lastY = 0.0
    var lastTransform = AffineTransform()

    init {
        background = Color.white
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

        val pt = Point2D.Double()
        lastTransform.inverseTransform(Point2D.Double(lastX, lastY), pt)

        println("Point: " + pt + ", Last" + XY(lastX, lastY))
        println("")

        lastTransform.translate(pt.x, pt.y)
        lastTransform.scale(zoom, zoom)
        lastTransform.translate(-pt.x, -pt.y)

        g2d.transform = lastTransform.clone() as AffineTransform

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
            zoom = 0.9
        } else {
            zoom = 1.1
        }
        repaint()
    }

    @Subscribe
    public fun onMouseMove(e: MouseEvent){
        lastX = e.x.toDouble()
        lastY = e.y.toDouble()
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
        addMouseMotionListener(object: MouseMotionAdapter() {
            override fun mouseMoved(e: java.awt.event.MouseEvent?) {
                if(e != null){
                    bus.post(main.MouseEvent(e.x,  e.y))
                }
            }
        })
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

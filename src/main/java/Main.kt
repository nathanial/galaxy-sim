package main

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import org.pcollections.TreePVector
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JLayeredPane
import javax.swing.JPanel
import javax.swing.event.MouseInputAdapter

data class XY(val x: Double, val y: Double)

data class MouseZoomEvent(val zoom: Int)
data class MouseDownEvent(val x: Int, val y: Int)
data class MouseUpEvent(val x: Int, val y: Int)
data class MouseMoveEvent(val x: Int, val y: Int)

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
    var isDragging = false
    var dragStart = XY(0.0,0.0)

    init {
        isOpaque = false
        background = Color.white
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

        g2d.color = Color(0,0,0,100)
        g2d.fillRect(0,0,1000,1000)

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

    fun drag(x: Int, y: Int){
        lastTransform.translate((x - dragStart.x) / lastTransform.scaleX, (y - dragStart.y) / lastTransform.scaleY)
        dragStart = XY(x.toDouble(),y.toDouble())
        repaint()
    }

    fun zoom(factor: Double){
        val pt = Point2D.Double()
        lastTransform.inverseTransform(Point2D.Double(lastX, lastY), pt)
        lastTransform.translate(pt.x, pt.y)
        lastTransform.scale(factor, factor)
        lastTransform.translate(-pt.x, -pt.y)
        repaint()
    }

    @Subscribe
    public fun onZoom(e: MouseZoomEvent){
        if(e.zoom > 0){
            zoom(0.8)
        } else {
            zoom(1.2)
        }
    }

    @Subscribe
    public fun onMouseMove(e: MouseMoveEvent){
        if(isDragging){
            drag(e.x, e.y);
        }
        lastX = e.x.toDouble()
        lastY = e.y.toDouble()
    }

    @Subscribe
    public fun onMouseDown(e: MouseDownEvent){
        isDragging = true
        dragStart = XY(e.x.toDouble(), e.y.toDouble())
    }

    @Subscribe
    public fun onMouseUp(e: MouseUpEvent){
        isDragging = false
    }
}

class GalaxyBackground() : JPanel() {
    var image: BufferedImage = ImageIO.read(File("src/assets/images/Blue Stars.jpg"))

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.drawImage(image, 0, 0, null)
    }

    override fun getPreferredSize(): Dimension? {
        return Dimension(1000,1000)
    }

}


class SimulationView(val simulation: Simulation, val bus: EventBus) : JPanel() {
    init {
        background = Color.white
        val galaxyView = GalaxyView(simulation)
        var galaxyBackground = GalaxyBackground()
        bus.register(galaxyView)

        val pane = JLayeredPane()
        pane.preferredSize = Dimension(1000,1000)

        pane.add(galaxyBackground, Integer(50))
        pane.add(galaxyView, Integer(51))


        galaxyView.bounds = Rectangle(0, 0, 1000, 1000)
        galaxyBackground.bounds = Rectangle(0,0,1000,1000)

        add(pane)




        addMouseWheelListener {
            val zoom = it.wheelRotation * it.scrollAmount
            bus.post(MouseZoomEvent(zoom))
        }
        addMouseMotionListener(object: MouseMotionAdapter() {
            override fun mouseMoved(e: java.awt.event.MouseEvent?) {
                if(e != null){
                    bus.post(MouseMoveEvent(e.x,  e.y))
                }
            }

            override fun mouseDragged(e: MouseEvent?) {
                if(e != null){
                    bus.post(main.MouseMoveEvent(e.x, e.y))
                }
            }
        })
        addMouseListener(object: MouseInputAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                if(e != null){
                    bus.post(main.MouseDownEvent(e.x, e.y))
                }
            }

            override fun mouseReleased(e: MouseEvent?) {
                if(e != null){
                    bus.post(main.MouseUpEvent(e.x, e.y))
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

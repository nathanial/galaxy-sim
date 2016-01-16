package simulation

import com.google.common.eventbus.Subscribe
import main.*
import simulation.model.basic.Simulation
import simulation.model.basic.SolarSystem
import simulation.model.basic.TickEvent
import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D
import java.util.*
import javax.swing.JPanel
import javax.swing.SwingUtilities


class GalaxyView(val simulation: Simulation) : JPanel() {
    var lastX = 0.0
    var lastY = 0.0
    var lastTransform = AffineTransform()
    var isDragging = false
    var dragStart = XY(0.0, 0.0)

    init {
        isOpaque = false
        background = Color.white
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

        g2d.color = Color(0, 0, 0)
        g2d.fillRect(0,0,1000,1000)

        g2d.transform = lastTransform.clone() as AffineTransform

        for(system in this.findSystemsInView(g2d.clipBounds)){
            system.paint(g2d, pickRenderMode(g2d))
        }
    }

    override fun getPreferredSize(): Dimension? {
        return Dimension(1000, 1000)
    }

    fun findSystemsInView(clipBounds: Rectangle): Collection<SolarSystem> {
        val systems = ArrayList<SolarSystem>()
        for(system in this.simulation.systems.get()){
            if(clipBounds.contains(Point2D.Double(system.galacticCoordinates.x, system.galacticCoordinates.y))){
                systems.add(system)
            }
        }
        return systems;
    }

    fun drag(x: Int, y: Int){
        lastTransform.translate((x - dragStart.x) / lastTransform.scaleX, (y - dragStart.y) / lastTransform.scaleY)
        dragStart = XY(x.toDouble(), y.toDouble())
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

    fun pickRenderMode(g2d: Graphics2D): SolarSystem.PaintOptions {
        if(g2d.transform.scaleY > 6){
            return SolarSystem.PaintOptions.ALL
        } else {
            return SolarSystem.PaintOptions.HIDE_PLANETS
        }
    }

    @Subscribe
    public fun onZoom(e: MouseZoomEvent){
        SwingUtilities.invokeLater {
            if(e.zoom > 0){
                zoom(0.8)
            } else {
                zoom(1.2)
            }
        }
    }

    @Subscribe
    public fun onMouseMove(e: MouseMoveEvent){
        SwingUtilities.invokeLater {
            if(isDragging){
                drag(e.x, e.y);
            }
            lastX = e.x.toDouble()
            lastY = e.y.toDouble()
        }
    }

    @Subscribe
    public fun onMouseDown(e: MouseDownEvent){
        SwingUtilities.invokeLater {
            isDragging = true
            dragStart = XY(e.x.toDouble(), e.y.toDouble())
        }
    }

    @Subscribe
    public fun onMouseUp(e: MouseUpEvent){
        SwingUtilities.invokeLater {
            isDragging = false
        }
    }

    @Subscribe
    public fun onSimulationUpdated(e: TickEvent){
        SwingUtilities.invokeLater {
            repaint()
        }
    }
}

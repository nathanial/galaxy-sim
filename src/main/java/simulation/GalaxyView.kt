package simulation

import com.google.common.eventbus.Subscribe
import main.*
import simulation.model.basic.Simulation
import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D
import javax.swing.JPanel


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

        g2d.color = Color(0, 0, 0, 100)
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
        return Dimension(1000, 1000)
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

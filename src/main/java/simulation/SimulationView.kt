package simulation

import com.google.common.eventbus.EventBus
import main.*
import simulation.model.basic.Simulation
import java.awt.Color
import java.awt.Dimension
import java.awt.Rectangle
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.swing.JLayeredPane
import javax.swing.JPanel
import javax.swing.event.MouseInputAdapter

class SimulationView(val simulation: Simulation, val bus: EventBus) : JPanel() {
    init {
        background = Color.white
        val galaxyView = GalaxyView(simulation)
        var galaxyBackground = GalaxyBackground()
        bus.register(galaxyView)

        val pane = JLayeredPane()
        pane.preferredSize = Dimension(1000, 1000)

        pane.add(galaxyBackground, Integer(50))
        pane.add(galaxyView, Integer(51))

        galaxyView.bounds = Rectangle(0, 0, 1000, 1000)
        galaxyBackground.bounds = Rectangle(0, 0, 1000, 1000)

        add(pane)

        addMouseWheelListener {
            val zoom = it.wheelRotation * it.scrollAmount
            bus.post(MouseZoomEvent(zoom))
        }
        addMouseMotionListener(object: MouseMotionAdapter() {
            override fun mouseMoved(e: java.awt.event.MouseEvent?) {
                if(e != null){
                    bus.post(MouseMoveEvent(e.x, e.y))
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

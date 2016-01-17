package main

import com.google.common.eventbus.EventBus
import simulation.SimulationView
import simulation.model.basic.Simulation
import java.awt.Color
import javax.swing.JFrame

data class XY(val x: Double, val y: Double)

data class MouseZoomEvent(val zoom: Int)
data class MouseDownEvent(val x: Int, val y: Int)
data class MouseUpEvent(val x: Int, val y: Int)
data class MouseMoveEvent(val x: Int, val y: Int)


class SimulationFrame(val simulation: Simulation, val bus: EventBus) : JFrame("Galactic Simulation") {

    init {
        isResizable = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        background = Color.white

        add(SimulationView(simulation, bus))
        pack()
        isVisible = true
    }
}

fun main(args: Array<String>){
    val bus = EventBus("Simulation Bus")
    val sim = Simulation(bus, systemCount = 1000, planetsRange = 0..9)
    SimulationFrame(sim, bus)
    while(true){
        sim.tick();
        Thread.sleep(10);
    }
}

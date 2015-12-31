package main

import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.JFrame


class Simulation(val systems: Int, val planetsRange: ClosedRange<Int>) {
    init {

    }

    fun step(){

    }
}

class SimulationFrame(val simulation: Simulation) : Frame("Galactic Simulation") {
    init {
        this.size = Dimension(1000,1000)
        isVisible = true
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                dispose()
                System.exit(0)
            }
        })
    }

    override fun paint(g: Graphics?) {
        val g2d = g as Graphics2D
        g2d.color = Color.red
        g2d.drawLine(0,0,100,100)

    }
}

fun main(args: Array<String>){
    val sim = Simulation(systems=1000, planetsRange=0..10)
    SimulationFrame(sim)
}

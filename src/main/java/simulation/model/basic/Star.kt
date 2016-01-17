package simulation.model.basic

import com.sun.imageio.plugins.common.ImageUtil
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import kotlin.collections.count
import kotlin.collections.size

val rand = Random()
val starColors = ArrayList<Color>()
var colorsLoaded = false;

fun loadStarColors(){
    val file = File("src/assets/images/StarColorGradient.png")
    val image = ImageIO.read(file)
    var x = 0
    while(x < 200){
        val clr = image.getRGB(x,0)
        val red = (clr and 0x00ff0000) shr 16
        val green = (clr and 0x0000ff00) shr 8
        val blue = (clr and 0x000000ff)
        val color = Color(red, green, blue)
        starColors.add(color)
        x += 1;
    }
    colorsLoaded = true;
}

fun randomStarColor():Color{
    if(!colorsLoaded){
        loadStarColors();
    }
    return starColors[Math.abs(rand.nextInt()) % starColors.count()];
}

class Star {
    val color: Color
    val id: UUID
    val random: Random
    val phaseOffset: Double

    constructor(){
        this.color = randomStarColor()
        this.id = UUID.randomUUID()
        this.random = Random(id.toString().hashCode().toLong())
        this.phaseOffset = this.random.nextDouble()
    }

    constructor(color: Color, id: UUID){
        this.color = color
        this.id = id
        this.random = Random(id.node())
        this.phaseOffset = this.random.nextDouble()
    }

    fun paint(g2d: Graphics2D){
        g2d.color = calculateColor(g2d)
        val radius = calculateRadius(g2d)
        val star = Ellipse2D.Double(
                0 - radius,
                0 - radius,
                2.0 * radius,
                2.0 * radius
        )
        g2d.fill(star)
    }

    private fun calculateRadius(g2d:Graphics2D): Double{
        if(g2d.transform.scaleX < 2){
            return 1 / Math.pow(g2d.transform.scaleX, 0.99)
        } else {
            return 0.5
        }
    }

    private fun calculateColor(g2d:Graphics2D):Color {
        if(g2d.transform.scaleX < 0.3) {
            val coeff = Math.max(Math.pow(g2d.transform.scaleX * 1000.0, 1.5), 1000.0)
            val time = System.currentTimeMillis() / coeff + phaseOffset * 100;
            val ratio = Math.min(Math.max(g2d.transform.scaleX * 255.0, 100.0), 255.0);
            val transparency = ((255-ratio) * Math.abs(Math.sin(time))).toInt() + ratio.toInt()
            return Color(color.red, color.green, color.blue, transparency)
        } else {
            return color
        }
    }

}
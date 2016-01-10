package simulation.model.basic

import com.sun.imageio.plugins.common.ImageUtil
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.io.File
import java.util.*
import javax.imageio.ImageIO

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

    constructor(){
        this.color = randomStarColor()
    }

    constructor(color: Color){
        this.color = color
    }

    fun paint(g2d: Graphics2D){
        g2d.color = color
        val radius = 0.5
        val star = Ellipse2D.Double(
                0 - radius,
                0 - radius,
                2.0 * radius,
                2.0 * radius
        )
        g2d.fill(star)
    }

}
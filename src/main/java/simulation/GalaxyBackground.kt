package simulation

import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JPanel


class GalaxyBackground() : JPanel() {
    var image: BufferedImage = ImageIO.read(File("src/assets/images/The-Dancing-Nebula.jpg"))

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.drawImage(image, 0, 0, null)
    }

    override fun getPreferredSize(): Dimension? {
        return Dimension(1000, 1000)
    }

}

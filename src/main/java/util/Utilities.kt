package util

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.util.*

fun <T> map(list: Collection<T>, func: (x: T) -> T): Collection<T> {
    val coll = ArrayList<T>()
    for(item in list){
        coll.add(func(item))
    }
    return coll;
}

fun paintToImage(width:Int, height:Int, func: (g2d: Graphics2D) -> Unit): BufferedImage {
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g2 = image.createGraphics()
    func(g2)
    g2.dispose()
    return image
}

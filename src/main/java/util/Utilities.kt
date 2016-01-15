package util

import simulation.model.basic.SolarSystem
import java.util.*

fun <T> map(list: Collection<T>, func: (x: T) -> T): Collection<T> {
    val coll = ArrayList<T>()
    for(item in list){
        coll.add(func(item))
    }
    return coll;
}
/**
 * Created by nathanial on 1/15/2016.
 */

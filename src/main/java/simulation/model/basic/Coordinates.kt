package simulation.model.basic

data class Coordinates(val x:Double, val y: Double){
    public fun plus(other: Coordinates): Coordinates {
        return Coordinates(x + other.x, y + other.y)
    }
}
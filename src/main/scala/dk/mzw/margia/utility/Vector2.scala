package dk.mzw.margia.utility

case class Vector2(
    x : Int,
    y : Int
) {
    def +(dx : Int, dy : Int) = Vector2(x = x + dx, y = y + dy)
    def +(v : Vector2) = Vector2(x = x + v.x, y = y + v.y)
    def -(v : Vector2) = Vector2(x = x - v.x, y = y - v.y)
    def *(a : Int) = Vector2(x = x * a, y = y * a)
    lazy val length : Double = Math.sqrt(x*x + y*y)
    lazy val angle : Double = Math.atan2(y, x)
}
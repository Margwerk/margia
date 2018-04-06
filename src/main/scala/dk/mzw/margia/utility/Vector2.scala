package dk.mzw.margia.utility

case class Vector2(
    x : Double,
    y : Double
) {
    def +(dx : Double, dy : Double) = Vector2(x = x + dx, y = y + dy)
    def +(v : Vector2) = Vector2(x = x + v.x, y = y + v.y)
    def -(v : Vector2) = Vector2(x = x - v.x, y = y - v.y)
    def *(a : Double) = Vector2(x = x * a, y = y * a)
    lazy val length : Double = Math.sqrt(x*x + y*y)
    lazy val unit : Vector2 = if(length > 0) * (1 / length) else this
    lazy val angle : Double = Math.atan2(y, x)
}
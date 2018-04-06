package dk.mzw.margia.utility

case class Rectangle(
    // The lower left corner
    p1 : Vector2,

    // The upper right corner
    p2 : Vector2
) {
    def width = p2.x - p1.x
    def height = p2.y - p1.y
    def area = width * height
}
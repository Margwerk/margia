package dk.mzw.margia

import dk.mzw.margia.utility.{Rectangle, Vector2}

class GenerateDungeon(boundingBox : Rectangle) {

    /**
      * @param minCoverage the percent of the boundingBox's area that should be covered with rooms
      */
    def makeRooms(minCoverage : Double) : List[Rectangle] = makeRoomsRecursive(minCoverage, 0)

    private def makeRoomsRecursive(minCoverage : Double, currentCoverage : Double) : List[Rectangle] = {
        if(currentCoverage >= minCoverage) List()
        else {
            val room = makeRoom()
            room :: makeRoomsRecursive(minCoverage, currentCoverage + room.area.toDouble / boundingBox.area)
        }
    }

    /**
      *  Make a random rectangle contained in boundingBox
      */
    def makeRoom() = {
        val minSize = 2
        val width = minSize + ((boundingBox.width - minSize) * Math.random() * 0.25).toInt
        val height = minSize + ((boundingBox.height - minSize) * Math.random() * 0.25).toInt

        val p1 = Vector2(
            x = (boundingBox.p1.x + (boundingBox.width - width) * Math.random()).toInt,
            y = (boundingBox.p1.y + (boundingBox.height - height) * Math.random()).toInt,
        )
        val p2 = p1 + (width, height)

        Rectangle(p1, p2)
    }



}


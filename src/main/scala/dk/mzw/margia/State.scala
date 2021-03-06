package dk.mzw.margia

import dk.mzw.margia.utility.{Rectangle, Vector2}

case class State(
    player : Player,
    var monsters : List[Monster],
    walls : List[Vector2],
    rooms : List[Rectangle]
)

case class Player(
    var position : Vector2,
    var health : Double,
    var maxHealth : Double,
)

case class Monster(
    var position : Vector2,
    var health : Double,
    var maxHealth : Double,
)


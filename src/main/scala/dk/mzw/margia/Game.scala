package dk.mzw.margia

import dk.mzw.margia.utility.{Keys, Mouse, Rectangle, Vector2}
import dk.mzw.scalasprites.SpriteCanvas.{BoundingBox, Display}

class Game(display: Display, sprites : Sprites, boundingBox: BoundingBox, mouse: Mouse, keys : Keys) {

    private var state = makeState()

    def update(delta: Double, t : Double) : Unit = {

        val dx = keys.factor(Keys.leftArrow, Keys.rightArrow)
        val dy = keys.factor(Keys.downArrow, Keys.upArrow)

        if(dx != 0 || dy != 0) {
            val newPosition = state.player.position + (dx, dy)
            val collision = state.walls.contains(newPosition)
            if(! collision) {
                state.monsters.find(monster => monster.position == newPosition) match {
                    case Some(blockingMonster) =>
                        blockingMonster.health -= Math.random() * 4
                        if(blockingMonster.health <= 0) {
                            val nonBlockingMonsters = state.monsters.filter(m => m != blockingMonster)
                            state.monsters = nonBlockingMonsters
                        }

                        state.player.health -= Math.random() * 1
                        if(state.player.health <= 0) {
                            // You are dead -> new game
                            state = makeState()
                        }
                    case None =>
                        state.player.position = newPosition
                }
            }
        }

    }

    def draw(t : Double) : Unit = {
        val h = 22
        val w = boundingBox.width

        display.add(sprites.grid(t), x = state.player.position.x, y = state.player.position.y,
            width = w, height = h,
            imageX = -0.5 * w + state.player.position.x,
            imageY = -0.5 * h + state.player.position.y,
            imageWidth = w,
            imageHeight = h
        )

        // Rooms
        state.rooms.foreach{ room =>
            display.add(
                image = sprites.room,
                x = room.p1.x + room.width * 0.5,
                y = room.p1.y + room.height * 0.5,
                width = room.width,
                height = room.height
            )
        }

        state.walls.foreach{p =>
            display.add(sprites.wall, p.x, p.y)
        }

        display.add(sprites.player(state.player.health / state.player.maxHealth)(t), state.player.position.x, state.player.position.y)

        state.monsters.foreach{ m =>
            display.add(sprites.monster(m.health / m.maxHealth), m.position.x, m.position.y)
        }

        display.draw((0, 0, 0, 1), h, state.player.position.x, state.player.position.y)
    }

    private def makeState() = {
        val walls = (for{
            x <- -10 to 10
            y <- -10 to 10
            if x == 10 || x == -10 || y == 10 || y == -10
        } yield Vector2(x, y)).toList

        val monsters = for(_ <- 1 to 10) yield {
            Monster(
                position = Vector2(
                    Math.floor((Math.random() - 0.5) * 18).toInt + 1,
                    Math.floor((Math.random() - 0.5) * 18).toInt + 1
                ),
                health = 10,
                maxHealth = 10,
            )
        }

        val generator = new GenerateDungeon(Rectangle(Vector2(-10, -10), Vector2(10, 10)))

        State(
            player = Player(
                position = Vector2(0, 0),
                health = 10,
                maxHealth = 10,
            ),
            monsters = monsters.toList,
            walls = walls,
            rooms = generator.makeRooms(0.2)
        )
    }

}

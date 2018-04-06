package dk.mzw.margia

import dk.mzw.scalashading.Language._
import dk.mzw.scalashading.Math._
import dk.mzw.scalashading.util.Prelude._
import dk.mzw.scalasprites.SpriteCanvas.{CustomShader, Loader}

class Sprites(loader : Loader) {

    val wall = loader("assets/floor.png", repeat = true)

    val room : CustomShader = {

        def shader(x : R)(y : R) = rgba(0.5, 0.5, 0, 1)

        loader.apply(shader _)
    }

    val player : Double => Double => CustomShader = {

        def shader(health : R)(t : R)(x : R)(y : R) = {
            val s = sinOne(t*4)
            val i = 1 - min(1, floor(distance(Vec2(0, 0), Vec2(x, y)) + s * 0.1))
            hsva(0, 1 - health, 0.7 + s * 0.3, i)
        }

        loader.u2(shader)
    }

    val monster : Double => CustomShader = {

        def shader(health : R)(x : R)(y : R) = {
            val i = 1 - min(1, floor(max(x, y)))
            hsva(0, 1 - health, 0.6, i)
        }

        loader.u1(shader)
    }

    val grid : Double => CustomShader = {

        def shader(t : R)(x : R)(y : R) = {
            val d = min(distance(round(x), x), distance(round(y), y))
            val i1 = smoothstep(0.02, 0.01, d)
            val d5 = min(distance(round(x * 0.2), x * 0.2), distance(round(y * 0.2), y * 0.2))
            val i5 = smoothstep(0.02 * 0.2, 0.01 * 0.2, d5)
            val d10 = min(distance(round(x * 0.1), x * 0.1), distance(round(y * 0.1), y * 0.1))
            val i10 = smoothstep(0.02 * 0.1, 0.01 * 0.1, d10)
            val i = i1 * 0.1 + i5 * 0.2 + i10 * 0.3
            hsva(
                0, //t * 0.05 + i * 0.5,
                0.5 + i * 0.5,
                i,
                1
            )
        }

        loader.u1(shader)
    }
}
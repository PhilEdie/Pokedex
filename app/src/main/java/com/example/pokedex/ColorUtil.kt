package com.example.pokedex

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.Size


class ColorUtil {

    companion object {
        /**
         * Returns the ColorID of the type which matches the provided string.
         */
        fun getColorIDByTypeString(typeString: String): Int {
            when (typeString) {
                "normal" -> return R.color.normal
                "fire" -> return R.color.fire
                "water" -> return R.color.water
                "electric" -> return R.color.electric
                "grass" -> return R.color.grass
                "ice" -> return R.color.ice
                "fighting" -> return R.color.fighting
                "poison" -> return R.color.poison
                "ground" -> return R.color.ground
                "flying" -> return R.color.flying
                "psychic" -> return R.color.psychic
                "bug" -> return R.color.bug
                "rock" -> return R.color.rock
                "ghost" -> return R.color.ghost
                "dragon" -> return R.color.dragon
                "dark" -> return R.color.dark
                "steel" -> return R.color.steel
                "fairy" -> return R.color.fairy
            }

            //Type not found. return the default system color.
            return R.color.design_default_color_background
        }

        /**
         * Converts a colorInt to a Color HSL.
         * Sourced from: https://medium.com/@anthony.st91/darken-or-lighten-a-color-in-android-ae70b63a249e
         */
        @Size(3)
        private fun colorToHSL(@ColorInt color: Int,
                               @Size(3) hsl: FloatArray = FloatArray(3)): FloatArray {
            val r = Color.red(color) / 255f
            val g = Color.green(color) / 255f
            val b = Color.blue(color) / 255f

            val max = Math.max(r, Math.max(g, b))
            val min = Math.min(r, Math.min(g, b))
            hsl[2] = (max + min) / 2

            if (max == min) {
                hsl[1] = 0f
                hsl[0] = hsl[1]

            } else {
                val d = max - min

                hsl[1] = if (hsl[2] > 0.5f) d / (2f - max - min) else d / (max + min)
                when (max) {
                    r -> hsl[0] = (g - b) / d + (if (g < b) 6 else 0)
                    g -> hsl[0] = (b - r) / d + 2
                    b -> hsl[0] = (r - g) / d + 4
                }
                hsl[0] /= 6f
            }
            return hsl
        }

        /**
         * Converts a Color HSL to a colorInt.
         * Sourced from: https://medium.com/@anthony.st91/darken-or-lighten-a-color-in-android-ae70b63a249e
         */
        @ColorInt
        private fun hslToColor(@Size(3) hsl: FloatArray): Int {
            val r: Float
            val g: Float
            val b: Float

            val h = hsl[0]
            val s = hsl[1]
            val l = hsl[2]

            if (s == 0f) {
                b = l
                g = b
                r = g
            } else {
                val q = if (l < 0.5f) l * (1 + s) else l + s - l * s
                val p = 2 * l - q
                r = hue2rgb(p, q, h + 1f / 3)
                g = hue2rgb(p, q, h)
                b = hue2rgb(p, q, h - 1f / 3)
            }

            return Color.rgb((r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt())
        }

        /**
         * Converts a hue to a rgb.
         * Sourced from: https://medium.com/@anthony.st91/darken-or-lighten-a-color-in-android-ae70b63a249e
         */
        private fun hue2rgb(p: Float, q: Float, t: Float): Float {
            var valueT = t
            if (valueT < 0) valueT += 1f
            if (valueT > 1) valueT -= 1f
            if (valueT < 1f / 6) return p + (q - p) * 6f * valueT
            if (valueT < 1f / 2) return q
            return if (valueT < 2f / 3) p + (q - p) * (2f / 3 - valueT) * 6f else p
        }

        /**
         * Lightens a color using a float value.
         * Sourced from: https://medium.com/@anthony.st91/darken-or-lighten-a-color-in-android-ae70b63a249e
         */
        @ColorInt
        fun lightenColor(@ColorInt color: Int,
                         value: Float): Int {
            val hsl = colorToHSL(color)
            hsl[2] += value
            hsl[2] = Math.max(0f, Math.min(hsl[2], 1f))
            return hslToColor(hsl)
        }

        /**
         * Darkens a color using a float value.
         * Sourced from: https://medium.com/@anthony.st91/darken-or-lighten-a-color-in-android-ae70b63a249e
         */
        @ColorInt
        fun darkenColor(@ColorInt color: Int,
                        value: Float): Int {
            val hsl = colorToHSL(color)
            hsl[2] -= value
            hsl[2] = Math.max(0f, Math.min(hsl[2], 1f))
            return hslToColor(hsl)
        }
    }
}
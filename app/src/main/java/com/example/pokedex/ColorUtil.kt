package com.example.pokedex

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
    }
}
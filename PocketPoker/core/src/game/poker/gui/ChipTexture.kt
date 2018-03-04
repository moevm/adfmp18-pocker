package game.poker.gui

import com.badlogic.gdx.graphics.Texture
import game.poker.core.Chip
import game.poker.staticFiles.Textures

class ChipTexture(val xPos: Int, val yPos: Int, chip: Chip) {
    val texture: Texture = Textures.getChip(chip)
}
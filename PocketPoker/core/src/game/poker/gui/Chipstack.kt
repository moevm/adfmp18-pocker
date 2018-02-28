package game.poker.gui

import game.poker.core.Chip


class Chipstack {

    var money: Int = 0

    private val stacks = arrayOf(
            mutableListOf<ChipTexture>(),
            mutableListOf<ChipTexture>(),
            mutableListOf<ChipTexture>(),
            mutableListOf<ChipTexture>(),
            mutableListOf<ChipTexture>(),
            mutableListOf<ChipTexture>(),
            mutableListOf<ChipTexture>(),
            mutableListOf<ChipTexture>()
    )

    fun setChips(newMoney: Int){
        money = newMoney
        var count = newMoney

        // below is near copy paste from javascript code
        val amounts = mutableListOf<MutableList<Pair<Chip, Int>>>()

        for(chip in Chip.values().reversed()){
            if(chip != Chip.DEALER){
                if(count >= chip.price()){
                    val intAmount = Math.floor(count.toDouble() / chip.price()).toInt()
                    amounts += mutableListOf(Pair(chip, intAmount))
                    count -= intAmount * chip.price()
                }
            }
        }

        while (amounts.size > 8){
            var minBetweens = -1
            var indexBetweens = -1

            for(i in 0 until amounts.size){

                var currBetween = 0

                for (j in amounts[i]){
                    currBetween += j.second
                }

                for (j in amounts[i+1]){
                    currBetween += j.second
                }

                if(currBetween <= minBetweens || minBetweens == -1){
                    minBetweens = currBetween
                    indexBetweens = i
                }

            }

            for (i in amounts[indexBetweens + 1]){
                amounts[indexBetweens].add(i)
            }

            amounts.removeAt(indexBetweens + 1)
        }

        for(stack in stacks){
            stack.clear()
        }

        for((i, currAmounts) in amounts.withIndex()){
            for(currChip in currAmounts){
                for(currChipAmount in 1..currChip.second){
                    stacks[i].add(ChipTexture(0, 0, currChip.first))
                }
            }
        }
    }
}
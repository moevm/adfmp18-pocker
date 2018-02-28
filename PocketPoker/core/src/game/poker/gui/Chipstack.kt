package game.poker.gui

import game.poker.core.Chip


class Chipstack {

    var money: Int = 0
        private set

    private val stacksCount = 8

    private val stacks: Array<MutableList<ChipTexture>> =
            Array(stacksCount) { mutableListOf<ChipTexture>() }

    fun setChips(newMoney: Int){
        money = newMoney
        var count = newMoney

        // below is copy paste from javascript code
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

        while (amounts.size > stacksCount){
            var minBetweens = -1
            var indexBetweens = -1

            for(i in 0 until amounts.size-1){

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
            println("" + (i + 1) + ")")
            for(currChip in currAmounts){
                println("    " + currChip.first.price() + " * " + currChip.second)
                for(currChipAmount in 1..currChip.second){
                    stacks[i].add(ChipTexture(0, 0, currChip.first))
                }
            }
        }
    }
}
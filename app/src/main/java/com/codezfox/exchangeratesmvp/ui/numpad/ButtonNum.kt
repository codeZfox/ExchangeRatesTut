package com.niveler.numpad

import android.support.annotation.DrawableRes
import com.codezfox.exchangeratesmvp.R

sealed class Button(var process: (String) -> String, var process2: (String) -> String = { it })

open class ButtonText(val symbol: String, process: (text: String, symbol: String) -> String) : Button({ process.invoke(it, symbol) }) {
    //todo for ButtonDot only one
    override fun toString(): String {
        return symbol
    }
}

class ButtonDot : ButtonText(".", { text, symbol ->
    if (!text.contains(symbol)) {
        if (text.isBlank()) {
            "0"
        } else {
            text
        }.plus(symbol)
    } else {
        text
    }
})

open class ButtonImage(@DrawableRes val image: Int, process: (String) -> String, process2: (String) -> String = { it }) : Button(process, process2)

class ButtonDel : ButtonImage(R.drawable.ic_backspace_outline, { it.dropLast(1) }, { "" })


open class ButtonNum(
        val num: Int,
        private val letters: List<Char> = listOf(),
        process: (text: String, number: Int) -> String = { text, number -> text.plus(number) }
) : Button({ process.invoke(it, num) }) {

    override fun toString(): String {
        return num.toString()
    }

    fun getLettersString(): String {
        return letters.joinToString("")
    }

    fun isHaveLetters() = letters.isNotEmpty()

    companion object {
        fun list(): List<Button> = listOf(
                ButtonNum(1, listOf()),
                ButtonNum(2, listOf('A', 'B', 'C')),
                ButtonNum(3, listOf('D', 'E', 'F')),
                ButtonNum(4, listOf('G', 'H', 'I')),
                ButtonNum(5, listOf('J', 'K', 'L')),
                ButtonNum(6, listOf('M', 'N', 'O')),
                ButtonNum(7, listOf('P', 'Q', 'R', 'S')),
                ButtonNum(8, listOf('T', 'U', 'V')),
                ButtonNum(9, listOf('W', 'X', 'Y', 'Z')),
                ButtonDot(),
                ButtonMinus(),
                ButtonDel()
        )

        fun list2(): List<Button> = listOf(
                ButtonNum(1, listOf()),
                ButtonNum(2, listOf()),
                ButtonNum(3, listOf()),
                ButtonNum(4, listOf()),
                ButtonNum(5, listOf()),
                ButtonNum(6, listOf()),
                ButtonNum(7, listOf()),
                ButtonNum(8, listOf()),
                ButtonNum(9, listOf()),
                ButtonDot(),
                ButtonNum(0, listOf(), { text, number ->
                    if (text.firstOrNull()?.toInt() ?: 0 == number) {
                        text
                    } else {
                        text.plus(number)
                    }
                }),
                ButtonDel()
        )
    }
}

class ButtonMinus : ButtonNum(0)
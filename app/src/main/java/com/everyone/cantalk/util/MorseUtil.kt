package com.everyone.cantalk.util

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.core.content.ContextCompat.getSystemService

data class MorseUtil(private val morseCode : Map<String, Any?> = mapOf(
    "A" to "._",
    "B" to "_...",
    "C" to "_._.",
    "D" to "_..",
    "E" to ".",
    "F" to ".._.",
    "G" to "__.",
    "H" to "....",
    "I" to "..",
    "J" to ".___",
    "K" to "_._",
    "L" to "._..",
    "M" to "__",
    "N" to "_.",
    "O" to "___",
    "P" to ".__.",
    "Q" to "__._",
    "R" to "._.",
    "S" to "...",
    "T" to "_",
    "U" to ".._",
    "V" to "..._",
    "W" to ".__",
    "X" to "_.._",
    "Y" to "_.__",
    "Z" to "__..",
    "a" to "._",
    "b" to "_...",
    "c" to "_._.",
    "d" to "_..",
    "e" to ".",
    "f" to ".._.",
    "g" to "__.",
    "h" to "....",
    "i" to "..",
    "j" to ".___",
    "k" to "_._",
    "l" to "._..",
    "m" to "__",
    "n" to "_.",
    "o" to "___",
    "p" to ".__.",
    "q" to "__._",
    "r" to "._.",
    "s" to "...",
    "t" to "_",
    "u" to ".._",
    "v" to "..._",
    "w" to ".__",
    "x" to "_.._",
    "y" to "_.__",
    "z" to "__..",
    "0" to "_____",
    "1" to ".____",
    "2" to "..___",
    "3" to "...__",
    "4" to "...._",
    "5" to ".....",
    "6" to "_....",
    "7" to "__...",
    "8" to "___..",
    "9" to "____.",
    " " to " ",
    "_____" to "0",
    ".____" to "1",
    "..___" to "2",
    "...__" to "3",
    "...._" to "4",
    "....." to "5",
    "_...." to "6",
    "__..." to "7",
    "___.." to "8",
    "____." to "9",
    "._"    to "a",
    "_..."  to "b",
    "_._."  to "c",
    "_.."   to "d",
    "."     to "e",
    ".._."  to "f",
    "__."   to "g",
    "...."  to "h",
    ".."    to "i",
    ".___"  to "j",
    "_._"   to "k",
    "._.."  to "l",
    "__"    to "m",
    "_."    to "n",
    "___"   to "o",
    ".__."  to "p",
    "__._"  to "q",
    "._."   to "r",
    "..."   to "s",
    "_"     to "t",
    ".._"   to "u",
    "..._"  to "v",
    ".__"   to "w",
    "_.._"  to "x",
    "_.__"  to "y",
    "__.."  to "z"
)) {

    companion object {

        fun morseCodeConverter(message: String) : String {
            var result = ""
            val morseCode = MorseUtil().morseCode
            val len = message.length
            for(i in 0 until len) {
                if (morseCode[message[i].toString()] != null) {
                    result += morseCode[message[i].toString()]
                    if((message[i].toString().trim().isNotEmpty() || message[i].toString().trim().isNotBlank()) && i < message.length-1)
                        result += '/'
                }
            }
            result = result.replace("/ ", " ")
            result = result.replace(" /", "")

            return result.trim()
        }

        fun alphabetConverter(message: String) : String {
            val arrCodes = message.split(" ")
            var letters = ""
            val morseCode = MorseUtil().morseCode

            for(i in arrCodes) {
                val code = i.split("/")
                var prevLetter = ""
                for (j in code) {
                    prevLetter = morseCode[j].toString()
                    letters += if (prevLetter != "null") prevLetter else "/$j/"
                }
                letters += " "
            }

            letters = letters.replace("/ ", " ")
            letters = letters.replace(" /", " ")

            return letters.trim()
        }

        fun readMorse(context: Context, codeMessage: String) {
            val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
            for (element in codeMessage) {
                when (element) {
                    '.' -> {
                        if (Build.VERSION.SDK_INT >= 26) {
                            vibrator.vibrate(
                                VibrationEffect.createOneShot(
                                    200,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                        } else {
                            vibrator.vibrate(200)
                        }
                        try {
                            Thread.sleep(300)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    '_' -> {
                        if (Build.VERSION.SDK_INT >= 26) {
                            vibrator.vibrate(
                                VibrationEffect.createOneShot(
                                    500,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                        } else {
                            vibrator.vibrate(500)
                        }
                        try {
                            Thread.sleep(300)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    ' ' -> {
                        try {
                            Thread.sleep(1000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    '/' -> {
                        try {
                            Thread.sleep(500)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

    }

}
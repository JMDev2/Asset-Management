package com.ekenya.rnd.common.utils

object CardMaskUtil {

    fun maskCardNumber(cardNumber: String): String {
        // number of digits to be seen at the begining
        val visibleDigits = 4

        // Ensure the cardNumber has at least 12 characters
        if (cardNumber.length < 12) {
            return cardNumber
        }

        val maskedChars = StringBuilder()

        // Append the visible digits at the beginning
        for (i in 0 until visibleDigits) {
            maskedChars.append(cardNumber[i])
        }

        // Append the masked characters with '*'
        for (i in visibleDigits until cardNumber.length - visibleDigits) {
            maskedChars.append('*')
        }

        // Append the visible digits at the end
        for (i in cardNumber.length - visibleDigits until cardNumber.length) {
            maskedChars.append(cardNumber[i])
        }

        return maskedChars.toString()
    }
}
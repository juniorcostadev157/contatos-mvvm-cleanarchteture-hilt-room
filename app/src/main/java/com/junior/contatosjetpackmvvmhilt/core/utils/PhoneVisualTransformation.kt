package com.junior.contatosjetpackmvvmhilt.core.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }.take(11)


        val formatted = buildString {
            for (i in digits.indices) {
                when (i) {
                    0 -> append("(")
                    2 -> append(") ")
                    7 -> append("-")
                }
                append(digits[i])
            }
        }

        val transformed = AnnotatedString(formatted)

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return 0
                var transformedOffset = offset
                if (offset >= 2) transformedOffset += 1   // "("
                if (offset >= 2 && offset < 7 && digits.length >= 3) transformedOffset += 2 // ") "
                if (offset >= 7 && digits.length >= 8) transformedOffset += 3 // ") " + "-"
                return transformedOffset.coerceAtMost(transformed.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 0) return 0
                var originalOffset = offset
                if (offset > 0 && digits.isNotEmpty()) originalOffset -= 1 // "("
                if (offset > 3 && digits.length >= 3) originalOffset -= 2 // ") "
                if (offset > 9 && digits.length >= 8) originalOffset -= 3 // "-"
                return originalOffset.coerceAtMost(digits.length)
            }
        }

        return TransformedText(transformed, offsetMapping)
    }

    fun formatPhoneNumber(phone: String): String {
        val digits = phone.filter { it.isDigit() }.take(11)

        return buildString {
            for (i in digits.indices) {
                when (i) {
                    0 -> append("(")
                    2 -> append(") ")
                    7 -> append("-")
                }
                append(digits[i])
            }
        }
    }
}

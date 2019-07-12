package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")

        return if (fullName?.trim() == "") {
            null to null
        } else {
            val firstName = parts?.getOrNull(0)
            val lastName = parts?.getOrNull(1)
            //return Pair (firstName, lastName)
            firstName to lastName
        }
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var out = payload
        val compare: Map<String, String> = mutableMapOf(
            "а" to "a",
            "б" to "b",
            "в" to "v",
            "г" to "g",
            "д" to "d",
            "е" to "e",
            "ё" to "e",
            "ж" to "zh",
            "з" to "z",
            "и" to "i",
            "й" to "i",
            "к" to "k",
            "л" to "l",
            "м" to "m",
            "н" to "n",
            "о" to "o",
            "п" to "p",
            "р" to "r",
            "с" to "s",
            "т" to "t",
            "у" to "u",
            "ф" to "f",
            "х" to "h",
            "ц" to "c",
            "ч" to "ch",
            "ш" to "sh",
            "щ" to "sh'",
            "ъ" to "",
            "ы" to "i",
            "ь" to "",
            "э" to "e",
            "ю" to "yu",
            "я" to "ya"
        )

        for ((key, value) in compare) {
            out = out.replace(key, value)
            out = out.replace(key.capitalize(), value.capitalize())
        }
        return out.replace(" ", divider)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        /*
        Utils.toInitials("john" ,"doe") //JD
        Utils.toInitials("John", null) //J
        Utils.toInitials(null, null) //null
        Utils.toInitials(" ", "") //null
        */

        var first = firstName?.trim()
        var last = lastName?.trim()

        if (first != null && first != "") {
            first = first[0].toString()
        }
        if (last != null && last != "") {
            last = last[0].toString()
        }

        if ((first == "" || first == null) && (last == "" || last == null)) {
            return null
        }

        if (first != null && last == null) {
            return "$first"
        }
        if (first == null && last != null) {
            return "$last"
        }
        return "$first$last".capitalize()
    }
}
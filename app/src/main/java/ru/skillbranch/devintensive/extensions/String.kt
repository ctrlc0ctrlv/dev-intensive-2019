package ru.skillbranch.devintensive.extensions

fun String.truncate(value: Int = 16) : String{
    val temp = this.trimEnd()
    return if (temp.length > value){
        var out = ""
        for (i in 0 until value){
            out += temp[i]
        }
        out.trimEnd()+"..."
    } else temp
}
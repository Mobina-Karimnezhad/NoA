package com.noa.app.ui.utils

fun Int.toPersianDigits(): String {

    val english = this.toString()

    val persian = arrayOf(
        '۰','۱','۲','۳','۴',
        '۵','۶','۷','۸','۹'
    )

    return buildString {

        english.forEach {

            append(

                if (it.isDigit())
                    persian[it - '0']
                else
                    it

            )

        }

    }

}
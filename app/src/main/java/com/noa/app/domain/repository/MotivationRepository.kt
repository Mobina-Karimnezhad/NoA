package com.noa.app.data.repository

import com.noa.app.domain.model.Motivation

object MotivationRepository {

    private val motivations = listOf(

        Motivation(
            1,
            "هر روز فقط یک قدم جلوتر از دیروز باش."
        ),

        Motivation(
            2,
            "پیشرفت آرام، بهتر از توقف کامل است."
        ),

        Motivation(
            3,
            "امروز فرصتی تازه برای ساختن خودت است."
        ),

        Motivation(
            4,
            "موفقیت نتیجه تکرار کارهای کوچک است."
        ),

        Motivation(
            5,
            "استمرار از انگیزه مهم‌تر است."
        ),

        Motivation(
            6,
            "هر عادت خوب، آینده‌ی تو را می‌سازد."
        ),

        Motivation(
            7,
            "همین امروز شروع کن؛ کامل بودن مهم نیست."
        )

    )

    fun randomMotivation(): Motivation {

        return motivations.random()

    }

}
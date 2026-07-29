package com.noa.app.ui.screens.habitdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.noa.app.domain.helper.PersianCalendarUtils
import androidx.compose.foundation.isSystemInDarkTheme

@Composable
fun HabitWeeklyCalendar(

    days: List<HabitCalendarDay>,

    canGoPrevious: Boolean,

    canGoNext: Boolean,

    onPreviousWeek: () -> Unit,

    onNextWeek: () -> Unit

) {

    if (days.isEmpty()) {
        return
    }

    Column(

        modifier =
            Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(24.dp)
                )
                .background(
                    MaterialTheme
                        .colorScheme
                        .surfaceVariant
                )
                .padding(16.dp)

    ) {

        // -------------------------
        // Week Navigation
        // -------------------------

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically,

            horizontalArrangement =
                Arrangement.SpaceBetween

        ) {

            // Previous week

            IconButton(

                enabled =
                    canGoPrevious,

                onClick =
                    onPreviousWeek

            ) {

                Icon(

                    imageVector =
                        Icons.Default.ChevronRight,

                    contentDescription =
                        "هفته قبل"

                )

            }

            // Month title

            Text(

                text =
                    getPersianMonthTitle(
                        days
                    ),

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    FontWeight.Bold,

                textAlign =
                    TextAlign.Center

            )

            // Next week

            IconButton(

                enabled =
                    canGoNext,

                onClick =
                    onNextWeek

            ) {

                Icon(

                    imageVector =
                        Icons.Default.ChevronLeft,

                    contentDescription =
                        "هفته بعد"

                )

            }

        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        // -------------------------
        // Seven days
        // -------------------------

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(
                    6.dp
                )

        ) {

            days.forEach { day ->

                HabitCalendarDayItem(

                    day =
                        day,

                    modifier =
                        Modifier.weight(1f)

                )

            }

        }

    }

}

// --------------------------------------------------
// Single day
// --------------------------------------------------

@Composable
private fun HabitCalendarDayItem(

    day: HabitCalendarDay,

    modifier: Modifier = Modifier

) {

    val isDark = isSystemInDarkTheme()

    val backgroundColor =
        when (day.status) {

            HabitCalendarDayStatus.COMPLETED ->
                if (isDark)
                    Color(0xFF1E3A29)
                else
                    Color(0xFFDFF3E4)

            HabitCalendarDayStatus.MISSED ->
                if (isDark)
                    Color(0xFF3D2020)
                else
                    Color(0xFFFCE4E4)

            HabitCalendarDayStatus.TODAY ->
                MaterialTheme.colorScheme.surface

            HabitCalendarDayStatus.FUTURE ->
                if (isDark)
                    Color(0xFF2C2C2C)
                else
                    MaterialTheme.colorScheme.surface

            HabitCalendarDayStatus.NOT_SELECTED ->
                if (isDark)
                    Color(0xFF3A3A3A)
                else
                    Color(0xFFE7E7E7)
        }

    val borderColor =
        when {

            day.isCompletionDate ->
                Color(0xFFFFC107)

            day.isStartDate ->
                MaterialTheme
                    .colorScheme
                    .primary

            day.status ==
                    HabitCalendarDayStatus.TODAY ->
                MaterialTheme
                    .colorScheme
                    .primary

            else ->
                Color.Transparent

        }

    Column(

        modifier =

            modifier

                .clip(
                    RoundedCornerShape(16.dp)
                )

                .background(
                    backgroundColor
                )

                .border(

                    width =
                        if (
                            day.isStartDate ||
                            day.isCompletionDate ||
                            day.status ==
                            HabitCalendarDayStatus.TODAY
                        ) {
                            2.dp
                        } else {
                            0.dp
                        },

                    color =
                        borderColor,

                    shape =
                        RoundedCornerShape(16.dp)

                )

                .padding(

                    vertical =
                        8.dp,

                    horizontal =
                        2.dp

                ),

        horizontalAlignment =
            Alignment.CenterHorizontally

    ) {

        // -------------------------
        // Week day name
        // -------------------------

        Text(

            text =
                day.persianWeekDayName.take(1),

            style =
                MaterialTheme.typography.labelSmall,

            fontWeight =
                FontWeight.SemiBold,

            color =
                when (day.status) {

                    HabitCalendarDayStatus.COMPLETED ->
                        if (isDark)
                            Color.White
                        else
                            Color(0xFF1B5E20)

                    HabitCalendarDayStatus.MISSED ->
                        if (isDark)
                            Color.White
                        else
                            Color(0xFFB71C1C)

                    HabitCalendarDayStatus.NOT_SELECTED ->
                        if (isDark)
                            Color(0xFFB5B5B5)
                        else
                            Color(0xFF707070)

                    else ->
                        MaterialTheme.colorScheme.onSurface
                }

        )

        Spacer(
            modifier =
                Modifier.height(6.dp)
        )

        // -------------------------
        // Day status
        // -------------------------

        Box(

            modifier =
                Modifier.size(32.dp),

            contentAlignment =
                Alignment.Center

        ) {

            when {

                // Final completion day

                day.isCompletionDate -> {

                    Text(

                        text =
                            "⭐",

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium

                    )

                }

                // Completed

                day.status ==
                        HabitCalendarDayStatus.COMPLETED -> {

                    Box(

                        modifier =

                            Modifier
                                .size(26.dp)
                                .clip(
                                    CircleShape
                                )
                                .background(
                                    Color(
                                        0xFF8BCF9B
                                    )
                                ),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Text(

                            text =
                                "✓",

                            color =
                                Color.White,

                            fontWeight =
                                FontWeight.Bold

                        )

                    }

                }

                // Missed

                day.status ==
                        HabitCalendarDayStatus.MISSED -> {

                    Text(

                        text = "✕",

                        color =
                            Color(
                                0xFFE58B8B
                            ),

                        fontWeight =
                            FontWeight.Bold,

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium

                    )

                }

                // Future / Not selected

                else -> {

                    Text(

                        text =
                            day.persianDayNumber,

                        color =
                            when(day.status){

                                HabitCalendarDayStatus.NOT_SELECTED ->
                                    if (isDark)
                                        Color(0xFFB5B5B5)
                                    else
                                        Color(0xFF757575)

                                HabitCalendarDayStatus.FUTURE ->
                                    if (isDark)
                                        Color(0xFFF2F2F2)
                                    else
                                        MaterialTheme.colorScheme.onSurface

                                else ->
                                    MaterialTheme.colorScheme.onSurface
                            },

                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium,

                        fontWeight =

                            if (
                                day.isStartDate
                            ) {

                                FontWeight.Bold

                            } else {

                                FontWeight.Normal

                            }

                    )

                }

            }

        }

        Spacer(
            modifier =
                Modifier.height(4.dp)
        )

        // -------------------------
        // Start / End label
        // -------------------------

        when {

            day.isStartDate -> {

                Text(

                    text =
                        "شروع",

                    style =
                        MaterialTheme
                            .typography
                            .labelSmall,

                    color =
                        MaterialTheme
                            .colorScheme
                            .primary,

                    fontWeight =
                        FontWeight.Bold

                )

            }

            day.isCompletionDate -> {

                Text(

                    text =
                        "پایان",

                    style =
                        MaterialTheme
                            .typography
                            .labelSmall,


                    color =
                        MaterialTheme.colorScheme.primary,

                    fontWeight =
                        FontWeight.Bold

                )

            }

        }

    }

}

// --------------------------------------------------
// Persian month title
// --------------------------------------------------

private fun getPersianMonthTitle(

    days: List<HabitCalendarDay>

): String {

    val first =
        days.first()

    val last =
        days.last()

    return if (
        first.persianMonthName ==
        last.persianMonthName
    ) {

        "${first.persianDayNumber} تا " +
                "${last.persianDayNumber} " +
                "${first.persianMonthName} " +
                "${getPersianYear(first)}"

    } else {

        "${first.persianDayNumber} " +
                "${first.persianMonthName} تا " +
                "${last.persianDayNumber} " +
                "${last.persianMonthName} " +
                "${getPersianYear(last)}"

    }

}

// --------------------------------------------------
// Persian year
// --------------------------------------------------

private fun getPersianYear(

    day: HabitCalendarDay

): String {

    return PersianCalendarUtils
        .fromGregorianLocalDate(
            day.date
        )
        .year
        .toString()

}
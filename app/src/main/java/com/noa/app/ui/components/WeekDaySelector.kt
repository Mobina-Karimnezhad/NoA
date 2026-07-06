package com.noa.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.noa.app.domain.model.WeekDay

@Composable
fun WeekDaySelector(

    selectedDays: List<WeekDay>,

    onDayClick: (WeekDay) -> Unit

) {

    Row(

        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.SpaceEvenly

    ) {

        WeekDay.entries.forEach { day ->

            WeekDayChip(

                day = day,

                selected = day in selectedDays,

                onClick = {

                    onDayClick(day)

                }

            )

        }

    }

}
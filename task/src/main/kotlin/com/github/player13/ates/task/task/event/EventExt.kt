package com.github.player13.ates.task.task.event

import com.github.player13.ates.event.task.Status as StatusInEvent
import com.github.player13.ates.task.task.Status

fun Status.toEvent() =
    when (this) {
        Status.IN_PROGRESS -> StatusInEvent.IN_PROGRESS
        Status.COMPLETED -> StatusInEvent.COMPLETED
    }
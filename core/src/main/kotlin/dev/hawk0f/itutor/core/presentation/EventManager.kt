package dev.hawk0f.itutor.core.presentation

import androidx.annotation.StringRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * A singleton object responsible for managing application-wide events in a reactive manner.
 * It provides an easy way to trigger and observe events using Channel.
 * This is particularly useful in unidirectional data flow architecture (MVI).
 */
object EventManager {

    /**
     * A buffered channel to send and receive application events.
     * This channel ensures that events are stored temporarily until they are consumed.
     */
    private val eventChannel = Channel<AppEvent>(Channel.BUFFERED)

    /**
     * A Flow that emits events as they are sent through the channel.
     * Observers can collect this flow to react to events in a non-blocking manner.
     */
    val eventsFlow = eventChannel.receiveAsFlow()

    /**
     * Triggers an event by sending it to the event channel.
     * This function is non-blocking and runs on a background thread.
     * @param event The event to trigger.
     */
    fun triggerEvent(event: AppEvent) {
        CoroutineScope(Dispatchers.Default).launch { eventChannel.send(event) }
    }

    /**
     * A sealed class representing all possible types of application events.
     * This allows for strongly typed event handling with a clear structure.
     */
    sealed class AppEvent {
        /**
         * Represents an event to display a Snackbar with a message.
         * @param message The string resource ID for the message to display.
         */
        data class ShowSnackbar(@StringRes val message: Int) : AppEvent()

        /**
         *
         * Navigate to profile from all screens
         *
         */
        data object NavigateToProfile : AppEvent()
    }
}
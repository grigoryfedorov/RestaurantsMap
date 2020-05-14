package org.grigoryfedorov.restaurantsmap.ui.map

sealed class UserAction {
    object LocationEnablePrompt : UserAction()
}
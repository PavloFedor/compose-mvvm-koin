package com.pavlo.fedor.compose.flow.laucnhes.state

internal sealed class LaunchesNavigationStateAction {

    class OnSelectedNew(val host: String) : LaunchesNavigationStateAction()
}
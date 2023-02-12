package com.alancamargo.tubecalculator.core.test.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelFlowCollector<S, A>(
    private val stateFlow: Flow<S>,
    private val actionFlow: Flow<A>,
    private val dispatcher: TestCoroutineDispatcher
) {

    fun test(block: suspend TestCoroutineScope.(List<S>, List<A>) -> Unit) {
        dispatcher.runBlockingTest {
            val states = mutableListOf<S>()
            val stateJob = launch { stateFlow.toList(states) }

            val actions = mutableListOf<A>()
            val actionJob = launch { actionFlow.toList(actions) }

            block(states, actions)

            stateJob.cancel()
            actionJob.cancel()
        }
    }
}

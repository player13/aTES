package com.github.player13.ates.analytics.statistics.rest

import com.github.player13.ates.analytics.statistics.usecase.GetStatisticsUseCase
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/statistics")
@SecurityRequirement(name = "security_auth")
class StatisticsController(
    private val getStatisticsUseCase: GetStatisticsUseCase,
) {

    @GetMapping
    fun get() =
        getStatisticsUseCase.get()
            .let { (companyProfits, mostExpensiveTasks, negativeBalanceCounts) ->
                StatisticsResponse(
                    companyProfits = companyProfits.associate { it.date to it.profit },
                    mostExpensiveTasks = mostExpensiveTasks.associate { it.date to it.reward },
                    negativeBalanceCounts = negativeBalanceCounts.associate { it.date to it.count },
                )
            }
}
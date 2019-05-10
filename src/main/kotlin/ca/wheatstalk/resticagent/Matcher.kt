package ca.wheatstalk.resticagent

import java.lang.RuntimeException

class Matcher<T> {
    private val list = ArrayList<MatcherPattern<T>>()
    fun matchPattern(pattern: String, body: (MatchResult) -> T) {
        list.add(MatcherPattern(pattern.toRegex(), body))
    }

    fun execute(subject: String): T {
        for (item in list) {
            val matchResult = item.pattern.matchEntire(subject)
            if (matchResult != null) {
                return item.body(matchResult)
            }
        }

        throw UnknownPatternException("Unknown pattern: $subject")
    }

    class UnknownPatternException(message: String) : RuntimeException(message)
}

data class MatcherPattern<T> (
    val pattern: Regex,
    val body: (MatchResult) -> T
)

fun <T>firstMatch(subject: String, block : Matcher<T>.() -> Unit): T {
    val matcher = Matcher<T>()
    matcher.block()
    return matcher.execute(subject)
}
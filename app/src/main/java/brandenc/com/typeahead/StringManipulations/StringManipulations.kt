package brandenc.com.typeahead.StringManipulations

import brandenc.com.typeahead.Models.Performer

fun createEventName(performers: List<Performer>): String {
    return performers.map { it.name() }.joinToString(", ")
}

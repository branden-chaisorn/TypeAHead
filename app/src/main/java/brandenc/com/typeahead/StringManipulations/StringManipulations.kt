package brandenc.com.typeahead.StringManipulations

import brandenc.com.typeahead.Models.Performer

fun createEventName(performerNames: List<Performer>): String {
    var performerString = ""
    for (i in 0..performerNames.size - 1) {
        if (i == 0) {
            performerString = performerNames[i].name()
        } else {
            performerString += ", " + performerNames[i].name()
        }
    }
    return performerString
}

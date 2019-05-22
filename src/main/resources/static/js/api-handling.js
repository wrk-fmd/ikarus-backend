var apiBaseUrl = "./api/v1";

function getEvents(successCallback, errorCallback) {
    $.ajax({
        type: "GET",
        url: apiBaseUrl + "/public/events",
        success: successCallback,
        error: errorCallback
    });
}

function updateEvent(eventData, successCallback, errorCallback) {
    $.ajax({
        type: "POST",
        url: apiBaseUrl + "/private/event",
        contentType: "application/json",
        data: eventData,
        success: successCallback,
        error: errorCallback
    });
}

function getEventInfo(eventId, successCallback, errorCallback) {
    $.ajax({
        type: "GET",
        url: apiBaseUrl + "/private/event/" + eventId,
        success: successCallback,
        error: errorCallback
    });
}

function deleteEvent(eventId, successCallback, errorCallback) {
    $.ajax({
        type: "DELETE",
        url: apiBaseUrl + "/private/event/" + eventId,
        success: successCallback,
        error: errorCallback
    });
}

function importEvent(eventData, successCallback, errorCallback) {
    $.ajax({
        type: "POST",
        url: apiBaseUrl + "/private/event/import",
        contentType: false,
        enctype: "multipart/form-data",
        processData: false,
        data: eventData,
        success: successCallback,
        error: errorCallback
    });
}
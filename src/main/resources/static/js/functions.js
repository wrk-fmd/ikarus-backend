$(document).ready(function () {
    $("body").append("<div id='info' class='modal'><div class='modal-dialog'><div class='modal-content'>" +
        "<div class='modal-header'><h4 class='modal-title'>Ikarus Info</h4><button class='close' data-dismiss='modal'>&times;</button></div>" +
        "<div class='modal-body'>Ikarus Backend 0.0.1-SNAPSHOT<br/><br/>Benutzte Drittanbieter-Bibliotheken: " +
        "<a href='https://jquery.com/'>jQuery</a>, <a href='https://getbootstrap.com/'>Bootstrap</a>, <a href='https://datatables.net/'>DataTables</a> und <a href='https://fontawesome.com/'>Font Awesome</a></div>" +
        "<div class='modal-footer'><button class='btn btn-primary' data-dismiss='modal'>Schlie&szlig;en</button></div></div></div></div>");
});

function getCurrentEvent() {
    if (!sessionStorage.getItem("currentEvent")) {
        return -1;
    }
    return sessionStorage.getItem("currentEvent")
}

function setCurrentEvent(currentEvent) {
    if (currentEvent == null) {
        sessionStorage.removeItem("currentEvent");
    } else {
        sessionStorage.setItem("currentEvent", currentEvent);
    }
}

function getCurrentStaff() {
    if (!sessionStorage.getItem("currentStaff")) {
        return -1;
    }
    return sessionStorage.getItem("currentStaff")
}

function setCurrentStaff(currentStaff) {
    if (currentStaff == null) {
        sessionStorage.removeItem("currentStaff");
    } else {
        sessionStorage.setItem("currentStaff", currentStaff);
    }
}

function convertFormToJSON(formData) {
    var json = {};
    jQuery.each(formData, function(index, value) {
        json[value["name"]] = value["value"];
    });
    return JSON.stringify(json);
}

function getStatusString(status) {
    switch (status) {
        case "NOT_REGISTERED": return "Nicht registriert";
        case "REGISTERED": return "Registriert";
        case "REGISTERED_WITH_MATERIAL": return "Registriert - Material ausgefasst";
        case "DEREGISTERED": return "Abgemeldet";
        default: return status;
    }
}

function getStatusColor(status) {
    switch (status) {
        case "NOT_REGISTERED": return "danger";
        case "REGISTERED": return "success";
        case "REGISTERED_WITH_MATERIAL": return "primary";
        case "DEREGISTERED": return "secondary";
        default: return status;
    }
}
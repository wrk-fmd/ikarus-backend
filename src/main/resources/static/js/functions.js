$(document).ready(function () {
    $("body").append("<div id='info' class='modal'><div class='modal-dialog'><div class='modal-content'>" +
        "<div class='modal-header'><h4 class='modal-title'>Ikarus Info</h4><button class='close' data-dismiss='modal'>&times;</button></div>" +
        "<div class='modal-body'>Ikarus Backend 1.0.2-SNAPSHOT<br/><br/>Benutzte Drittanbieter-Bibliotheken: " +
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

function getStatusOrder(status) {
    switch (status) {
        case "NOT_REGISTERED": return 1;
        case "REGISTERED": return 2;
        case "REGISTERED_WITH_MATERIAL": return 3;
        case "DEREGISTERED": return 4;
        default: 0;
    }
}

function getStatusString(status) {
    switch (status) {
        case "NOT_REGISTERED": return "<i class='fas fa-times-circle text-danger' title='Nicht registriert'></i><span class='d-none text-danger status-text'><br/>Nicht registriert</span>";
        case "REGISTERED": return "<i class='fas fa-check-circle text-success' title='Registriert'></i><span class='d-none text-success status-text'><br/>Registriert</span>";
        case "REGISTERED_WITH_MATERIAL": return "<i class='fas fa-check-circle text-success' title='Registriert'></i>&nbsp;<i class='fas fa-box text-primary' title='Material ausgefasst'></i><span class='d-none text-primary status-text'><br/>Mit Materialausgabe registriert</span>";
        case "DEREGISTERED": return "<i class='fas fa-walking text-secondary' title='Abgemeldet'></i><span class='d-none text-secondary status-text'><br/>Abgemeldet</span>";
        default: return status;
    }
}

function showStatusString(element) {
    element.removeClass("d-none");
    element.fadeIn(500, function() {
        element.fadeOut(1500);
    });
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
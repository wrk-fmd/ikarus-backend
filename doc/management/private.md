# Private API for Management

## CRUD of events

### Create or update an event

* Path: `/api/v1/private/event`
* HTTP method: `POST`

To create or update an event the request body has to contain an
[Event](../types.md#data-type-event) object in JSON format.

To create the event the `id` value must not be given,
because it will get generated automatically.

In case of success the server responds with the created (HTTP status code `201`)
or updated (HTTP status code `200`) [Event](../types.md#data-type-event) object in JSON format.

### Get event information

* Path: `/api/v1/private/event/<eventId>`
* HTTP method: `GET`

In case of success (HTTP status code `200`) the server responds with the searched
[Event](../types.md#data-type-event) object in JSON format.

### Delete an event

* Path: `/api/v1/private/event/<eventId>`
* HTTP method: `DELETE`

In case of success (HTTP status code `204`) the server responds with an empty response body.

### Import an event

* Path: `/api/v1/private/event/import`
* HTTP method: `POST`

This method imports a new event or updates an existing event by uploading an import file.
The following file types are allowed:

* iCalendar files (`.ical`, `.ics`, `.ifb`, `.iCalendar`)

In case of success the server responds with the created (HTTP status code `201`)
or updated (HTTP status code `200`) [Event](../types.md#data-type-event) object in JSON format.

#### iCalendar files import

Uploaded iCalendar files should offer a `VEVENT` structure.
The properties of the file will be mapped to an
[Event](../types.md#data-type-event) object as follows:

* `SUMMARY` maps to `name`
* `DESCRIPTION` maps to `description`
* `LOCATION` maps to `location`
* `DTSTART` maps to `start`
* `DTEND` maps to `end`

The `externalId` will be read from the `DESCRIPTION` field if given as for example:
`ID: "2019/00017"`.

In case of the iCalendar export file of the Google Chrome extension "NIUs little helper"
the `AmbulanceNr` of the `URL` field will be extracted to fill the `externalId`.

## CRUD of staff for an event

### Add a staff member to an event or update an existing staff member

* Path: `/api/v1/private/event/<eventId>/staff`
* HTTP method: `POST`

To create a new staff member for an event or update an existing one the request body has to contain a
[Staff](../types.md#data-type-staff) object in JSON format.
Only the `id` value must not be given,
because it will get generated automatically.

In case of success the server responds with the created (HTTP status code `201`)
or updated (HTTP status code `200`) [Staff](../types.md#data-type-staff) object in JSON format.

### Get staff information

* Path: `/api/v1/private/staff/<staffId>`
* HTTP method: `GET`

In case of success (HTTP status code `200`) the server responds with the searched
[Staff](../types.md#data-type-staff) object in JSON format.

### Delete a staff member

* Path: `/api/v1/private/staff/<staffId>`
* HTTP method: `DELETE`

In case of success (HTTP status code `204`) the server responds with an empty response body.

### Import staff members

* Path: `/api/v1/private/event/<eventId>/staff/import`
* HTTP method: `POST`

This method imports staff members or updates existing ones by uploading an import file.
The following file types are allowed:

* Comma separated value files (`.csv`)

In case of success the server responds with a list of the created (HTTP status code `201`)
or updated (HTTP status code `200`) [Staff](../types.md#data-type-staff) objects in JSON format.

#### Comma separated value files import

A correct `.csv` file,
where the name of the columns matches the names of the properties of a [Staff](../types.md#data-type-staff) object,
should be uploaded.
The `id` field must not be given and the `status` field is optional.
The `id` gets generated automatically,
and the `status` will get initialized with the value `NOT_REGISTERED` if not given in the file.

When uploading a file which has been exported with the Google Chrome extension "NIUs little helper"
the fields will map as follows:

* `dNr` maps to `externalId`
* `Nachname` + `Vorname` maps to `name`
* `tel` maps to `phone`
* `Email` maps to `email`
* `Ort` maps to `startLocation`
* `Zeitpunkt` maps to `startTime`
* `Anmerkung` maps to `comment`

Also here the `status` will get initialized with the value `NOT_REGISTERED` if not given in the file.

In every case it is possible to upload a single file containing all staff members of all sections,
or multiple files named after the sections name (for example `EL.csv`).
So depending on the format the `section` field will be filled with the file name or the given
`section` which can be found within the `.csv` file.
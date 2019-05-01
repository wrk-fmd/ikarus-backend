# Private API for Management

## Enum `Status`

The status enumeration contains the following possible values:

* `NOT_REGISTERED`
* `REGISTERED`
* `REGISTERED_GOT_MATERIAL`
* `DEREGISTERED`

## Data type `Event`

Properties:

* `id`: The ID of the event
* (optional) `identifier`: The identifier of the event
* `name`: The name of the event
* (optional) `description`: The description of the event
* (optional) `location`: String representation of the location of the event
* (optional) `start`: Date and time of the events start
* (optional) `end`: Date and time of the events end

Example:

```json
{
  "id": 1,
  "identifier": "2019/00017",
  "name": "Name of the event",
  "description": "A very cool test event",
  "location": "Ernst-Happel-Stadion",
  "start": "2019-09-05T14:30Z",
  "end": "2019-09-05T22:45Z"
}
```

## Data type `Staff`

Properties:

* `id`: The ID of the staff member
* (optional) `identifier`: The identifier of the staff member
* `name`: The name of the staff member
* (optional) `phone`: The telephone number of the staff member
* (optional) `email`: The email address of the staff member
* (optional) `startLocation`: String representation of the staff members start location
* (optional) `startTime`: Date and time of the staff members start
* (optional) `comment`: Comment containing additional information about the staff member
* (optional) `section`: The section where the staff member is working
* `status`: The current status of the staff member

Example:

```json
{
  "id": 1,
  "identifier": "9999",
  "name": "John Doe",
  "phone": "+43 (660) 123456789",
  "email": "john.doe@foobar.com",
  "startLocation": "KSS",
  "startTime": "2019-09-05T14:30Z",
  "comment": "14:30 - 22:45; Start: KSS; MA mit Führerschein Klasse C",
  "section": "EL",
  "status": "DEREGISTERED"
}
```

## CRUD of events

### Create an event

* Path: `/api/private/event/new`
* HTTP method: `POST`

To create a new event the request body has to contain an [Event](#data-type-event) object in JSON format.
Only the `id` value must not be given,
because it will get generated automatically.

In case of success (HTTP status code `201`) the server responds with the created
[Event](#data-type-event) object in JSON format.

### Get event information

* Path: `/api/private/event/<eventId>`
* HTTP method: `GET`

In case of success (HTTP status code `200`) the server responds with the searched
[Event](#data-type-event) object in JSON format.

### Update an event

* Path: `/api/private/event/update/<eventId>`
* HTTP method: `PUT`

To update an event the request body has to contain an [Event](#data-type-event) object in JSON format.
Only the `id` value must not be given,
because it is part of the request URL.

In case of success (HTTP status code `200`) the server responds with the updated
[Event](#data-type-event) object in JSON format.

### Delete an event

* Path: `/api/private/event/delete/<eventId>`
* HTTP method: `DELETE`

In case of success (HTTP status code `204`) the server responds with an empty response body.

### Import an event

* Path: `/api/private/event/import`
* HTTP method: `POST`

This method imports a new event or updates an existing event by uploading a import file.
The following file types are allowed:

* iCalendar files (`.ical`, `.ics`, `.ifb`, `.iCalendar`)

In case of success the server responds with the created (HTTP status code `201`)
or updated (HTTP status code `200`) [Event](#data-type-event) object in JSON format.

## CRUD of staff for an event

### Add a staff member to an event

* Path: `/api/private/staff/add/<eventId>`
* HTTP method: `POST`

To create a new staff member for an event the request body has to contain a
[Staff](#data-type-staff) object in JSON format.
Only the `id` and `status` values must not be given,
because they will get generated automatically.

In case of success (HTTP status code `201`) the server responds with the created
[Staff](#data-type-staff) object in JSON format.

### Get staff information

* Path: `/api/private/staff/info/<staffId>`
* HTTP method: `GET`

In case of success (HTTP status code `200`) the server responds with the searched
[Staff](#data-type-staff) object in JSON format.

### Update a staff member

* Path: `/api/private/staff/update/<staffId>`
* HTTP method: `PUT`

To update a staff member for an event the request body has to contain a
[Staff](#data-type-staff) object in JSON format.
Only the `id` value must not be given,
because it is part of the request URL.

In case of success (HTTP status code `200`) the server responds with the updated
[Staff](#data-type-staff) object in JSON format.

### Delete a staff member

* Path: `/api/private/staff/delete/<staffId>`
* HTTP method: `DELETE`

In case of success (HTTP status code `204`) the server responds with an empty response body.

### Import a staff member

* Path: `/api/private/staff/import/<eventId>`
* HTTP method: `POST`

This method imports a new staff member or updates an existing one by uploading a import file.
The following file types are allowed:

* Comma separated value files (`.csv`)

In case of success the server responds with the created (HTTP status code `201`)
or updated (HTTP status code `200`) [Staff](#data-type-staff) object in JSON format.
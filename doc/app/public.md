# Public API for App

## Get all events

* Path: `/api/v1/public/events`
* HTTP method: `GET`

In case of success (HTTP status code `200`) the server responds with a list of
[Event](../types.md#data-type-event) objects as follows:

```json
{
  "events": [
    {
      "id": 1,
      "externalId": "2019/00017",
      "name": "Name of event 1",
      "description": "A very cool test event",
      "location": "Ernst-Happel-Stadion",
      "start": "2019-09-05T14:30Z",
      "end": "2019-09-05T22:45Z"
    }, {
      "id": 2,
      "externalId": "2019/00095",
      "name": "Name of event 2",
      "description": "A very cool test event",
      "location": "Rathausplatz",
      "start": "2019-05-05T09:00Z",
      "end": "2019-05-05T20:00Z"
    }
  ]
}
```

## Get staff list of event

* Path: `/api/v1/public/staff/<eventId>`
* HTTP method: `GET`

In case of success (HTTP status code `200`) the server responds with a list of
[Staff](../types.md#data-type-staff) objects as follows:

```json
{
  "staff": [
    {
      "id": 1,
      "externalId": "9999",
      "name": "John Doe",
      "phone": "+43 (660) 123456789",
      "email": "john.doe@foobar.com",
      "startLocation": "KSS",
      "startTime": "2019-09-05T14:30Z",
      "comment": "14:30 - 22:45; Start: KSS; MA mit Führerschein Klasse C",
      "section": "EL",
      "status": "NOT_REGISTERED"
    }, {
      "id": 2,
      "externalId": "9998",
      "name": "Jane Doe",
      "phone": "+43 (660) 987654321",
      "email": "jane.doe@foobar.com",
      "startLocation": "KSS",
      "startTime": "2019-09-05T14:30Z",
      "comment": "15:30 - 22:45; Start: direkt;",
      "section": "EL",
      "status": "REGISTERED_WITH_MATERIAL"
    }
  ]
}
```

## Set attendance status of staff for event

* Path: `/api/v1/public/staff/status/<staffId>`
* HTTP method: `POST`

The request body has to contain a [StaffStatus](../types.md#enum-staffstatus) value in JSON format.

Example request:

```json
{
  "status": "REGISTERED"
}
```

In case of success (HTTP status code `200`) the server responds with the updated
[Staff](../types.md#data-type-staff) objects as follows:

```json
{
  "id": 1,
  "externalId": "9999",
  "name": "John Doe",
  "phone": "+43 (660) 123456789",
  "email": "john.doe@foobar.com",
  "startLocation": "KSS",
  "startTime": "2019-09-05T14:30Z",
  "comment": "14:30 - 22:45; Start: KSS; MA mit Führerschein Klasse C",
  "section": "EL",
  "status": "REGISTERED"
}
```
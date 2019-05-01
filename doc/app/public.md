# Public API for App

## Enum `Status`

The status enumeration contains the following possible values:

* `NOT_REGISTERED`
* `REGISTERED`
* `REGISTERED_GOT_MATERIAL`
* `DEREGISTERED`

## Get all events

* Path: `/api/public/events`
* HTTP method: `GET`

In case of success (HTTP status code `200`) the server responds with following data in JSON format:

```json
{
  "events": [
    {
      "id": 1,
      "name": "Name of event 1"
    }, {
      "id": 2,
      "name": "Name of event 2"
    }
  ]
}
```

The response is basically a list of events with it's IDs and names,
so that the app can access staff data for a single event.

## Get staff list of event

* Path: `/api/public/staff/<eventId>`
* HTTP method: `GET`

In case of success (HTTP status code `200`) the server responds with following data in JSON format:

```json
{
  "staff": [
    {
      "id": 1,
      "name": "John Doe",
      "status": "NOT_REGISTERED"
    }, {
      "id": 2,
      "name": "Jane Doe",
      "status": "REGISTERED_GOT_MATERIAL"
    }
  ]
}
```

Every staff member contains the ID, the name and the current [status](#enum-status).

## Set attendance status of staff for event

* Path: `/api/public/staff/status/<staffId>`
* HTTP method: `PUT`

The request body has to contain a [status](#enum-status) value in JSON format.

In case of success (HTTP status code `200`) the server responds with following data in JSON format:

```json
{
  "id": 1,
  "name": "John Doe",
  "status": "REGISTERED"
}
```
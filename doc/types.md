# Data types and enums for the Ikarus backend

## Enum `StaffStatus`

The status enumeration contains the following possible values:

* `NOT_REGISTERED`
* `REGISTERED`
* `REGISTERED_WITH_MATERIAL`
* `DEREGISTERED`

## Data type `Event`

Properties:

* `id`: The ID of the event
* (optional) `externalId`: The external ID of the event
* `name`: The name of the event
* (optional) `description`: The description of the event
* (optional) `location`: String representation of the location of the event
* (optional) `start`: Date and time of the events start
* (optional) `end`: Date and time of the events end

Example:

```json
{
  "id": 1,
  "externalId": "2019/00017",
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
* (optional) `externalId`: The external ID of the staff member (for example employee number)
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
  "externalId": "9999",
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
FactCast example "BOOKMARX"




### Event Specifications:

#### Bookmark Created

Schema:

{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "definitions": {},
  "id": "http://example.com/example.json",
  "properties": {
    "bookmarkId": {
      "id": "/properties/bookmarkId",
      "type": "string"
    },
    "description": {
      "id": "/properties/description",
      "type": "string"
    },
    "tags": {
      "id": "/properties/tags",
      "items": {
        "id": "/properties/tags/items",
        "type": "string"
      },
      "type": "array"
    },
    "title": {
      "id": "/properties/title",
      "type": "string"
    },
    "url": {
      "id": "/properties/url",
      "type": "string"
    }
  },
  "type": "object"
}

Example:

{
  "bookmarkId": "8183982171",
  "title": "ibm",
  "url": "http://ibm.com",
  "description": "the big blue",
  "tags": [
    "home",
    "green"
  ]
}

#### Bookmark Changed

{
  "bookmarkId": "8183982171",
  "title": "ibm",
  "url": "http://ibm.com",
  "description": "the big blue",
}

#### Tag Added

{
  "bookmarkId": "8183982171",
  "tag": "home"
}

#### Tag Removed

{
  "bookmarkId": "8183982171",
  "tag": "home"
}

#### Bookmark Deleted

{
  "bookmarkId": "8183982171"
}
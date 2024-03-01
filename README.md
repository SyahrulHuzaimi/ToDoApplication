
JWT:
On login, create refresh and session tokens, session token has short life, refresh token can have long life, and is stored in database.

On requests, send session token, use as normal.
When session token expires, request new session token by sending refresh token. New session and refresh tokens get made, refresh token gets updated in DB.

On logout & password change
Delete refresh token in DB.

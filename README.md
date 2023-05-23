# AuthExample
Example Sping Boot program which uses JWT

Currently available endpoints:<br>
/api/v1/login<br>
/api/v1/register<br>
/api/v1/forgetpassword (incomplete)<br>
/api/v1/sessions/renew (unimplemented)<br>
/api/v1/sessions/active (unimplemented)<br>
/api/v1/sessions/revoke (unimplemented)<br>

For this example the tokens are generated with the following params:
IssuedAt -> Current time truncated to ChronoUnit.SECONDS
Expiration -> 3 minutes after IssuedAt

JWTId -> Random UUID which is used to ensure that a token has not been replayed or reused
Signed with HMAC256 SECRET_KEY

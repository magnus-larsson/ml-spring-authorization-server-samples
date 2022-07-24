# Samples

## Messages Sample

The messages sample integrates `spring-security-oauth2-resource-server` with *Spring Authorization Server*.

The username is `user1` and the password is `password`.

### Run the Sample

1. Run Authorization Server
   
   ```
   ./gradlew -b default-authorizationserver/samples-default-authorizationserver.gradle bootRun
   ```

2. Run Resource Server as a native image

   ```
   ./gradlew -b messages-resource/samples-messages-resource.gradle nativeCompile
   messages-resource/build/native/nativeCompile/messages-resource
   ```

3. Call the resource server

   ```
   AT=
   echo $AT
   AT=$(curl http://messaging-client:secret@localhost:9000/oauth2/token -d grant_type=client_credentials -s | jq .access_token -r)
   echo $AT
   curl http://localhost:8090/messages  -H "Authorization: Bearer $AT"
   ```
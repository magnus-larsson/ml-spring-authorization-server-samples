# Samples

## Messages Sample

The messages sample integrates `spring-security-oauth2-client` and `spring-security-oauth2-resource-server` with *Spring Authorization Server*.

The username is `user1` and the password is `password`.

### Run the Sample

1. Run Authorization Server
   
   ```
   ./gradlew -b default-authorizationserver/samples-default-authorizationserver.gradle bootRun
   ```

1. Run Client
   ```
   ./gradlew -b messages-client/samples-messages-client.gradle bootRun
   ```

1. Run Resource Server as a native image

   ```
   ./gradlew -b messages-resource/samples-messages-resource.gradle nativeCompile
   messages-resource/build/native/nativeCompile/messages-resource
   ```

1. Go to `http://127.0.0.1:8080`


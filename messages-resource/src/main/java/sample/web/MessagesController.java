/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@RestController
public class MessagesController {

	private static final Logger LOG = LoggerFactory.getLogger(MessagesController.class);

	@GetMapping("/messages")
	public Flux<String> getMessages() {
		return ReactiveSecurityContextHolder.getContext()
			.map(SecurityContext::getAuthentication)
			.doOnNext(auth -> logAuthorizationInfo(auth))
			.map(auth -> new String[] {"Message 1", "Message 2", "Message 3"})
			.flatMapMany(Flux::fromArray);
	}

	private void logAuthorizationInfo(Authentication auth) {
		Jwt jwtToken = ((JwtAuthenticationToken)auth).getToken();
		URL issuer = jwtToken.getIssuer();
		List<String> audience = jwtToken.getAudience();
		Object subject = jwtToken.getClaims().get("sub");
		Object scopes = jwtToken.getClaims().get("scope");
		Object expires = jwtToken.getClaims().get("exp");

		LOG.info("Authorization info: Subject: {}, scopes: {}, expires {}: issuer: {}, audience: {}", subject, scopes, expires, issuer, audience);
	}
}

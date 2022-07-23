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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@RestController
public class MessagesController {

	private static final Logger LOG = LoggerFactory.getLogger(MessagesController.class);

	@GetMapping("/messages")
	public String[] getMessages() {
		LOG.info("v3");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwtToken = ((JwtAuthenticationToken)auth).getToken();
		logAuthorizationInfo(jwtToken);
		return new String[] {"Message 1", "Message 2", "Message 3"};
	}

	private void logAuthorizationInfo(Jwt jwt) {
		if (jwt == null) {
			LOG.warn("No JWT supplied, running tests are we?");
		} else {
			URL issuer = jwt.getIssuer();
			List<String> audience = jwt.getAudience();
			Object subject = jwt.getClaims().get("sub");
			Object scopes = jwt.getClaims().get("scope");
			Object expires = jwt.getClaims().get("exp");

			LOG.info("Authorization info: Subject: {}, scopes: {}, expires {}: issuer: {}, audience: {}", subject, scopes, expires, issuer, audience);
		}
	}
}

package com.jdc.online.security;

import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import com.jdc.online.model.entity.Member;
import com.jdc.online.model.service.MemberService;

@RequestScoped
public class CustomLoginStore implements IdentityStore {
	
		@Inject
		private MemberService service;
		
		
	
		@Override
		public CredentialValidationResult validate(Credential credential) {
			
			UsernamePasswordCredential UsernameAndPassword = (UsernamePasswordCredential) credential;
			
			Member member = service.findByEmail(UsernameAndPassword.getCaller());
			
			
			
			
			return new CredentialValidationResult("email",Set.of(member.getRole().name()));
		}
}

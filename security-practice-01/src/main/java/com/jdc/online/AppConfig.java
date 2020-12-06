package com.jdc.online;

import javax.annotation.security.DeclareRoles;
import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;

@DataSourceDefinition(
		
		name="java:app/shop_db",
		url="jdbc:postgresql://localhost:5432/shop_db",
		className="org.postgresql.ds.PGSimpleDataSource",
		user="zwemanmyint",
		password="zwemanmyint"
		)

@DeclareRoles ({
		
		"Admin" , "Customer"
})

@FacesConfig(version = Version.JSF_2_3)

@CustomFormAuthenticationMechanismDefinition(
		
		loginToContinue = @LoginToContinue(
				
				loginPage = "/index.xhtml"
				)
		
		
		
		)



@ApplicationScoped
public class AppConfig {

}

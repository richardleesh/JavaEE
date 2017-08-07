package ca.seneca.jee.jsf.jpaexample.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ApplicationBean implements Serializable {

	private String currentThemeName = "afternoon";
	
	public void endSession() {
		System.out.println("About to invalidate Session");
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		try {
			ec.redirect(ec.getRequestContextPath() + "/pages/start.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getCurrentThemeName() {
		return currentThemeName;
	}

	public void setCurrentThemeName(String currentThemeName) {
		this.currentThemeName = currentThemeName;
	}
}

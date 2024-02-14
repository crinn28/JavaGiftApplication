package managedBean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

import com.example.dao.UserDAORemote;
import com.example.dto.LoginDTO;
import com.example.dto.UserDTO;
import com.example.exception.LoginException;
import javax.inject.Named;
import java.io.Serializable;

@Named(value ="loginBean")
@SessionScoped
public class LoginBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	LoginDTO loginDTO = new LoginDTO();
	
	@EJB
	UserDAORemote userDAORemote;

	UserDTO userDTO;

	public LoginDTO getLoginDTO() {
		return loginDTO;
	}

	public void setLoginDTO(LoginDTO loginDTO) {
		this.loginDTO = loginDTO;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String loginUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		try {
			userDTO = userDAORemote.loginUser(loginDTO);
			facesContext.getExternalContext().getSessionMap().put("userDTO", userDTO);
			
			switch(userDTO.getRole().getId()) {
			case 1:
				if(userDTO.isApproved())
					return "/shopAdmin/shopAdmin.xhtml?faces-redirect=true";
				else return "/index?faces-redirect=true";
			case 2:
				return "/customer/customer.xhtml?faces-redirect=true";
			case 3:
				if(userDTO.isApproved())
					return "/courier/courier.xhtml?faces-redirect=true";
				else return "/index?faces-redirect=true";
			case 4:
				return "/account/admin.xhtml?faces-redirect=true";
			default:
				return "/index?faces-redirect=true";
			}
			

		} catch (LoginException e) {
			System.out.println("Invalid username or password");
			facesContext.addMessage("loginForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.message(), null));
			return null;
		}

	}
	
	public String redirectToCreateAccount() {
		return "/account/register.xhtml?faces-redirect=true";
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		userDTO = null;

		return "/index?faces-redirect=true";
	}
}
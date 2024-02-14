package managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.example.dao.UserDAORemote;
import com.example.dto.UserDTO;

@Named(value = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	UserDAORemote userDAORemote;

	UserDTO userDTO;
	List<UserDTO> users;
	
	@PostConstruct
	public void initializeUsers() {
		userDTO = new UserDTO();
		users = userDAORemote.getAllWaitingUsers();
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String approveUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		try {
			userDTO.setApproved(true);
			userDTO = userDAORemote.update(userDTO);
			users = userDAORemote.getAllWaitingUsers();
			return "/account/admin.xhtml?faces-redirect=true";
		} catch (PersistenceException e) {
			facesContext.addMessage("createShopForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error: " + e.getMessage(), null));
			return null;
		}
	}
	
	public String deleteUser() {
		userDAORemote.delete(userDTO.getId());
		users = userDAORemote.getAllWaitingUsers();
		return "/account/admin?faces-redirect=true";
	}

}

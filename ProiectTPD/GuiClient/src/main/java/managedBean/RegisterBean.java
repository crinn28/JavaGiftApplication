package managedBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

import com.example.dao.CourierDAORemote;
import com.example.dao.RoleDAORemote;
import com.example.dao.UserDAORemote;
import com.example.dto.CourierDTO;
import com.example.dto.RoleDTO;
import com.example.dto.UserDTO;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value ="registerBean")
@SessionScoped
public class RegisterBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	UserDTO registerDTO = new UserDTO();
	List<RoleDTO> roleList = new ArrayList<>();
	
	@EJB
	UserDAORemote userDAORemote;
	
	@EJB
	CourierDAORemote courierDAORemote;
	
	@EJB
	RoleDAORemote roleDAORemote;

    RoleDTO selectedRole;
    
    @PostConstruct
	public void initialize() {
		selectedRole = new RoleDTO();
		roleList = roleDAORemote.getAllRolesWithoutAdmin();
	}
    
	public UserDTO getRegisterDTO() {
		return registerDTO;
	}

	public void setRegisterDTO(UserDTO registerDTO) {
		this.registerDTO = registerDTO;
	}

	public RoleDTO getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(RoleDTO selectedRole) {
		this.selectedRole = selectedRole;
	}

	public RoleDAORemote getRoleDAORemote() {
		return roleDAORemote;
	}

	public void setRoleDAORemote(RoleDAORemote roleDAORemote) {
		this.roleDAORemote = roleDAORemote;
	}

	public List<RoleDTO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleDTO> roleList) {
		this.roleList = roleList;
	}

	public String registerUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			registerDTO.setRole(roleDAORemote.findById(selectedRole.getId()));
			registerDTO = userDAORemote.create(registerDTO);
			
			if(registerDTO.getRole().getId() == 3)
				courierDAORemote.create(new CourierDTO(registerDTO));
		    return "/index?faces-redirect=true";
			
		} catch (Exception e) {
			facesContext.addMessage("registerForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			return null;
		}

	}
}
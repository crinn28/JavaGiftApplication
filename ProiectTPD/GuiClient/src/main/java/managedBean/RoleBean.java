package managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.example.dao.RoleDAORemote;
import com.example.dto.RoleDTO;

@Named(value = "roleBean")
@SessionScoped
public class RoleBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RoleDTO> roleList;
	
	RoleDTO roleDTO = new RoleDTO();

	@EJB
	RoleDAORemote roleDAORemote;

	@PostConstruct
	public void init() {
		roleList = roleDAORemote.findAll();
	}

	public List<RoleDTO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleDTO> roleList) {
		this.roleList = roleList;
	}

	public RoleDTO getRoleDTO() {
		return roleDTO;
	}

	public void setRoleDTO(RoleDTO roleDTO) {
		this.roleDTO = roleDTO;
	}

	public RoleDAORemote getRoleDAORemote() {
		return roleDAORemote;
	}

	public void setRoleDAORemote(RoleDAORemote roleDAORemote) {
		this.roleDAORemote = roleDAORemote;
	}
}

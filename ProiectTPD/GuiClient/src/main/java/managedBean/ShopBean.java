package managedBean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.example.dao.ShopDAORemote;
import com.example.dto.ShopDTO;
import com.example.dto.UserDTO;
import com.example.exception.InvalidPhoneNumberException;

@Named(value = "shopBean")
@SessionScoped
public class ShopBean implements Serializable {

	private static final long serialVersionUID = 1L;

	boolean shopAlreadyExists;
	ShopDTO shopDTO = new ShopDTO();

	@EJB
	ShopDAORemote shopDAORemote;

	UserDTO userDTO;

	public ShopDTO getShopDTO() {
		return shopDTO;
	}

	@PostConstruct
	public void init() {
		shopDTO = new ShopDTO();
	}

	public void setShopDTO(ShopDTO shopDTO) {
		this.shopDTO = shopDTO;
	}

	public ShopDAORemote getShopDAORemote() {
		return shopDAORemote;
	}

	public void setShopDAORemote(ShopDAORemote shopDAORemote) {
		this.shopDAORemote = shopDAORemote;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String redirectToCreateShop() {
		return "/shopAdmin/createShop.xhtml?faces-redirect=true";
	}
	
	public String redirectToCreateProduct() {
		return "/shopAdmin/createProduct.xhtml?faces-redirect=true";
	}

	public boolean hasUserShop() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		userDTO = (UserDTO) facesContext.getExternalContext().getSessionMap().get("userDTO");
		shopDTO = shopDAORemote.getShopByUser(userDTO);
		if (shopDTO == null) {
			shopAlreadyExists = false;
			shopDTO = new ShopDTO();
			return false;
		}
		
		facesContext.getExternalContext().getSessionMap().put("shopDTO", shopDTO);
		shopAlreadyExists = true;
		return true;
	}

	public String createShop() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			userDTO = (UserDTO) facesContext.getExternalContext().getSessionMap().get("userDTO");
			shopDTO.setUser(userDTO);
			if (shopAlreadyExists == false) {
				shopDTO = shopDAORemote.create(shopDTO);
				return "/shopAdmin/shopAdmin.xhtml?faces-redirect=true";
			}
		
			shopDTO = shopDAORemote.update(shopDTO);
			return "/shopAdmin/shopAdmin.xhtml?faces-redirect=true";
		} catch (PersistenceException e) {
			facesContext.addMessage("createShopForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error: " + e.getMessage(), null));
			return null;
		}
		catch (InvalidPhoneNumberException ex) {
			facesContext.addMessage("createShopForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
			return null;
		}
	}

}

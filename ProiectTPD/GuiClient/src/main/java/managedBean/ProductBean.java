package managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.example.dao.ProductDAORemote;
import com.example.dto.CategoryDTO;
import com.example.dto.ProductDTO;
import com.example.dto.ShopDTO;
import com.example.exception.InvalidQuantityException;

@Named(value = "productBean")
@SessionScoped
public class ProductBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean wasLinkClicked;
	boolean productAlreadyExists;
	int productID;

	ProductDTO productDTO;

	List<ProductDTO> productList;

	private CategoryDTO selectedCategory;

	private ShopDTO shopDTO;

	@EJB
	ProductDAORemote productDAORemote;

	@PostConstruct
	public void initializeProducts() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		shopDTO = (ShopDTO) facesContext.getExternalContext().getSessionMap().get("shopDTO");
		productList = new ArrayList<>();
		if (shopDTO != null)
			productList = productDAORemote.getShopProducts(shopDTO.getId());
		productDTO = new ProductDTO();
		selectedCategory = new CategoryDTO();
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public ProductDAORemote getProductDAORemote() {
		return productDAORemote;
	}

	public void setProductDAORemote(ProductDAORemote productDAORemote) {
		this.productDAORemote = productDAORemote;
	}

	public CategoryDTO getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(CategoryDTO selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public boolean isWasLinkClicked() {
		return wasLinkClicked;
	}

	public void setWasLinkClicked(boolean wasLinkClicked) {
		this.wasLinkClicked = wasLinkClicked;
	}

	public List<ProductDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDTO> productList) {
		this.productList = productList;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public ShopDTO getShopDTO() {
		return shopDTO;
	}

	public void setShopDTO(ShopDTO shopDTO) {
		this.shopDTO = shopDTO;
	}

	public boolean productExists() {
		if (productDTO.getId() == 0) {
			productAlreadyExists = false;
			productDTO = new ProductDTO();
			return false;
		}
		productAlreadyExists = true;
		return true;
	}

	public String redirectToCreateCategory() {
		wasLinkClicked = true;
		return "/shopAdmin/createCategory.xhtml?faces-redirect=true";
	}

	public String setProductFromTable() {
		selectedCategory = productDAORemote.findById(productDTO.getId()).getCategory();
		return "/shopAdmin/createProduct?faces-redirect=true";
	}

	public String deleteProduct() {
		productDAORemote.delete(productDTO.getId());
		productList = productDAORemote.getShopProducts(shopDTO.getId());
		productDTO = new ProductDTO();
		return "/shopAdmin/shopAdmin?faces-redirect=true";
	}

	public String createProduct() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ProductDTO currentProduct = productDTO;
		try {
			productDTO.setCategory(selectedCategory);
			productDTO.setShop(shopDTO);

			if (productExists() == false) {
				productDTO = productDAORemote.create(currentProduct);
				productList.add(productDTO);
				productList = productDAORemote.getShopProducts(shopDTO.getId());
				productDTO = new ProductDTO();
				return "/shopAdmin/shopAdmin.xhtml?faces-redirect=true";
			}
			productDTO = productDAORemote.update(currentProduct);
			productList = productDAORemote.getShopProducts(shopDTO.getId());
			productDTO = new ProductDTO();
			return "/shopAdmin/shopAdmin.xhtml?faces-redirect=true";

		} catch (PersistenceException e) {
			facesContext.addMessage("createProductForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error: " + e.getMessage(), null));
			return null;
		} catch (InvalidQuantityException ex) {
			facesContext.addMessage("createProductForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
			return null;
		}
	}

	public String redirectToShopAdminPage() {
		productDTO = new ProductDTO();
		return "/shopAdmin/shopAdmin.xhtml?faces-redirect=true";
	}
}

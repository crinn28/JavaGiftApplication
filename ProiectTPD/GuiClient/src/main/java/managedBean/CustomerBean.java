package managedBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.example.dao.ProductDAORemote;
import com.example.dao.ShopDAORemote;
import com.example.dto.ProductShoppingCartDTO;
import com.example.dto.ShopDTO;

@Named(value = "customerBean")
@SessionScoped
public class CustomerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ShopDTO> shopList;
	private ShopDTO selectedShop = new ShopDTO();

	private String shopId;

	private String searchKeyword;

	@EJB
	ShopDAORemote shopDAORemote;

	@EJB
	ProductDAORemote productDAORemote;

	List<ProductShoppingCartDTO> productList;

	@PostConstruct
	public void init() {
		shopList = shopDAORemote.findAll();
	}

	public List<ShopDTO> getShopList() {
		return shopList;
	}

	public void setShopList(List<ShopDTO> shopList) {
		this.shopList = shopList;
	}

	public ShopDTO getSelectedShop() {
		return selectedShop;
	}

	public List<ProductShoppingCartDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductShoppingCartDTO> productList) {
		this.productList = productList;
	}

	public void setSelectedShop(ShopDTO selectedShop) {
		this.selectedShop = selectedShop;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public void initializeShopProducts() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("shopId", shopId);
		
		shopId = paramMap.get("shopId");

		if (shopId != null) {
			if (searchKeyword == null || searchKeyword.isEmpty()) {
		        productList = productDAORemote.getShoppinCartProducts(Integer.parseInt(shopId));
		    } else {
		        productList = productDAORemote.searchProducts(Integer.parseInt(shopId), searchKeyword);
		        searchKeyword = "";
		    }
			selectedShop = shopDAORemote.findById(Integer.parseInt(shopId));
		}
	}

	public void increment(ProductShoppingCartDTO product) {
		product.setDesiredQuantity(product.getDesiredQuantity() + 1);
	}

	public void decrement(ProductShoppingCartDTO product) {
		if (product.getDesiredQuantity() > 0)
			product.setDesiredQuantity(product.getDesiredQuantity() - 1);
	}

	public void buyProduct(ProductShoppingCartDTO product) {
		product.setDesiredQuantity(0);
	}

	public boolean isInStock(ProductShoppingCartDTO product) {
		if (product.getQuantity() == 0)
			return false;
		return true;
	}

	public String redirectToShoppingCart() {
		return "/customer/shoppingCart.xhtml?faces-redirect=true";
	}

	public String redirectToMainPage() {
		return "/customer/customer.xhtml?faces-redirect=true";
	}

	public String redirectToOrders() {
		return "/customer/order.xhtml?faces-redirect=true";
	}

	public String searchProducts() {
		
		FacesContext context = FacesContext.getCurrentInstance();
	    Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
	    
	    String originalShopId = shopId;

	    if (paramMap.containsKey("shopId")) {
	        shopId = paramMap.get("shopId");
	    } else {
	        shopId = originalShopId;
	    }
	
	    FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("shopId", shopId);

	    shopId = originalShopId;

	    return "/customer/shopProducts.xhtml?faces-redirect=true&shopId=" + shopId;
	}

}

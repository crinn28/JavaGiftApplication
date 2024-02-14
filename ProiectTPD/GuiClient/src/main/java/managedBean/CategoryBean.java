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

import com.example.dao.CategoryDAORemote;
import com.example.dto.CategoryDTO;

@Named(value = "categoryBean")
@SessionScoped
public class CategoryBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CategoryDTO> categoryList;
	private CategoryDTO selectedCategory = new CategoryDTO();

	boolean categoryAlreadyExists;
	CategoryDTO categoryDTO = new CategoryDTO();

	@EJB
	CategoryDAORemote categoryDAORemote;

	@PostConstruct
	public void init() {
		categoryList = categoryDAORemote.findAll();
	}

	public List<CategoryDTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryDTO> categoryList) {
		this.categoryList = categoryList;
	}

	public CategoryDTO getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(CategoryDTO selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public boolean isCategoryAlreadyExists() {
		return categoryAlreadyExists;
	}

	public void setCategoryAlreadyExists(boolean categoryAlreadyExists) {
		this.categoryAlreadyExists = categoryAlreadyExists;
	}

	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}

	public CategoryDAORemote getCategoryDAORemote() {
		return categoryDAORemote;
	}

	public void setCategoryDAORemote(CategoryDAORemote categoryDAORemote) {
		this.categoryDAORemote = categoryDAORemote;
	}

	public String createCategory() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			categoryDAORemote.create(categoryDTO);
			categoryList = categoryDAORemote.findAll();
			return "/shopAdmin/createProduct.xhtml?faces-redirect=true";
		} catch (PersistenceException e) {
			facesContext.addMessage("createCategoryForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error: " + e.getMessage(), null));
			return null;
		}
	}

	public String cancel() {
		return "/shopAdmin/createProduct.xhtml?faces-redirect=true";
	}

}

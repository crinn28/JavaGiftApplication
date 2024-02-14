package converters;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.example.dao.CategoryDAORemote;
import com.example.dto.CategoryDTO;

@SuppressWarnings("rawtypes")
@FacesConverter("categoryConverter")
public class CategoryDTOConverter implements Converter {

	@EJB
	private CategoryDAORemote categoryDAORemote;
	
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        CategoryDTO c= new CategoryDTO();
        c = categoryDAORemote.findById(Integer.parseInt(value));
        System.out.print(c);
        return c;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        return String.valueOf(value);
    }
}
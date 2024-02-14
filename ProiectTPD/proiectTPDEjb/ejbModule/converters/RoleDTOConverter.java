package converters;

import java.util.List;
import java.util.Optional;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.example.dao.RoleDao;
import com.example.dto.RoleDTO;

@SuppressWarnings("rawtypes")
@FacesConverter("roleDTOConverter")
public class RoleDTOConverter implements Converter {

    @Inject
    private RoleDao roleService = new RoleDao(); // Inject your actual service

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
        	int roleId = Integer.parseInt(value);
        	
        	List<RoleDTO> roles = roleService.findAll();
        	Optional<RoleDTO> optionalRoleDTO = roles.stream()
                    .filter(role -> role.getId() == roleId)
                    .findFirst();

            return optionalRoleDTO.orElse(null);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof RoleDTO) {
            return String.valueOf(((RoleDTO) value).getId());
        }
        return null;
    }
}
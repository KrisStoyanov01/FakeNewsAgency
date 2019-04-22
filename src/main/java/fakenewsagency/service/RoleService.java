package fakenewsagency.service;

import fakenewsagency.domain.models.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {
    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}

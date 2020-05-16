package com.ascending.training.club.respository;

import com.ascending.training.club.model.Role;

import java.util.List;

public interface RoleDao {
    public abstract Role save(Role role);
    public abstract List<Role> getRoles();
    public abstract Role getById(Long id);
    public abstract int deleteById(Long id);
    public abstract int update(Role role);
}

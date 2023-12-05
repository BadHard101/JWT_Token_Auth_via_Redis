package com.example.studentsretake.Controllers;

import com.example.studentsretake.Entities.Role;
import com.example.studentsretake.Entities.User;
import com.example.studentsretake.Repos.RoleRepo;
import com.example.studentsretake.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RoleController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @RequestMapping(value = "/api/admin/setRole", method = RequestMethod.PUT)
    public ResponseEntity<String> setRole(@RequestParam String role, @RequestParam Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Удаление ролей из базы данных
        user.getRoles().forEach(roleRepo::delete);

        // Очистка ролей пользователя
        user.removeAllRoles();

        // Установка новой роли
        if (role.equals("ROLE_ADMIN")) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepo.save(Role.builder().name("ROLE_ADMIN").build()));
            user.setRoles(roles);
        } else if (role.equals("ROLE_SELLER")) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepo.save(Role.builder().name("ROLE_SELLER").build()));
            user.setRoles(roles);
        } else if (role.equals("ROLE_USER")) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepo.save(Role.builder().name("ROLE_USER").build()));
            user.setRoles(roles);
        }

        userRepo.save(user);
        return ResponseEntity.ok("Roles successfully updated");
    }


}

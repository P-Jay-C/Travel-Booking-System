package com.amalitech.org.userservice.user;
import com.amalitech.org.userservice.service.AuthService;
import com.amalitech.org.userservice.util.StatusCode;
import com.amalitech.org.userservice.util.Result;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/users")
public class AuthController {
    private final AuthService userService;

    public AuthController(AuthService userService ){
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public Result findUserById(@PathVariable String userId) {
        AppUser user = userService.findById(new ObjectId(userId));

        return new Result(true, StatusCode.SUCCESS, "Find Success", user);
    }

    @GetMapping
    public Result findAllUsers() {
        List<AppUser> users = userService.findAll();

        return new Result(true, StatusCode.SUCCESS, "Find All Success", users);
    }

    @PostMapping
    public Result addUser(@Valid @RequestBody AppUser user) {
        AppUser savedUser = userService.save(user);

        return new Result(true, StatusCode.SUCCESS, "Add Success", savedUser);
    }

    @PutMapping("/{userId}")
    public Result updateUpdateUser(@PathVariable String userId, @Valid @RequestBody AppUser appUser) {

        AppUser updatedUser = userService.update(new ObjectId(userId), appUser);

        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedUser);
    }

    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable String userId) {
        userService.delete(new ObjectId(userId));
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}

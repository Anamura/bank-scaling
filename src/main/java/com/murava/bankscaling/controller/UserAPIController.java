package com.murava.bankscaling.controller;

import com.murava.bankscaling.dto.EmailData;
import com.murava.bankscaling.dto.PhoneData;
import com.murava.bankscaling.dto.User;
import com.murava.bankscaling.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserAPIController {

    @Autowired
    private UserService userService;

    @ApiOperation(authorizations = {@Authorization(value = "jwtAuth")}, tags = {}, value = "")
    @PutMapping(path = "/{userId}/update")
    public void updateUser(@PathVariable("userId") Long userId, @RequestBody @Valid User user) {
        userService.updateUser(userId, user);
    }

    @GetMapping
    public ResponseEntity<Page<User>> searchUsers(@RequestParam("dateOfBirth") Date dateOfBirth, @RequestParam("name") String name,
                                                  @RequestParam("phone") Long phone, @RequestParam("email") String email,
                                                  @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                  @RequestParam(value = "size", defaultValue = "30", required = false) int size,
                                                  @RequestParam(value = "sort", defaultValue = "name", required = false) String sort) {
        Page<User> users = userService.findAllPageable(dateOfBirth, name, phone, email, PageRequest.of(page, size, DESC, sort));
        return ResponseEntity.ok(users);
    }

    @SubscribeMapping("/register/messages")
    User createUser(@RequestBody @Valid User user) {
        return userService.registerNewUser(user);
    }

    @PostMapping(path = "/{userId}/email")
    public @ResponseBody
    EmailData addEmail(@PathVariable("userId") Long userId, @RequestBody @Valid EmailData email,
                       @RequestParam(value = "author") Long accountId) {
        return userService.addEmail(userId, email, accountId);
    }

    @GetMapping(path = "/{userId}/phone")
    public @ResponseBody
    ResponseEntity<List<PhoneData>> fetch(@PathVariable("userId") Long userId) {
        throw new UnsupportedOperationException();
    }

}
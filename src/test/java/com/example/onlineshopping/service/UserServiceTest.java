package com.example.onlineshopping.service;

import com.example.onlineshopping.model.AppUser;
import com.example.onlineshopping.model.AppUserRole;
import com.example.onlineshopping.model.Items;
import com.example.onlineshopping.model.Order;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.security.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    Logger log;
    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testGetAllUser(){
        AppUser user1= AppUser.builder().id(1).build();
        AppUser user2 = AppUser.builder().id(2).build();
        given(userService.getAllUsers()).willReturn(asList(user1, user2));
        //When
        List<AppUser> allActual = userService.getAllUsers();

        //Then
        assertThat(allActual).containsExactly(user1, user2);
    }
    @Test
    public void testGetUserById(){
        AppUser user=AppUser.builder().id(1).build();
        when(userService.getUserById(1)).thenReturn(Optional.of(user));
        AppUser user1 = userService.getUserById(1).get();
        Assertions.assertEquals(user.getId(),user1.getId());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testSaveUser(){
        AppUser user = new AppUser("Dr Jane Doe", 42L,"janee","789465321", Arrays.asList(AppUserRole.ROLE_ADMIN));
        when(userService.saveUser(Mockito.any(AppUser.class))).thenReturn(user);
        userService.saveUser(user);

    }
    @Test
    void testDeleteUserByUsername() {
        AppUser expected = AppUser.builder().id(1).username("Jane").build();
        when(userService.getUserById(1)).thenReturn(Optional.of(expected));
        AppUser actual = userService.getUserById(1).get();

        Assertions.assertTrue(userService.delete("Jane"));

        verify(userRepository, times(1)).deleteById(1);

    }

//    @Test
//    void testSignin() {
//        when(userRepository.findByUsername(anyString())).thenReturn(new AppUser(Integer.valueOf(0), "fullName", "address", Long.valueOf(1), "username", "password", "token", new Order(Integer.valueOf(0), LocalDateTime.of(2022, Month.APRIL, 21, 16, 3, 27), Long.valueOf(1), Boolean.TRUE, Boolean.TRUE, null, Arrays.<Items>asList(new Items(0, "name", Long.valueOf(1), "category", null))), Arrays.asList(AppUserRole.ROLE_ADMIN)));
//        when(jwtTokenProvider.createToken(anyString(), any())).thenReturn("createTokenResponse");
//
//        String result = userService.signin("username", "password");
//        Assertions.assertEquals("replaceMeWithExpectedResult", result);
//    }

//    @Test
//    void testSignup() {
//        when(userRepository.existsByUsername(anyString())).thenReturn(true);
//        when(jwtTokenProvider.createToken(anyString(), any())).thenReturn("createTokenResponse");
//
//        String result = userService.signup(new AppUser(Integer.valueOf(0), "fullName", "address", Long.valueOf(1), "username", "password", "token", new Order(Integer.valueOf(0), LocalDateTime.of(2022, Month.APRIL, 21, 16, 3, 27), Long.valueOf(1), Boolean.TRUE, Boolean.TRUE, null, Arrays.<Items>asList(new Items(0, "name", Long.valueOf(1), "category", null))), Arrays.asList(new UserRole("ROLE_ADMIN"))));
//        Assertions.assertEquals("replaceMeWithExpectedResult", result);
//    }
//
//    @Test
//    void testDelete() {
//        userService.delete("username");
//    }
//
//    @Test
//    void testSearch() {
//        when(userRepository.findByUsername(anyString())).thenReturn(new AppUser(Integer.valueOf(0), "fullName", "address", Long.valueOf(1), "username", "password", "token", new Order(Integer.valueOf(0), LocalDateTime.of(2022, Month.APRIL, 21, 16, 3, 27), Long.valueOf(1), Boolean.TRUE, Boolean.TRUE, null, Arrays.<Items>asList(new Items(0, "name", Long.valueOf(1), "category", null))), Arrays.asList(new UserRole("ROLE_ADMIN"))));
//
//        AppUser result = userService.search("username");
//        Assertions.assertEquals(new AppUser(Integer.valueOf(0), "fullName", "address", Long.valueOf(1), "username", "password", "token", new Order(Integer.valueOf(0), LocalDateTime.of(2022, Month.APRIL, 21, 16, 3, 27), Long.valueOf(1), Boolean.TRUE, Boolean.TRUE, null, Arrays.<Items>asList(new Items(0, "name", Long.valueOf(1), "category", null))), Arrays.asList(new UserRole("ROLE_ADMIN"))), result);
//    }
//
//    @Test
//    void testWhoami() {
//        when(userRepository.findByUsername(anyString())).thenReturn(new AppUser(Integer.valueOf(0), "fullName", "address", Long.valueOf(1), "username", "password", "token", new Order(Integer.valueOf(0), LocalDateTime.of(2022, Month.APRIL, 21, 16, 3, 27), Long.valueOf(1), Boolean.TRUE, Boolean.TRUE, null, Arrays.<Items>asList(new Items(0, "name", Long.valueOf(1), "category", null))),  Arrays.asList(new UserRole("ROLE_ADMIN"))));
//        when(jwtTokenProvider.getUsername(anyString())).thenReturn("getUsernameResponse");
//        when(jwtTokenProvider.resolveToken(any())).thenReturn("resolveTokenResponse");
//
//        AppUser result = userService.whoami(null);
//        Assertions.assertEquals(new AppUser(Integer.valueOf(0), "fullName", "address", Long.valueOf(1), "username", "password", "token", new Order(Integer.valueOf(0), LocalDateTime.of(2022, Month.APRIL, 21, 16, 3, 27), Long.valueOf(1), Boolean.TRUE, Boolean.TRUE, null, Arrays.<Items>asList(new Items(0, "name", Long.valueOf(1), "category", null))),  Arrays.asList(new UserRole("ROLE_ADMIN"))), result);
//    }
//
//    @Test
//    void testRefresh() {
//        when(userRepository.findByUsername(anyString())).thenReturn(new AppUser(Integer.valueOf(0), "fullName", "address", Long.valueOf(1), "username", "password", "token", new Order(Integer.valueOf(0), LocalDateTime.of(2022, Month.APRIL, 21, 16, 3, 27), Long.valueOf(1), Boolean.TRUE, Boolean.TRUE, null, Arrays.<Items>asList(new Items(0, "name", Long.valueOf(1), "category", null))),  Arrays.asList(new UserRole("ROLE_ADMIN"))));
//        when(jwtTokenProvider.createToken(anyString(), any())).thenReturn("createTokenResponse");
//
//        String result = userService.refresh("username");
//        Assertions.assertEquals("replaceMeWithExpectedResult", result);
//    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
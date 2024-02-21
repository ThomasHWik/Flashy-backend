package com.flashy.server.api;

import com.flashy.server.AbstractTest;
import com.flashy.server.core.LoginResponseDTO;
import com.flashy.server.core.User;
import com.flashy.server.core.UserDTO;
import com.flashy.server.service.FlashyuserService;
import com.flashy.server.service.JWTService;

import org.aspectj.lang.annotation.AfterThrowing;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlashyuserControllerTest extends AbstractTest {

    @Autowired
    FlashyuserService flashyuserService;

    @Autowired
    JWTService jwtService;

    LoginResponseDTO loginResponseDTO;

    UserDTO user;
    @BeforeAll
    public void setUp() {
        super.setUp();
        user = new UserDTO();
        user.setPassword(UUID.randomUUID().toString());
        user.setUsername(UUID.randomUUID().toString());
    }



    @Test
    public void testRegister() throws Exception{
        String url = "/user/register";

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        loginResponseDTO = super.mapFromJson(content, LoginResponseDTO.class);
        assertNotEquals(null, loginResponseDTO.getMessage());
        assertNotEquals("", loginResponseDTO.getMessage());

        // test duplicate username
        inputJson = super.mapToJson(user);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);


        // delete user
        boolean res = flashyuserService.deleteUser(user.getUsername(), jwtService.getUsernameFromToken(loginResponseDTO.getMessage()));
        assertTrue(res);
    }

    @Test
    public void testLogin() throws Exception {
        String url = "/user/register";

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        loginResponseDTO = super.mapFromJson(content, LoginResponseDTO.class);
        assertNotEquals(null, loginResponseDTO.getMessage());
        assertNotEquals("", loginResponseDTO.getMessage());


        url = "/user/login";
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        inputJson = super.mapToJson(user);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        content = mvcResult.getResponse().getContentAsString();
        loginResponseDTO = super.mapFromJson(content, LoginResponseDTO.class);
        assertNotEquals(null, loginResponseDTO.getMessage());
        assertNotEquals("", loginResponseDTO.getMessage());

        // invalid login
        url = "/user/login";


        inputJson = super.mapToJson(new User(user.getUsername(), "wrongsPassword"));
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);


        boolean res = flashyuserService.deleteUser(user.getUsername(), jwtService.getUsernameFromToken(loginResponseDTO.getMessage()));
        assertTrue(res);
    }

    @Test
    public void testDeleteUser() throws Exception {

        String url = "/user/register";

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        loginResponseDTO = super.mapFromJson(content, LoginResponseDTO.class);

        // delete with invalid authorization
        url = "/user/delete/" + user.getUsername();
        mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url).header("Authorization", "Bearer " + "invalidToken").contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // registrer new user
        UserDTO user2 = new UserDTO();
        user2.setUsername(UUID.randomUUID().toString());
        user2.setPassword(UUID.randomUUID().toString());

        inputJson = super.mapToJson(user2);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
         status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
         content = mvcResult.getResponse().getContentAsString();
        LoginResponseDTO loginResponseDTO1 = super.mapFromJson(content, LoginResponseDTO.class);

        // delete with other users auth
        url = "/user/delete/" + user.getUsername();
        mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url).header("Authorization", "Bearer " + loginResponseDTO1.getMessage()).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // delete with authorization
        url = "/user/delete/" + user.getUsername();
        mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url).header("Authorization", "Bearer " + loginResponseDTO.getMessage()).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }

    @Test
    public void testCreateAdmin() throws Exception {
        String adminUsername = "admin";
        String adminPassword = "admin";

        UserDTO adminUser = new UserDTO();
        adminUser.setUsername(adminUsername);
        adminUser.setPassword(adminPassword);

        String url = "/user/admin/create";

        // invalid auth
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .header("Authorization", "Bearer " + "invalidToken")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // valid token but not authorized
        inputJson = super.mapToJson(user);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .header("Authorization", "Bearer " + jwtService.getToken(user.getUsername()))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);


        // valid token and authorized
        inputJson = super.mapToJson(user);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .header("Authorization", "Bearer " + jwtService.getToken(adminUsername))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // check duplicate username
        inputJson = super.mapToJson(user);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .header("Authorization", "Bearer " + jwtService.getToken(adminUsername))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);

        flashyuserService.deleteUser(user.getUsername(), adminUsername);


    }

    @Test
    public void testChangePassword() throws Exception {
        String adminUsername = "admin";
        String adminPassword = "admin";

        UserDTO adminUser = new UserDTO();
        adminUser.setUsername(adminUsername);
        adminUser.setPassword(adminPassword);

        String url = "/user/changepassword";

        // register user
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        LoginResponseDTO loginresponsedto1 = super.mapFromJson(mvcResult.getResponse().getContentAsString(), LoginResponseDTO.class);
        assertEquals(200, status);

        // change with invalid auth
        user.setPassword("newPassword");
         inputJson = super.mapToJson(user);
         mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                .header("Authorization", "Bearer " + "invalidToken")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

         status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // change with valid token but not authorized
        inputJson = super.mapToJson(user);
        mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                .header("Authorization", "Bearer " + jwtService.getToken("invalidUser"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);


        // change - valid token and authorized admin
        inputJson = super.mapToJson(user);
        mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                .header("Authorization", "Bearer " + jwtService.getToken(adminUsername))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // change - valid token and authorized user
        inputJson = super.mapToJson(user);
        mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                .header("Authorization", "Bearer " + loginresponsedto1.getMessage())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // change with invalid username
        UserDTO user2 = new UserDTO();
        user2.setUsername(UUID.randomUUID().toString());
        user2.setPassword("newPassword");
        inputJson = super.mapToJson(user2);
        mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                .header("Authorization", "Bearer " + loginresponsedto1.getMessage())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // test login with new password
        inputJson = super.mapToJson(user);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        flashyuserService.deleteUser(user.getUsername(), adminUsername);
    }

    @Test
    public void testGetAllAdmins() throws Exception {


        String adminUsername = "admin";
        String adminPassword = "admin";

        UserDTO adminUser = new UserDTO();
        adminUser.setUsername(adminUsername);
        adminUser.setPassword(adminPassword);

        user.setPassword(UUID.randomUUID().toString());

        String url = "/user/admin/create";

           // create admin
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .header("Authorization", "Bearer " + jwtService.getToken(adminUsername))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // get all admins
         mvcResult = mvc.perform(MockMvcRequestBuilders.get("/user/admin/getall")
                .header("Authorization", "Bearer " + jwtService.getToken(adminUsername))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

         status = mvcResult.getResponse().getStatus();
         List<String> admins = super.mapFromJson(mvcResult.getResponse().getContentAsString(), List.class);
         assertEquals(200, status);

         assertTrue(admins.contains(user.getUsername()));
         assertTrue(admins.contains(adminUsername));

         // with invalid token
            mvcResult = mvc.perform(MockMvcRequestBuilders.get("/user/admin/getall")
                    .header("Authorization", "Bearer " + "invalidToken")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            status = mvcResult.getResponse().getStatus();
            assertEquals(403, status);

            // with invalid user
            mvcResult = mvc.perform(MockMvcRequestBuilders.get("/user/admin/getall")
                    .header("Authorization", "Bearer " + jwtService.getToken(UUID.randomUUID().toString()))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            status = mvcResult.getResponse().getStatus();
            assertEquals(403, status);

        // delete user
        boolean res = flashyuserService.deleteUser(user.getUsername(), adminUsername);
        assertTrue(res);


    }

    @AfterThrowing
    public void tearDown() {
        flashyuserService.deleteUser(user.getUsername(), "admin");
    }



}

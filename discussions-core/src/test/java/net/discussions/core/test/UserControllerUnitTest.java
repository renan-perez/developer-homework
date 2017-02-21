package net.discussions.core.test;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

import net.discussions.core.business.UserBusiness;
import net.discussions.core.conf.AppWebConfiguration;
import net.discussions.core.conf.JPAConfiguration;
import net.discussions.core.controller.UserController;
import net.discussions.core.dao.GenericDAO;
import net.discussions.core.exception.SystemException;
import net.discussions.core.model.User;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, AppWebConfiguration.class, GenericDAO.class, JPAConfiguration.class})
@WebAppConfiguration
public class UserControllerUnitTest {
	
	private MockMvc mockMvc;
	
	@Mock
	@Autowired
    private UserBusiness userBusiness;
	
	@InjectMocks
	@Autowired
    private UserController userController;
	
	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .addFilters(new CORSFilter())
                .build();
    }
	
	@Test
	public void testListUsersSuccess() throws Exception, SystemException {
		List<User> users = Arrays.asList(new User("reperez.rp@gmail.com", "Renan Perez"));
		when(userBusiness.listUsers()).thenReturn(users);
		mockMvc.perform(get("/users/listUsers"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].email", is("reperez.rp@gmail.com")))
				.andExpect(jsonPath("$.content[0].name", is("Renan Perez")));
		verify(userBusiness, times(1)).listUsers();
	    verifyNoMoreInteractions(userBusiness);
	}
	
	@Test
	public void testGetUserSuccess() throws Exception, SystemException {
	    User user = new User("reperez.rp@gmail.com", "Renan Perez");
	    when(userBusiness.getUser(user.getEmail())).thenReturn(user);
	    mockMvc.perform(get("/users/getUser/{email}", user.getEmail()+"/"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$.content.email", is("reperez.rp@gmail.com")))
	            .andExpect(jsonPath("$.content.name", is("Renan Perez")));
	    verify(userBusiness, times(1)).getUser(user.getEmail());
	    verifyNoMoreInteractions(userBusiness);
	}
	
	@Test
	public void testSaveOrUpdateUserSuccess() throws Exception, SystemException {
	    User user = new User("reperez.rp@gmail.com", "Renan Perez");
	    when(userBusiness.saveOrUpdateUser(user)).thenReturn(user);
	    mockMvc.perform(post("/users/saveOrUpdateUser")
	    				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
	    				.content(new Gson().toJson(user)))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$.status", is("OK")));
	}


}

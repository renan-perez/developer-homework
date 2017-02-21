package net.discussions.core.test;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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

import net.discussions.core.business.PostBusiness;
import net.discussions.core.conf.AppWebConfiguration;
import net.discussions.core.conf.JPAConfiguration;
import net.discussions.core.controller.PostController;
import net.discussions.core.dao.GenericDAO;
import net.discussions.core.exception.SystemException;
import net.discussions.core.model.Post;
import net.discussions.core.model.PostId;
import net.discussions.core.util.DateUtil;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, AppWebConfiguration.class, GenericDAO.class, JPAConfiguration.class})
@WebAppConfiguration
public class PostControllerUnitTest {
	
	private MockMvc mockMvc;
	
	@Mock
	@Autowired
    private PostBusiness postBusiness;
	
	@InjectMocks
	@Autowired
    private PostController postController;
	
	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(postController)
                .addFilters(new CORSFilter())
                .build();
    }
	
	@Test
	public void listPostsByUserEmail() throws Exception, SystemException {
	    String email = "reperez.rp@gmail.com";
	    List<Post> posts = Arrays.asList(new Post(email));
	    when(postBusiness.listPostsByUserEmail(email)).thenReturn(posts);
	    mockMvc.perform(get("/posts/listPostsByUserEmail/{email}", email+"/"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$.content[0].id.user.email", is(email)));
	    verify(postBusiness, times(1)).listPostsByUserEmail(email);
	    verifyNoMoreInteractions(postBusiness);
	}
	
	@Test
	public void listResponses() throws Exception, SystemException {
		String email = "reperez.rp@gmail.com";
		String dateTime = "2017,02,20,15,09,48";
		LocalDateTime dt = DateUtil.stringToLocalDateTime(dateTime);
		PostId id = new PostId(email, dt);
	    List<Post> posts = Arrays.asList(new Post(email));
	    when(postBusiness.listResponses(id)).thenReturn(posts);
	    mockMvc.perform(get("/posts/listResponses/{email}/{dateTime}", email, dateTime+"/"))
	            .andExpect(status().isOk());
	}
	
}

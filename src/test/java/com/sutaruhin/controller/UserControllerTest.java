package com.sutaruhin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.intThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sutaruhin.entity.User;
import com.sutaruhin.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
	}
	@Test
	@DisplayName("ユーザ編集画面")
	@WithMockUser
	void testGetUser() throws Exception{
		MvcResult result = mockMvc.perform(get("/user/update/1/"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("user"))
			.andExpect(model().hasNoErrors())
			.andExpect(view().name("user/update"))
			.andReturn();

		User user = (User)result.getModelAndView().getModel().get("user");

		assertEquals(user.getId(), 1);
		assertEquals(user.getName(), "スタルヒン太郎");
	}

	@Test
	@DisplayName("ユーザ一覧取得")
	@WithMockUser
	void testGetList()throws Exception{
		MvcResult result = mockMvc.perform(get("/user/list/"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("userlist"))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("user/list"))
				.andReturn();
//		テストデータ(id,name)をMapにて格納する
		Map<Integer, String> map = new HashMap<>();
        map.put(1, "スタルヒン太郎");
        map.put(2, "スタルヒン次郎");
        map.put(3, "スタルヒン花子");

        List<User> users= (List<User>)result.getModelAndView().getModel().get("userlist");
		assertEquals(users.size(), 3);
		for (int i=0;i<=2;i++) {
			assertEquals(users.get(i).getId(), i+1);
			assertEquals(users.get(i).getName(), map.get(i+1));
		}

	}

}

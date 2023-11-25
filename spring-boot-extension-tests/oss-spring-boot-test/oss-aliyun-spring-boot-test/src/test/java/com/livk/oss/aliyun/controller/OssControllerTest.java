/*
 * Copyright 2021 spring-boot-extension the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.livk.oss.aliyun.controller;

import com.livk.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author livk
 */
@Disabled("需要阿里云OSS账号")
@SpringBootTest
@AutoConfigureMockMvc
class OssControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void test() throws Exception {
		String url = mockMvc.perform(get("/oss/test"))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			.getResponse()
			.getContentAsString();
		try (InputStream stream = new URI(url).toURL().openStream()) {
			FileUtils.download(stream, "./test.jpg");
		}
		File file = new File("./test.jpg");
		assertTrue(file.exists());
		assertTrue(file.delete());
	}

}

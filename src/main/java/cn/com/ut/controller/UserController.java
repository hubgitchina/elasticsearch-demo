package cn.com.ut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.com.ut.dao.UserRepository;
import cn.com.ut.entity.User;

/**
 * 
 * @author wangpeng1
 * @since 2018年10月23日
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 添加
	 * 
	 * @return
	 */
	@GetMapping("/add")
	public String add(User user) {

		userRepository.save(user);
		return "success";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable String id) {

		User user = userRepository.queryUserById(id);
		if (user != null) {
			userRepository.delete(user);
		}
		return "success";
	}

	/**
	 * 删除所有数据
	 * 
	 * @return
	 */
	@GetMapping("/deleteAll")
	public String deleteAll() {

		userRepository.deleteAll();
		return "success";
	}

	/**
	 * 局部更新
	 * 
	 * @return
	 */
	@GetMapping("/update")
	public String update() {

		User user = userRepository.queryUserById("1");
		user.setUserName("李四");
		userRepository.save(user);
		return "success";
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	@GetMapping("/query")
	public User query() {

		User userInfo = userRepository.queryUserById("1");
		String jsonString = JSON.toJSONString(userInfo);
		return userInfo;
	}
}

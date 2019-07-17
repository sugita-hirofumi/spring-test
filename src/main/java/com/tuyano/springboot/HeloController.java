package com.tuyano.springboot;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tuyano.springboot.repositories.MyDataRepository;

@Controller
public class HeloController {
	
	@Autowired
	MyDataRepository repository;
	
	/*@PersistenceContext
	EntityManager entityManager;*/
	
	@Autowired
	MyDataDaoImpl dao;
	
	//MyDataDaoImpl dao;
	
	@PostConstruct
	public void init() {
		//dao = new MyDataDaoImpl(entityManager);
		//1つ目のダミーデータ作成
		MyData d1 = new MyData();
		d1.setName("tuyano");
		d1.setAge(123);
		d1.setMail("syoda@tuyano.com");
		d1.setMemo("090999999");
		repository.saveAndFlush(d1);
		//2つ目のダミーデータ作成
		MyData d2 = new MyData();
		d2.setName("hanako");
		d2.setAge(15);
		d2.setMail("hanako@flower");
		d2.setMemo("080888888");
		repository.saveAndFlush(d2);
		//3つ目のダミーデータ作成
		MyData d3 = new MyData();
		d3.setName("sachiko");
		d3.setAge(37);
		d3.setMail("sachiko@happy");
		d3.setMemo("070777777");
		repository.saveAndFlush(d3);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg", "MyDataのサンプルです");
		Iterable<MyData> list = dao.getAll(); //repository.findByAge(10, 40);  //dao.findByAge(10, 40);  //repository.findAllOrderByName();   
		mav.addObject("datalist", list);
		return mav;
	}
	
	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(
			@ModelAttribute("formModel") MyData mydata,
		    ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg", "this is sample content.");
		mav.addObject("formModel", mydata);
		Iterable<MyData> list = repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}*/
	
	@RequestMapping(value ="/", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView form(
			@ModelAttribute("formModel")
			@Validated MyData mydata,
			BindingResult result,
			ModelAndView mov) {
		ModelAndView res = null;
		if(!result.hasErrors()) {
			repository.saveAndFlush(mydata);
			res = new ModelAndView("redirect:/");
		}else {
			mov.setViewName("index");
			mov.addObject("msg", "sorry, erroe is occured...");
			Iterable<MyData> list = repository.findAll();
			mov.addObject("datalist", list);
			res = mov;
		}
		return res;
	}
	
	
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute MyData mydata,
			@PathVariable int id, ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title", "edit mydata");
		Optional<MyData> data = repository.findById((long)id);
		mav.addObject("formModel", data.get());
		return mav;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView update(@ModelAttribute MyData mydata,
			ModelAndView mav) {
		repository.saveAndFlush(mydata);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id,
			ModelAndView mav) {
		mav.setViewName("delete");
		mav.addObject("title", "delete mydata");
		Optional<MyData> data = repository.findById((long)id);
		mav.addObject("formModel", data.get());
		return mav;
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView remove(@RequestParam long id,
			ModelAndView mav) {
		repository.deleteById(id);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView find(ModelAndView mav) {
		mav.setViewName("find");
		mav.addObject("title", "find page");
		mav.addObject("msg", "MyDataのサンプルです。");
		mav.addObject("value", "");
		Iterable<MyData> list = dao.getAll();
		mav.addObject("datalist", list);
		return mav;
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest request,
			ModelAndView mav) {
		mav.setViewName("find");
		String param = request.getParameter("fstr");
		if(param == "") {
			mav = new ModelAndView("redirect:/find");
		}else {
			mav.addObject("title", "find result");
			mav.addObject("msg", "「" + param + "」の検索結果");
			mav.addObject("value", param);
			List<MyData> list = dao.find(param);
			mav.addObject("datalist", list);
		}
		return mav;
	}
	
		
}


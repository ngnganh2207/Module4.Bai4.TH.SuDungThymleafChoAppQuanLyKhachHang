package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import com.codegym.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
//    private final ICustomerService customerService= new CustomerService();
//    tương đương với
    @Autowired
    ICustomerService customerService;

    @GetMapping("")
    public String index(Model model){
        List<Customer> customerList= customerService.findAll();
        model.addAttribute("customers",customerList);
        return "/index";
    }
//    @GetMapping("/create")
//    public String create(){
//        return "redirect:/create";
//    }
//    Hoặc
    @GetMapping("/create")
    public ModelAndView create(){
        ModelAndView modelAndView =new ModelAndView("create");
        modelAndView.addObject("customer",new Customer());
        return modelAndView;
    }
//gửi một Flash message về trang danh sách khách hàng để thông báo lưu thành công, bằng cách sử dụng redirect.addFlashAttribute(messageName, messageContent).
    @PostMapping("/save")
    public String save(Customer customer, RedirectAttributes redirectAttributes) {
        //customer.setId((int) (Math.random() * 10000));
        redirectAttributes.addFlashAttribute("success","Luu Thanh Cong");
        customerService.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Customer customer,RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("susscess","Update Thành Công");
        customerService.update(customer.getId(), customer);
        return "redirect:/customer";
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("customer",customerService.findById(id));
        return "/delete";
    }
    @PostMapping("/delete")
    public String deletePost(Customer customer, RedirectAttributes redirectAttributes){
        customerService.remove(customer.getId());
        redirectAttributes.addFlashAttribute("success","Removed customer successfully!");
        return "redirect:/customer";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/view";
    }

}

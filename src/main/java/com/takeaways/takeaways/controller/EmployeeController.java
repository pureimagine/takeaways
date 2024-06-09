package com.takeaways.takeaways.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.takeaways.takeaways.common.R;
import com.takeaways.takeaways.entity.Employee;
import com.takeaways.takeaways.service.EmployeeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    /**
     *
     * @param httpSession
     * @param employee
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public R<Employee> login(HttpSession httpSession, @RequestBody Employee employee)
    {
        /**
         * 将页面提交的密码password进行md5加密处理
         * 根据页面提交的用户名username查询数据库
         * 如果没有查询到则返回登录失 败结果
         * 密码比对，如果不一致则返回登录失败结果
         * 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
         * 登录成功，将员工id存入Session并返回登录成功结果
         */
        String password = employee.getPassword();
        //将页面提交的密码password进行md5加密处理
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(Employee::getUsername,employee.getUsername());
        //根据页面提交的用户名username查询数据库
        Employee one = employeeService.getOne(queryWrapper);
        //如果没有查询到则返回登录失败结果
        if(one==null)
            return R.error("没有该人查询");
        //密码比对，如果不一致则返回登录失败结果
        if (!password.equals(one.getPassword()))
            return R.error("密码比对不一致");
        //查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (one.getStatus()==0)
            return R.error("员工被禁用");
        //登录成功，将员工id存入Session并返回回登录成功结果
        httpSession.setAttribute("employee",employee.getId());
        System.out.println("employee->>>>>>>>>>>>>>"+httpSession.getAttribute("employee"));
        return R.success(one);
    }
    @PostMapping("/logout")
    @ResponseBody
    public R<String> logout(HttpSession httpSession)
    {
        httpSession.removeAttribute("employee");
        return R.success("成功退出登录");
    }
    @ResponseBody
    @PostMapping
    public R<String> saveUser(HttpSession session,@RequestBody Employee employee)
    {
        log.info(String.valueOf(employee));
        String password="123456";
        employee.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long) session.getAttribute("employee"));
//        employee.setUpdateUser((Long) session.getAttribute("employee"));
        employee.setCreateUser(Long.valueOf("1"));
        employee.setUpdateUser(Long.valueOf("1"));
        employeeService.save(employee);
        return R.success("成功");
    }

}

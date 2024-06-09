package com.takeaways.takeaways.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.takeaways.takeaways.entity.Employee;
import com.takeaways.takeaways.mapper.EmployeeMapper;
import com.takeaways.takeaways.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}

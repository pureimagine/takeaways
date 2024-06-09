package com.takeaways.takeaways.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.takeaways.takeaways.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}

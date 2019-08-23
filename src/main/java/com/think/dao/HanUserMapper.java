package com.think.dao;

import com.think.entity.HanUser;
import com.think.entity.TOrder;
import org.apache.ibatis.annotations.*;

@Mapper
public interface HanUserMapper {

    @Results(value = { @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"), })
    @Select("SELECT * FROM han_user WHERE id=#{id}")
    public HanUser findById(int idu);

}

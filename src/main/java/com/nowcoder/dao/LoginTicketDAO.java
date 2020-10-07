package com.nowcoder.dao;

import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME = " login_ticket ";
    String INSERT_FIELDS = " user_id, expired, status, ticket ";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, " (", INSERT_FIELDS,
            ") values (#{userId}, #{expired}, #{status}, #{ticket})"})
    int addTicket(LoginTicket loginTicket);

   // 用户登录，把 ticket拿出来，看用户在不在
    @Select({"select ", SELECT_FIELDS," from ", TABLE_NAME, " where ticket=#{ticket}"})
    LoginTicket selectByTickets(String ticket);

    // 用户登出，ticket的状态改变
    @Update({"update ", TABLE_NAME, " set status = #{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}

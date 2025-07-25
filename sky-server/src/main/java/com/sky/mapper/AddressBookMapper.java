package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

public interface AddressBookMapper {

    List<AddressBook> list(AddressBook addressBook);

    void insert(AddressBook addressBook);

    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefaultByUserId(AddressBook addressBook);

    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);
}

package com.ns.dao;

import com.ns.entity.File;

public interface FileMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file
     *
     * @mbggenerated
     */
    int insert(File record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file
     *
     * @mbggenerated
     */
    int insertSelective(File record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file
     *
     * @mbggenerated
     */
    File selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(File record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(File record);
}
package com.mapper;

import com.entity.ScheduleJobLog;
import com.entity.ScheduleJobLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScheduleJobLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job_log
     *
     * @mbggenerated Tue Mar 26 08:00:33 CST 2019
     */
    int countByExample(ScheduleJobLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job_log
     *
     * @mbggenerated Tue Mar 26 08:00:33 CST 2019
     */
    int deleteByExample(ScheduleJobLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job_log
     *
     * @mbggenerated Tue Mar 26 08:00:33 CST 2019
     */
    int deleteByPrimaryKey(Long logId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job_log
     *
     * @mbggenerated Tue Mar 26 08:00:33 CST 2019
     */
    int insert(ScheduleJobLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job_log
     *
     * @mbggenerated Tue Mar 26 08:00:33 CST 2019
     */
    int insertSelective(ScheduleJobLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job_log
     *
     * @mbggenerated Tue Mar 26 08:00:33 CST 2019
     */
    List<ScheduleJobLog> selectByExample(ScheduleJobLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job_log
     *
     * @mbggenerated Tue Mar 26 08:00:33 CST 2019
     */
    ScheduleJobLog selectByPrimaryKey(Long logId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job_log
     *
     * @mbggenerated Tue Mar 26 08:00:33 CST 2019
     */
    int updateByExampleSelective(@Param("record") ScheduleJobLog record, @Param("example") ScheduleJobLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job_log
     *
     * @mbggenerated Tue Mar 26 08:00:33 CST 2019
     */
    int updateByExample(@Param("record") ScheduleJobLog record, @Param("example") ScheduleJobLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job_log
     *
     * @mbggenerated Tue Mar 26 08:00:33 CST 2019
     */
    int updateByPrimaryKeySelective(ScheduleJobLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table schedule_job_log
     *
     * @mbggenerated Tue Mar 26 08:00:33 CST 2019
     */
    int updateByPrimaryKey(ScheduleJobLog record);
}
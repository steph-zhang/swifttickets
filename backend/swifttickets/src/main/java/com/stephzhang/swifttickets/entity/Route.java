package com.stephzhang.swifttickets.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_route")
public class Route {
    private String path;

    private String trainname;

    private String starttime;

    private String endtime;

    private String timetable;
}

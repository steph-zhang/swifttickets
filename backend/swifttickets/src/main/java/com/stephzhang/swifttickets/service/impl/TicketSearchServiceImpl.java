package com.stephzhang.swifttickets.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stephzhang.swifttickets.dto.QueryTicketLeftForm;
import com.stephzhang.swifttickets.dto.StationDTO;
import com.stephzhang.swifttickets.dto.TicketSearchReq;
import com.stephzhang.swifttickets.entity.Route;
import com.stephzhang.swifttickets.entity.User;
import com.stephzhang.swifttickets.mapper.RouteMapper;
import com.stephzhang.swifttickets.service.TicketSearchService;
import com.stephzhang.swifttickets.utils.Result;
import com.stephzhang.swifttickets.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TicketSearchServiceImpl implements TicketSearchService {

    @Resource
    RouteMapper routeMapper;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result searchAllStations() {
        List<StationDTO> allsStations = new ArrayList<>();
        allsStations.add(new StationDTO("北京南", "北京南"));
        allsStations.add(new StationDTO("济南西", "济南西"));
        allsStations.add(new StationDTO("南京南", "南京南"));
        allsStations.add(new StationDTO("杭州东", "杭州东"));
        allsStations.add(new StationDTO("宁波", "宁波"));
        return Result.ok(allsStations);
    }

    @Override
    public Result searchTickets(TicketSearchReq ticketSearchReq) {
        System.out.println(ticketSearchReq);
        String fromStation = ticketSearchReq.getFromStation();
        String toStation = ticketSearchReq.getToStation();
        String departureDate = ticketSearchReq.getDepartureDate();

        String routeListJson = stringRedisTemplate.opsForValue().get("search:cache:" + fromStation + toStation + departureDate);
        if (StrUtil.isNotBlank(routeListJson)){
            return Result.ok(routeListJson);
        }

        QueryWrapper<Route> routeQueryWrapper = new QueryWrapper<>();
        routeQueryWrapper.like("path", fromStation + "%" + toStation)
                        .ge("starttime", departureDate);
        List<Route> routeList = routeMapper.selectList(routeQueryWrapper);

        stringRedisTemplate.opsForValue().set("search:cache:" + fromStation + toStation + departureDate, routeList.toString());
        System.out.println(routeList);
        return Result.ok(routeList);
    }

    @Override
    public Result queryTicketLeft(QueryTicketLeftForm qtl) {
        // 获得子路段
        List<String> atomicRouteArray = getAtomicRoute(qtl.getPath(), qtl.getFromStation(), qtl.getToStation());
        if(atomicRouteArray == null) return Result.fail("子路段不存在");
        // 根据子路段得到原子路段的列表
//        for(String atomic: atomicRouteArray){
//            System.out.println(atomic);
//        }
        // 遍历原子路段列表，所有原子路段余票均大于0则代表有余票。
        Map<Object, Object> entries = stringRedisTemplate.
                opsForHash().entries("ticket:" + qtl.getTrainname() + "_" + qtl.getDepartureDate());
        for(Object field: entries.keySet()){
            System.out.println(entries.get(field));
            if(Integer.parseInt(entries.get(field).toString()) <= 0) return Result.fail("没有余票");
        }



        return Result.ok("有余票");
    }

    @Override
    public Result buyTicket(QueryTicketLeftForm qtl, HttpSession session) {
        List<String> atomicRouteArray = getAtomicRoute(qtl.getPath(), qtl.getFromStation(), qtl.getToStation());
        if(atomicRouteArray == null) return Result.fail("子路段不存在");
        // 根据子路段得到原子路段的列表
//        for(String atomic: atomicRouteArray){
//            System.out.println(atomic);
//        }
        // 遍历原子路段列表，所有原子路段余票均大于0则代表有余票。
        Map<Object, Object> entries = stringRedisTemplate.
                opsForHash().entries("ticket:" + qtl.getTrainname() + "_" + qtl.getDepartureDate());
        for(Object field: entries.keySet()){
//            System.out.println(entries.get(field));
            if(Integer.parseInt(entries.get(field).toString()) <= 0) return Result.fail("没有余票");
        }
        for(Object field: entries.keySet()){
            stringRedisTemplate.opsForHash().
                    increment("ticket:" + qtl.getTrainname() + "_" + qtl.getDepartureDate(),
                            field, -1L);
        }
        User user = UserHolder.get();

        return Result.ok("成功买票");
    }

    public static List<String> getAtomicRoute(String input, String from, String to) {
        int startIdx = input.indexOf(from);
        if (startIdx == -1) return null; // str1 not found

        int endIdx = input.indexOf(to, startIdx + from.length());
        if (endIdx == -1) return null; // str2 not found after str1

        String subPath = input.substring(startIdx, endIdx + to.length());
        String[] stationArray = subPath.split(",");
        List<String> atomicRouteArray = new ArrayList<>();
        for(int i = 0; i < stationArray.length - 1; i ++){
            atomicRouteArray.add(stationArray[i] + "," + stationArray[i + 1]);
        }
        return atomicRouteArray;
    }
}

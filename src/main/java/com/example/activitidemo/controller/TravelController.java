package com.example.activitidemo.controller;

import com.example.activitidemo.bean.Travel;
import com.example.activitidemo.service.TravelService;
import com.example.activitidemo.service.TravelServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("travel")
public class TravelController {
    @Resource
    private TravelService travelService = new TravelServiceImpl();

    @RequestMapping(value = "/getTravelApproveByUserName",method = RequestMethod.POST)
    public List<Travel> getTravelApprove(@RequestBody Map<String,String> searchInfo){
        return travelService.getTravelApproveByUserName(searchInfo);
    }

    @PostMapping(value = "/newTravelApprove")
    public Boolean newTravelApprove(@RequestBody Map<String,String> travelInfo){
        return travelService.applyTravelByProcessId(travelInfo);
    }

    @PostMapping("/travelTaskApprove")
    public  Boolean ApproveTravelTask(@RequestBody Map<String,String> approveInfo){
        return travelService.ApproveTravelTask(approveInfo);
    }
}

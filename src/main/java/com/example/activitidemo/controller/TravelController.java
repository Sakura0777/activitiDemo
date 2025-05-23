package com.example.activitidemo.controller;

import com.example.activitidemo.bean.ApproveRequest;
import com.example.activitidemo.bean.TaskSearchRequest;
import com.example.activitidemo.bean.Travel;
import com.example.activitidemo.service.TravelService;
import com.example.activitidemo.service.TravelServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("travel")
public class TravelController {
    @Resource
    private TravelService travelService = new TravelServiceImpl();

    @RequestMapping(value = "/getTravelApproveByUserName",method = RequestMethod.POST)
    public List<Travel> getTravelApprove(@Valid  @RequestBody TaskSearchRequest searchInfo){
        return travelService.getTravelApproveByUserName(searchInfo);
    }
    @PostMapping(value = "/getTravelHistoryByUserName")
    public ResponseEntity<List<Travel>> getLeaveHistory(@Valid @RequestBody TaskSearchRequest searchInfo){
        List<Travel> travelList = travelService.getTravelHistoryByUserName(searchInfo);

        return ResponseEntity.ok(travelList);
        // 设置自定义响应头
/*        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-User-Created", "true");
        headers.add("X-Request-Id", UUID.randomUUID().toString());

        // 返回 201 Created 状态码，并在 Location 头中包含资源路径
        URI location = URI.create("/travel/getTravelHistoryByUserName");
        return ResponseEntity.created(location)
                .headers(headers)  // 添加自定义头
                .body(travelList);      // 设置响应体*/
    }

    @PostMapping(value = "/newTravelApprove")
    public Boolean newTravelApprove(@Valid @RequestBody Travel travelInfo){
        return travelService.applyTravelByProcessId(travelInfo);
    }

    @PostMapping("/travelTaskApprove")
    public  Boolean ApproveTravelTask(@Valid @RequestBody ApproveRequest approveInfo){
        return travelService.ApproveTravelTask(approveInfo);
    }
}

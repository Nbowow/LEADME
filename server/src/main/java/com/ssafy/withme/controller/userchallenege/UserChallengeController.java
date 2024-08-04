package com.ssafy.withme.controller.userchallenege;

import com.ssafy.withme.controller.userchallenege.request.UserChallengeAnalyzeRequest;
import com.ssafy.withme.controller.userchallenege.request.UserChallengeDeleteRequest;
import com.ssafy.withme.controller.userchallenege.request.UserChallengeReportViewResponse;
import com.ssafy.withme.controller.userchallenege.request.UserChallengeSaveRequest;
import com.ssafy.withme.domain.report.Report;
import com.ssafy.withme.domain.user.User;
import com.ssafy.withme.global.annotation.CurrentUser;
import com.ssafy.withme.global.response.ApiResponse;
import com.ssafy.withme.global.response.SuccessResponse;
import com.ssafy.withme.service.userchellenge.UserChallengeService;
import com.ssafy.withme.service.userchellenge.response.UserChallengeAnalyzeResponse;
import com.ssafy.withme.service.userchellenge.response.UserChallengeReportResponse;
import com.ssafy.withme.service.userchellenge.response.UserChallengeSaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserChallengeController {

    private final UserChallengeService userChallengeService;

    /**
     * 영상 분석 결과를 조회한다.
     * 영상 고유 uuid를 받아서 영상 레포트 정보를 반환한다.
     * @param uuid
     * @return
     */

    @GetMapping("/api/v1/userChallenge/report/{uuid}")
    public SuccessResponse<UserChallengeReportResponse> findReportByUuid(@PathVariable("uuid") String uuid) {
        return SuccessResponse.of(userChallengeService.findReportByUuid(uuid));
    }

    /**
     * 사용자가 찍은 영상들을 조회한다.
     * @param pageable
     * @param user
     * @return
     */
//    @GetMapping("/api/v1/userChallenge/reports")
//    public SuccessResponse<List<UserChallengeReportViewResponse>> findReports(
//            @PageableDefault(size = 10) Pageable pageable,
//            @CurrentUser User user){
//        return SuccessResponse.of(userChallengeService.findReportsByUserId(user.getId()));
//    }

    /**
     ** 유저의 스켈레톤 데이터를 받아와서 알고리즘으로 분석률을 반환한다.
     * @param request
     * @param videoFile
     * @return
     * @throws IOException
     */
    @PostMapping("/api/v1/userChallenge/analyze")
    public ApiResponse<UserChallengeAnalyzeResponse> createUserChallenge(@RequestPart("request") UserChallengeAnalyzeRequest request,
                                                                         @RequestPart MultipartFile videoFile) throws IOException {

        return SuccessResponse.of(userChallengeService.analyzeVideo(request, videoFile));
    }

    /**
     ** uuid와 fileName을 받아 임시저장 파일에서 해당 영상을 찾아 영구저장 파일로 이동시키고 파일 이름을 변경하여 영구저장한다.
     * @param request
     * @return
     */
    @PostMapping("/api/v1/userChallenge/temporary/save")
    public SuccessResponse<UserChallengeSaveResponse> saveTemporaryFile(@RequestBody UserChallengeSaveRequest request) {
        return SuccessResponse.of(userChallengeService.saveUserFile(request));
    }

    /**
     * 유저 영상 uuid를 받아 임시저장폴더에서 해당 영상을 찾아서 삭제한다.
     * @param request
     * @return
     */
    @PostMapping("/api/v1/userChallenge/temporary/delete")
    public SuccessResponse<Void> deleteTemporaryFile(@RequestBody UserChallengeDeleteRequest request){
        userChallengeService.deleteUserFile(request);
        return SuccessResponse.empty();
    }

}

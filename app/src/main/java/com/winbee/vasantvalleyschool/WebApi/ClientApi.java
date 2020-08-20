package com.winbee.vasantvalleyschool.WebApi;

import com.winbee.vasantvalleyschool.Models.AssignmentToSubmit;
import com.winbee.vasantvalleyschool.Models.AttendenceModel;
import com.winbee.vasantvalleyschool.Models.ForgetMobile;
import com.winbee.vasantvalleyschool.Models.LiveClassModel;
import com.winbee.vasantvalleyschool.Models.OtpVerify;
import com.winbee.vasantvalleyschool.Models.PurchasedMainModel;
import com.winbee.vasantvalleyschool.Models.RefCode;
import com.winbee.vasantvalleyschool.Models.RefUser;
import com.winbee.vasantvalleyschool.Models.ResetPassword;
import com.winbee.vasantvalleyschool.Models.ResultModel;
import com.winbee.vasantvalleyschool.Models.SIACDetailsMainModel;
import com.winbee.vasantvalleyschool.Models.SIADMainModel;
import com.winbee.vasantvalleyschool.Models.SectionDetailsMainModel;
import com.winbee.vasantvalleyschool.Models.SemesterName;
import com.winbee.vasantvalleyschool.Models.StartTestModel;
import com.winbee.vasantvalleyschool.Models.SubmitAssignment;
import com.winbee.vasantvalleyschool.Models.SubmittedAssignment;
import com.winbee.vasantvalleyschool.Models.UrlName;
import com.winbee.vasantvalleyschool.Models.UrlNewQuestion;
import com.winbee.vasantvalleyschool.Models.UrlQuestion;
import com.winbee.vasantvalleyschool.Models.UrlQuestionSolution;
import com.winbee.vasantvalleyschool.Models.UrlSolution;
import com.winbee.vasantvalleyschool.Models.ViewResult;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClientApi {
    @POST("fetch_user_cover_information.php")
    Call<RefCode> refCodeSignIn(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("password") String password,
            @Query("refcode") String refcode
    );

    @POST("user_registration_information.php")
    Call<RefUser> refUserSignIn(
            @Query("SubURL") int SubURL,
            @Query("name") String name,
            @Query("email") String email,
            @Query("mobile") String mobile,
            @Query("refcode") String refcode,
            @Query("registration") String registration,
            @Query("class_data") String class_data,
            @Query("password") String password

    );

    @POST("send-otp.php")
    Call<ForgetMobile> getForgetMobile(
            @Query("SubURL") int SubURL,
            @Query("username") String username
    );

    @POST("verify-otp.php")
    Call<OtpVerify> getOtpVerify(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("otp") String otp
    );

    @POST("fetch_purchased_bucket_information.php")
    Call<PurchasedMainModel> getCourseById(
            @Query("SubURL") int SubURL,
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID
    );

    @POST("fetch_bucket_cover_information.php")
    Call<ArrayList<SemesterName>> getCourseSubject(
            @Query("SubURL") int SubURL,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID,
            @Query("USER_ID") String USER_ID
    );
    @POST("fetch_bucket_cover_information.php")
    Call<ArrayList<UrlName>> getTopic(
            @Query("SubURL") int SubURL,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID,
            @Query("USER_ID") String USER_ID
    );

    @FormUrlEncoded
    @POST("submit-doubt.php")
    Call<UrlQuestionSolution> getQuestionSolution(
            @Field("filename") String filename,
            @Field("answer") String answer,
            @Field("DocumentId") String DocumentId,
            @Field("userid") String userid
    );

    @FormUrlEncoded
    @POST("submit-doubt.php")
    Call<UrlNewQuestion> getNewQuestion(
            @Field("title") String title,
            @Field("question") String question,
            @Field("userid") String userid,
            @Field("DocumentId") String DocumentId
    );

    @POST("doubt-session.php")
    Call<ArrayList<UrlQuestion>> getQuestion(
            @Query("DocumentId") String DocumentId
    );


    @POST("doubt-session.php")
    Call<ArrayList<UrlSolution>> getSolution(
            @Query("DocumentId") String DocumentId,
            @Query("filename") String filename
    );

    @POST("record-attendence.php")
    Call<AttendenceModel> fetchAttendence(
            @Query("user_id") String user_id
    );

    @POST("fetch_assignment_data.php")
    Call<AssignmentToSubmit> getAllAssignment(
            @Query("org_id") String org_id,
            @Query("user_id") String user_id
    );

    @POST("fetch-section-details.php")
    @FormUrlEncoded
    Call<SectionDetailsMainModel> fetchSectionDetails(
            @Field("org_code") String org_code,
            @Field("auth_code") String auth_code,
            @Field("user_id") String user_id
    );

    @POST("fetch-section-individual-assessment-cover-details.php")
    @FormUrlEncoded
    Call<SIACDetailsMainModel> fetchSIACDetails(
            @Field("org_code") String org_code,
            @Field("auth_code") String auth_code,
            @Field("bucket_code") String bucket_code
    );

    @POST("fetch-section-individual-assessment-data.php")
    @FormUrlEncoded
    Call<SIADMainModel> fetchSIADDATA(
            @Field("org_code") String org_code,
            @Field("auth_code") String auth_code,
            @Field("bucket_code") String bucket_code,
            @Field("paper_code") String paper_code
    );


    @POST("Start-Exam-Paper.php")
    @FormUrlEncoded
    Call<StartTestModel> getStartTest(
            @Field("OrgID") String OrgID,
            @Field("UserID") String UserID,
            @Field("PaperID") String PaperID,
            @Field("ExamStatus") String ExamStatus,
            @Field("Read_Instruction") String Read_Instruction
    );


    @POST("Submit-Exam-Paper.php")
    @FormUrlEncoded
    Call<ResultModel> submitResponse(
            @Field("CoachingID") String CoachingID,
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID,
            @Field("Response") JSONArray jsonArray,
            @Field("Staticstics") String Staticstics,
            @Field("Submit_Exam_Paper") boolean Submit_Exam_Paper
    );


    @POST("view-result.php")
    @FormUrlEncoded
    Call<ViewResult> viewResult(
            @Field("OrgID") String OrgID,
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID
    );

    @POST("insert_assignment_data.php")
    @FormUrlEncoded
    Call<SubmitAssignment> getSubmitAssignment(
            @Field("org_id") String org_id,
            @Field("user_id") String user_id,
            @Field("course_id") String course_id,
            @Field("assignment_id") String assignment_id,
            @Field("attachment") String attachment,
            @Field("description") String description,
            @Field("doc_type") String doc_type
    );

    @POST("fetch_assignment_submitted_student.php")
    Call<SubmittedAssignment> getSubmitedAssignment(
            @Query("org_id") String org_id,
            @Query("user_id") String user_id
    );

    @POST("reset-password.php")
    Call<ResetPassword> getResetPassword(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("otp") String otp,
            @Query("new_password") String new_password
    );

    @POST("fetch_live_classes.php")
    Call<ArrayList<LiveClassModel>> getLive(
            @Query("ORG_ID") String ORG_ID,
            @Query("USER_ID") String USER_ID,
            @Query("SubURL") int SubURL
    );
}

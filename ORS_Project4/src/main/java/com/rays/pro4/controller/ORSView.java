package com.rays.pro4.controller;

/**
 * @author Ankit Rajput
 *
 */
public interface ORSView {

	public String APP_CONTEXT = "/ORS_Project4";
	public String LAYOUT_VIEW = "/BaseLayout.jsp";
	public static String PAGE_FOLDER = "/jsp";
	public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";

	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";
	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public static String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";
	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimeTableView.jsp";
	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimeTableListView.jsp";
	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";
	public String UPI_LIST_VIEW = PAGE_FOLDER + "/UPIListView.jsp";
	public String UPI_VIEW = PAGE_FOLDER + "/UPIView.jsp";
	public String ATM_LIST_VIEW = PAGE_FOLDER + "/ATMListView.jsp";
	public String ATM_VIEW = PAGE_FOLDER + "/ATMView.jsp";

	public String BANK_VIEW = PAGE_FOLDER + "/BankView.jsp";
	public String BANK_LIST_VIEW = PAGE_FOLDER + "/BankListView.jsp";

	public String CANDIDATE_VIEW = PAGE_FOLDER + "/CandidateView.jsp";
	public String CANDIDATE_LIST_VIEW = PAGE_FOLDER + "/CandidateListView.jsp";

	public String FAMILY_VIEW = PAGE_FOLDER + "/FamilyView.jsp";
	public String FAMILY_LIST_VIEW = PAGE_FOLDER + "/FamilyListView.jsp";

	public String SALARY_VIEW = PAGE_FOLDER + "/SalaryView.jsp";
	public String SALARY_LIST_VIEW = PAGE_FOLDER + "/SalaryListView.jsp";

	public String PRODUCT_VIEW = PAGE_FOLDER + "/ProductView.jsp";
	public String PRODUCT_LIST_VIEW = PAGE_FOLDER + "/ProductListView.jsp";

	public String SUPPORT_VIEW = PAGE_FOLDER + "/SupportView.jsp";
	public String SUPPORT_LIST_VIEW = PAGE_FOLDER + "/SupportListView.jsp";

	public String WISHLIST_VIEW = PAGE_FOLDER + "/WishListView.jsp";
	public String WISHLIST_LIST_VIEW = PAGE_FOLDER + "/WishListListView.jsp";

	public String VEHICLE_VIEW = PAGE_FOLDER + "/VehicleView.jsp";
	public String VEHICLE_LIST_VIEW = PAGE_FOLDER + "/VehicleListView.jsp";

	// public String ERROR_VIEW5 = PAGE_FOLDER + "/ErrorView5.jsp";

	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";
	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";
	public String LOGOUT_CTL = APP_CONTEXT + "/LoginCtl";
	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";
	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";
	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";
	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimeTableCtl";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimeTableListCtl";
	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";
	public String UPI_LIST_CTL = APP_CONTEXT + "/ctl/UPIListCtl";
	public String UPI_CTL = APP_CONTEXT + "/ctl/UPICtl";

	public String FAMILY_LIST_CTL = APP_CONTEXT + "/ctl/FamilyListCtl";
	public String FAMILY_CTL = APP_CONTEXT + "/ctl/FamilyCtl";

	public String BANK_CTL = APP_CONTEXT + "/ctl/BankCtl";
	public String BANK_LIST_CTL = APP_CONTEXT + "/ctl/BankListCtl";

	public String CANDIDATE_CTL = APP_CONTEXT + "/ctl/CandidateCtl";
	public String CANDIDATE_LIST_CTL = APP_CONTEXT + "/ctl/CandidateListCtl";

	public String SALARY_CTL = APP_CONTEXT + "/ctl/SalaryCtl";
	public String SALARY_LIST_CTL = APP_CONTEXT + "/ctl/SalaryListCtl";

	public String SUPPORT_CTL = APP_CONTEXT + "/ctl/SupportCtl";
	public String SUPPORT_LIST_CTL = APP_CONTEXT + "/ctl/SupportListCtl";

	public String WISHLIST_CTL = APP_CONTEXT + "/ctl/WishListCtl";
	public String WISHLIST_LIST_CTL = APP_CONTEXT + "/ctl/WishListListCtl";

	public String VEHICLE_CTL = APP_CONTEXT + "/ctl/VehicleCtl";
	public String VEHICLE_LIST_CTL = APP_CONTEXT + "/ctl/VehicleListCtl";

	public String PRODUCT_CTL = APP_CONTEXT + "/ctl/ProductCtl";
	public String PRODUCT_LIST_CTL = APP_CONTEXT + "/ctl/ProductListCtl";
	public String ATM_LIST_CTL = APP_CONTEXT + "/ctl/ATMListCtl";
	public String ATM_CTL = APP_CONTEXT + "/ctl/ATMCtl";

}

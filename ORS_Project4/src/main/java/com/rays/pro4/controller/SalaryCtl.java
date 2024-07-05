package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.SalaryBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.SalaryModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "SalaryCtl", urlPatterns = { "/ctl/SalaryCtl" })

public class SalaryCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
	
		HashMap map1 = new HashMap();
		map1.put("Riya", "Riya");
		map1.put("Ankit", "Ankit");
		map1.put("Pooja", "Pooja");
		map1.put("Dharam", "Dharam");


		request.setAttribute("map1", map1);
		
		HashMap map = new HashMap();

		map.put("Active", "Active");
		map.put("Inactive", "Inactive");
		request.setAttribute("map", map);
		
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("employee"))) {
			request.setAttribute("employee", PropertyReader.getValue("error.require", "employee"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("employee"))) {
			request.setAttribute("employee", "employee name must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "status"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("status"))) {
			request.setAttribute("status", "status must contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", "amount"));
			pass = false;
		} 
		else if (DataValidator.isTooLong(request.getParameter("amount"),20)) {
			request.setAttribute("amount", "amount must contain max 20 digit");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Applied Date"));
			pass = false;
		}
		
		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SalaryBean bean = new SalaryBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setEmployee(DataUtility.getString(request.getParameter("employee")));

		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		bean.setAmount(DataUtility.getLong(request.getParameter("amount")));

		bean.setDate(DataUtility.getDate(request.getParameter("date")));

		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Salary ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));

		SalaryModel model = new SalaryModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			SalaryBean bean;
			try {
				bean = model.findByPK(id)
;
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><>**********" + id + op);

		SalaryModel model = new SalaryModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			SalaryBean bean = (SalaryBean) populateBean(request);
			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" salary ctl DoPost 222222");
					ServletUtility.setSuccessMessage("Salary is successfully Updated", request);

				} else {
					
						long pk = model.add(bean);
						ServletUtility.setBean(bean, request);
						ServletUtility.setSuccessMessage("Salary is successfully Added ", request);
				
					
				//		bean.setId(pk);
					}
					ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" Salary D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Salary exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" Salary ctl D p 5555555");

			SalaryBean bean = (SalaryBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" salary ctl D Post  6666666");
				ServletUtility.redirect(ORSView.SALARY_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" salary  ctl Do post 77777");

			ServletUtility.redirect(ORSView.SALARY_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.SALARY_VIEW;
	}
}

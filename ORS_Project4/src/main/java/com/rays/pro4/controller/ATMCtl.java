package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.ATMBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.ATMModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ATMCtl", urlPatterns = { "/ctl/ATMCtl" })

public class ATMCtl extends BaseCtl {

@Override
protected void preload(HttpServletRequest request) {
	HashMap map = new HashMap();
	map.put("indore", "indore");
	map.put("ujjain", "ujjain");
	map.put("mhow", "mhow");

	request.setAttribute("map", map);
}


	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("location"))) {
			request.setAttribute("location", PropertyReader.getValue("error.require", "location"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("location"))) {
			request.setAttribute("location", "location must contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("remark"))) {
			request.setAttribute("remark", PropertyReader.getValue("error.require", "remark"));
			pass = false;

		} 
		
		

		if (DataValidator.isNull(request.getParameter("cash"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", "cash"));
			pass = false;
		}	else if (DataValidator.isTooLong(request.getParameter("cash"),20)) {
			request.setAttribute("cash", "cash must contain max 20 digit");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}

		
		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		ATMBean bean = new ATMBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setDate(DataUtility.getDate(request.getParameter("date")));

		bean.setCash(DataUtility.getDouble(request.getParameter("cash")));

		bean.setLocation(DataUtility.getString(request.getParameter("location")));

		bean.setRemark(DataUtility.getString(request.getParameter("remark")));

		
		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ABC ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));

		ATMModel model = new ATMModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			ATMBean bean;
			try {
				bean = model.findByPK(id);
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

		ATMModel model = new ATMModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			ATMBean bean = (ATMBean) populateBean(request);
			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" atm ctl DoPost 222222");
					ServletUtility.setSuccessMessage("atm is successfully Updated", request);

				} else {
					System.out.println(" atm ctl DoPost 33333");
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("atm is successfully Added", request);

					bean.setId(pk);
				}

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" ATMBean D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("atm exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" atm ctl D p 5555555");

			ATMBean bean = (ATMBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" atm ctl D Post  6666666");
				ServletUtility.redirect(ORSView.ATM_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" atm  ctl Do post 77777");

			ServletUtility.redirect(ORSView.ATM_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.ATM_VIEW;
	}

}



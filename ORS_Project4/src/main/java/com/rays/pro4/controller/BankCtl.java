package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.UPIBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.BankModel;
import com.rays.pro4.Model.UPIModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "BankCtl", urlPatterns = { "/ctl/BankCtl" })

public class BankCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		BankModel model = new BankModel();

		BankBean bean = new BankBean();

		try {

			List rlist = model.list(0, 0);
			request.setAttribute("rlist", rlist);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("BankName"))) {
			request.setAttribute("BankName", PropertyReader.getValue("error.require", "BankName"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("BankName"))) {
			request.setAttribute("BankName", "BankName must contains alphabet only");
			pass = false;
		}else if (DataValidator.isTooLong(request.getParameter("BankName"), 20)) {
			request.setAttribute("BankName", "BankName No must contain max 20 characters");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("AccountNumber"))) {
			request.setAttribute("AccountNumber", PropertyReader.getValue("error.require", "AccountNumber"));
			pass = false;
		} else if (!DataValidator.isLong(request.getParameter("AccountNumber"))) {
			request.setAttribute("AccountNumber", "AccountNumber  must contains numbers only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("OpenDate"))) {
			request.setAttribute("OpenDate", PropertyReader.getValue("error.require", "OpenDate"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("AccountType"))) {
			request.setAttribute("AccountType", PropertyReader.getValue("error.require", "AccountType"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("AccountType"))) {
			request.setAttribute("AccountType", "AccountType must contains numbers only");
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		BankBean bean = new BankBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setBankName(DataUtility.getString(request.getParameter("BankName")));

		bean.setAccountNumber(DataUtility.getLong(request.getParameter("AccountNumber")));

		bean.setOpenDate(DataUtility.getDate(request.getParameter("OpenDate")));

		bean.setAccountType(DataUtility.getString(request.getParameter("AccountType")));

		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("j ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		BankModel model = new BankModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			BankBean bean;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><>**********" + id + op);

		BankModel model = new BankModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			BankBean bean = (BankBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" J ctl DoPost 222222");
					ServletUtility.setSuccessMessage("Bank is successfully Updated", request);

				} else {
					System.out.println(" J ctl DoPost 33333");
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("Bank is successfully Added", request);

					bean.setId(pk);
				}
				/*
				 * ServletUtility.setBean(bean, request); ServletUtility.setSuccessMessage( is
				 * successfully saved", request);
				 */

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" Jctl D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Bank exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" J ctl D p 5555555");

			BankBean bean = (BankBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" u ctl D Post  6666666");
				ServletUtility.redirect(ORSView.BANK_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" J  ctl Do post 77777");

			ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.BANK_VIEW;
	}

}

package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.SupportBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.SupportModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "SupportCtl", urlPatterns = { "/ctl/SupportCtl" })

public class SupportCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		HashMap map1 = new HashMap();
		map1.put("Riya", "Riya");
		map1.put("Ankit", "Ankit");
		map1.put("Pooja", "Pooja");
		map1.put("Dharam", "Dharam");

		request.setAttribute("map1", map1);

		HashMap map = new HashMap();

		map.put("Hardware", "Hardware");
		map.put("Software", "Software");
		request.setAttribute("map", map);

	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("technician"))) {
			request.setAttribute("technician", PropertyReader.getValue("error.require", "technician"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("technician"))) {
			request.setAttribute("technician", "technician name must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("type"))) {
			request.setAttribute("type", PropertyReader.getValue("error.require", "type"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("type"))) {
			request.setAttribute("type", "status must contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("ticketno"))) {
			request.setAttribute("ticketno", PropertyReader.getValue("error.require", "ticketno"));
			pass = false;
		} else if (DataValidator.isTooLong(request.getParameter("ticketno"), 20)) {
			request.setAttribute("ticketno", "Ticket No must contain max 20 digit");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "date"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SupportBean bean = new SupportBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setTechnician(DataUtility.getString(request.getParameter("technician")));
		bean.setTicketNo(DataUtility.getInt(request.getParameter("ticketno")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));
		bean.setType(DataUtility.getString(request.getParameter("type")));

		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Support ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));

		SupportModel model = new SupportModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			SupportBean bean;
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

		SupportModel model = new SupportModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			SupportBean bean = (SupportBean) populateBean(request);
			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" Support ctl DoPost 222222");
					ServletUtility.setSuccessMessage("Support is successfully Updated", request);

				} else {

					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Support is successfully Added ", request);

					// bean.setId(pk);
				}
				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" Support D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Support exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" Support ctl D p 5555555");

			SupportBean bean = (SupportBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" Support ctl D Post  6666666");
				ServletUtility.redirect(ORSView.SUPPORT_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" Support  ctl Do post 77777");

			ServletUtility.redirect(ORSView.SUPPORT_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.SUPPORT_VIEW;
	}
}

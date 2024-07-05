
package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.FamilyBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.FamilyModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "FamilyCtl", urlPatterns = { "/ctl/FamilyCtl" })
public class FamilyCtl extends BaseCtl{

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;

		} 
			  else if (!DataValidator.isName(request.getParameter("name"))) {
			  request.setAttribute("name", "name must contains alphabet only");
			  pass =false;
			  }
		

		if (DataValidator.isNull(request.getParameter("type"))) {
			request.setAttribute("type", PropertyReader.getValue("error.require", "type"));
			pass = false;

		} 
			  else if (!DataValidator.isName(request.getParameter("type"))) {
			  request.setAttribute("type", "type must contains alphabet only");
			  pass =false;
			  }
			 

		if (DataValidator.isNull(request.getParameter("salary"))) {
			request.setAttribute("salary", PropertyReader.getValue("error.require", "salary"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("salary"))) {
			request.setAttribute("salary", "salary  must contains numbers only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("mobile"))) {
			request.setAttribute("mobile", PropertyReader.getValue("error.require", "mobile"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobile"))) {
			request.setAttribute("mobile", "mobile must contains numbers only");
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		FamilyBean bean = new FamilyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		bean.setSalary(DataUtility.getLong(request.getParameter("salary")));
		
		bean.setType(DataUtility.getString(request.getParameter("type")));

		bean.setName(DataUtility.getString(request.getParameter("name")));
		
		bean.setMobile(DataUtility.getString(request.getParameter("mobile")));


		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("j ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		FamilyModel model = new FamilyModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			FamilyBean bean;
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

		FamilyModel model = new FamilyModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FamilyBean bean = (FamilyBean) populateBean(request);
			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" J ctl DoPost 222222");
					ServletUtility.setSuccessMessage("family is successfully Updated", request);

				} else {
					System.out.println(" J ctl DoPost 33333");
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("family is successfully Added", request);

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
				ServletUtility.setErrorMessage("family exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" J ctl D p 5555555");

			FamilyBean bean = (FamilyBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" u ctl D Post  6666666");
				ServletUtility.redirect(ORSView.FAMILY_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" J  ctl Do post 77777");

			ServletUtility.redirect(ORSView.FAMILY_LIST_CTL, request, response);
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
		return ORSView.FAMILY_VIEW;
	}

}

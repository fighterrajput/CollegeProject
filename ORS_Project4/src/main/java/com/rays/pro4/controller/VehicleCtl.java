package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.VehicleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.VehicleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "VehicleCtl", urlPatterns = { "/ctl/VehicleCtl" })

public class VehicleCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
	
		HashMap map1 = new HashMap();
		map1.put("Red", "Red");
		map1.put("Blue", "Blue");
		map1.put("Yellow", "Yellow");
		map1.put("White", "White");


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

		if (DataValidator.isNull(request.getParameter("number"))) {
			request.setAttribute("number", PropertyReader.getValue("error.require", "number"));
			pass = false;

		} else if (DataValidator.isTooLong(request.getParameter("number"),15)) {
			request.setAttribute("number", "number must contain max 15 digit");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("colour"))) {
			request.setAttribute("colour", PropertyReader.getValue("error.require", "colour"));
			pass = false;

		} 

		if (DataValidator.isNull(request.getParameter("insuranceamount"))) {
			request.setAttribute("insuranceamount", PropertyReader.getValue("error.require", "insuranceamount"));
			pass = false;
		} 
		else if (DataValidator.isTooLong(request.getParameter("insuranceamount"),15)) {
			request.setAttribute("insuranceamount", "insuranceamount must contain max 15 digit");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("purchasedate"))) {
			request.setAttribute("purchasedate", PropertyReader.getValue("error.require", "purchasedate"));
			pass = false;
		}
		
		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		VehicleBean bean = new VehicleBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setNumber(DataUtility.getString(request.getParameter("number")));
		
		bean.setPurchaseDate(DataUtility.getDate(request.getParameter("purchasedate")));
		
		bean.setInsuranceAmount(DataUtility.getLong(request.getParameter("insuranceamount")));
		
		bean.setColour(DataUtility.getString(request.getParameter("colour")));

		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Vehicle ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));

		VehicleModel model = new VehicleModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			VehicleBean bean;
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

		VehicleModel model = new VehicleModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			VehicleBean bean = (VehicleBean) populateBean(request);
			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" Vehicle ctl DoPost 222222");
					ServletUtility.setSuccessMessage("Vehicle is successfully Updated", request);

				} else {
					
						long pk = model.add(bean);
						ServletUtility.setBean(bean, request);
						ServletUtility.setSuccessMessage("Vehicle is successfully Added ", request);
				
					
				//		bean.setId(pk);
					}
					ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" Vehicle D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Vehicle exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" Vehicle ctl D p 5555555");

			VehicleBean bean = (VehicleBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" Vehicle ctl D Post  6666666");
				ServletUtility.redirect(ORSView.VEHICLE_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" Vehicle  ctl Do post 77777");

			ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.VEHICLE_VIEW;
	}
}
